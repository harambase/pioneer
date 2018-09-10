package com.harambase.pioneer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.common.support.util.IDUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.helper.MessageSender;
import com.harambase.pioneer.server.pojo.base.*;
import com.harambase.pioneer.server.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class RequestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TempUserServerService tempUserServerService;
    private final TempCourseServerService tempCourseServerService;
    private final TempAdviseServerService tempAdviseServerService;
    private final AdviseServerService adviseServerService;
    private final PersonServerService personServerService;
    private final CourseServerService courseServerService;
    private final MessageSender messageSender;

    @Autowired
    public RequestService(TempUserServerService tempUserServerService, TempCourseServerService tempCourseServerService,
                          TempAdviseServerService tempAdviseServerService, AdviseServerService adviseServerService,
                          MessageSender messageSender, PersonServerService personServerService,
                          CourseServerService courseServerService) {
        this.tempUserServerService = tempUserServerService;
        this.tempCourseServerService = tempCourseServerService;
        this.tempAdviseServerService = tempAdviseServerService;
        this.adviseServerService = adviseServerService;
        this.messageSender = messageSender;
        this.personServerService = personServerService;
        this.courseServerService = courseServerService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public ResultMap deleteTempUser(Integer id) {
        try {
            return tempUserServerService.deleteTempUserById(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap registerNewUser(JSONObject jsonObject) {
        try {
            String password = jsonObject.getString("password");
            jsonObject.put("password", passwordEncoder().encode(password));
            return tempUserServerService.register(jsonObject);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap updateTempUser(Integer id, TempUser tempUser) {
        try {
            ResultMap resultMap;
            if (tempUser.getStatus().equals("1")) {
                Person newUser = JSONObject.parseObject(tempUser.getUserJson(), Person.class);
                resultMap = personServerService.addUser(newUser);
                if (resultMap.getCode() == SystemConst.SUCCESS.getCode()) {
                    Person person = ((Person) resultMap.getData());
                    String info = person.getLastName() + ", " + person.getFirstName() + "(" + person.getUserId() + ")";
                    resultMap = tempUserServerService.updateTempUser(id, tempUser);

                    if (resultMap.getCode() == SystemConst.SUCCESS.getCode()) {
                        messageSender.sendImportantSystemMsg(person.getUserId(), tempUser.getOperatorId(),
                                "您接收到来自系统的一条消息:您的账户已创建！欢迎来到先锋！", "用户创建", "用户申请");
                        messageSender.sendImportantSystemMsg(tempUser.getOperatorId(), IDUtil.ROOT,
                                "您接收到来自系统的一条消息:来自用户 " + info + " 批准已通过！", "批准操作成功", "用户申请");
                    }
                }
            } else if (tempUser.getStatus().equals("-1")) {
                resultMap = tempUserServerService.updateTempUser(id, tempUser);
                JSONObject jsonObject = JSONObject.parseObject(tempUser.getUserJson());
                String info = jsonObject.getString("lastName") + ", " + jsonObject.get("firstName") + "(" + tempUser.getUserId() + ")";
                if (resultMap.getCode() == SystemConst.SUCCESS.getCode()) {
                    messageSender.sendImportantSystemMsg(tempUser.getOperatorId(), IDUtil.ROOT,
                            "您接收到来自系统的一条消息:来自用户 " + info + " 批准已拒绝！", "拒绝操作成功", "用户申请");
                } else {
                    messageSender.sendImportantSystemMsg(tempUser.getOperatorId(), IDUtil.ROOT,
                            "您接收到来自系统的一条消息:来自用户 " + info + " 批准已拒绝！", "拒绝操作失败", "用户申请");
                }

            } else {
                resultMap = tempUserServerService.updateTempUser(id, tempUser);
            }
            return resultMap;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap tempUserList(int start, int length, String search, String order, String orderColumn, String viewStatus) {
        try {
            return tempUserServerService.tempUserList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderColumn, viewStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap updateTempCourse(Integer id, TempCourse tempCourse) {
        try {
            if (tempCourse.getStatus().equals("1")) {
                ResultMap message = courseServerService.addCourse(JSONObject.parseObject(tempCourse.getCourseJson(), Course.class));
                if (message.getCode() == SystemConst.SUCCESS.getCode()) {
                    return tempCourseServerService.updateTempCourse(id, tempCourse);
                } else {
                    return message;
                }
            }
            return tempCourseServerService.updateTempCourse(id, tempCourse);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap registerNewCourse(String facultyId, JSONObject jsonObject) {
        try {
            return tempCourseServerService.register(facultyId, jsonObject);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap deleteTempCourse(Integer id) {
        try {
            return tempCourseServerService.deleteTempCourseById(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap tempCourseList(Integer start, Integer length, String search, String order, String orderCol, String viewStatus, String facultyId) {
        try {
            return tempCourseServerService.tempCourseList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, viewStatus, facultyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap getTempUser(Integer id) {
        try {
            return tempUserServerService.get(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap getTempCourse(Integer id) {
        try {
            return tempCourseServerService.get(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap uploadCourseInfo(Integer id, MultipartFile file) {
        ResultMap message = new ResultMap();
        JSONObject jsonObject = new JSONObject();

        try {
            TempCourse tempCourse = (TempCourse) tempCourseServerService.get(id).getData();

            //处理老的文件
            JSONObject courseJson = JSONObject.parseObject(tempCourse.getCourseJson());

            if (StringUtils.isNotEmpty(courseJson.getString("courseInfo"))) {
                String oldInfoPath = (JSON.parseObject(courseJson.getString("courseInfo"))).getString("path");
                FileUtil.deleteFileFromFTP(oldInfoPath, Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);
            }

            String fileUri = FileUtil.uploadFileToFtpServer(file, Config.FTP_PATH + "/document/courseInfo/", Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);
            String name = file.getOriginalFilename();

            jsonObject.put("name", name);
            jsonObject.put("size", file.getSize());
            jsonObject.put("type", name.substring(name.lastIndexOf(".") + 1));
            jsonObject.put("path", fileUri);

            courseJson.put("courseInfo", jsonObject.toJSONString());

            tempCourse.setCourseJson(courseJson.toJSONString());
            message = tempCourseServerService.updateTempCourse(id, tempCourse);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message.setMsg("上传失败");
            message.setCode(SystemConst.FAIL.getCode());
            return message;
        }

        message.setMsg("上传成功");
        message.setData(jsonObject);
        message.setCode(SystemConst.SUCCESS.getCode());
        return message;
    }

    public ResultMap registerTempAdvise(TempAdvise tempAdvise) {
        try {
            return tempAdviseServerService.register(tempAdvise);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap deleteTempAdviseById(Integer id) {
        try {
            return tempAdviseServerService.deleteTempAdviseById(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap getTempAdvise(String studentId) {
        try {
            return tempAdviseServerService.get(studentId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap tempAdviseList(Integer start, Integer length, String search, String order, String orderCol, String viewStatus, String info, String studentId, String facultyId) {
        try {
            return tempAdviseServerService.tempAdviseList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, viewStatus, info, studentId, facultyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap updateTempAdvise(Integer id, TempAdvise tempAdvise) {
        try {
            return tempAdviseServerService.updateTempAdvise(id, tempAdvise);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap tempUserUpload(Integer id, MultipartFile file, String mode) {

        ResultMap message = new ResultMap();
        JSONObject jsonObject = new JSONObject();

        try {
            TempUser tempUser = (TempUser) tempUserServerService.get(id).getData();
            JSONObject person = JSON.parseObject(tempUser.getUserJson());

            String name = file.getOriginalFilename();

            String fileUri;

            switch (mode) {
                case "p":

                    if (StringUtils.isNotEmpty(person.getString("profile"))) {
                        String oldInfoPath = (JSON.parseObject(person.getString("profile"))).getString("path");
                        FileUtil.deleteFileFromFTP(oldInfoPath, Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);
                    }

                    fileUri = FileUtil.uploadFileToFtpServer(file, Config.FTP_PATH + "/image/profile/", Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);

                    jsonObject.put("name", name);
                    jsonObject.put("size", file.getSize());
                    jsonObject.put("type", name.substring(name.lastIndexOf(".") + 1));
                    jsonObject.put("path", fileUri);

                    person.put("profile", jsonObject.toJSONString());
                    tempUser.setUserJson(JSON.toJSONString(person));

                    if (StringUtils.isNotEmpty(fileUri)) {
                        String fileName = FileUtil.getFileLogicalName(fileUri);
                        String localPath = Config.serverPath + "/static/" + FileUtil.getFileDirPath(fileUri);
                        File localFile = new File(localPath + fileName);
                        if (!localFile.exists())
                            FileUtil.saveFileToLocal(file, fileName, localPath);
                    }
                    break;

                case "f":

                    if (StringUtils.isNotEmpty(person.getString("userInfo"))) {
                        String oldInfoPath = (JSON.parseObject(person.getString("userInfo"))).getString("path");
                        FileUtil.deleteFileFromFTP(oldInfoPath, Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);
                    }

                    fileUri = FileUtil.uploadFileToFtpServer(file, Config.FTP_PATH + "/document/userInfo/", Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);

                    jsonObject.put("name", name);
                    jsonObject.put("size", file.getSize());
                    jsonObject.put("type", name.substring(name.lastIndexOf(".") + 1));
                    jsonObject.put("path", fileUri);

                    person.put("userInfo", jsonObject.toJSONString());
                    tempUser.setUserJson(JSON.toJSONString(person));
                    break;
            }

            message = tempUserServerService.updateTempUser(id, tempUser);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message.setMsg("上传失败");
            message.setCode(SystemConst.FAIL.getCode());
            return message;
        }

        logger.info("The file task - user  mode is" + mode + " - for tempUser serial id:" + id + " has completed!");
        message.setMsg("上传成功");
        message.setData(jsonObject);
        message.setCode(SystemConst.SUCCESS.getCode());
        return message;
    }

    public ResultMap assignAdvisor(Integer id, TempAdvise tempAdvise, String choice) {
        try {
            Advise advise = new Advise();
            String facultyId = "";
            advise.setStudentId(tempAdvise.getStudentId());
            advise.setOperatorId(tempAdvise.getOperatorId());
            advise.setInfo(tempAdvise.getInfo());
            advise.setStatus("1");

            switch (choice) {
                case "1":
                    facultyId = tempAdvise.getFirstId();
                    break;
                case "2":
                    facultyId = tempAdvise.getSecondId();
                    break;
                case "3":
                    facultyId = tempAdvise.getThirdId();
                    break;
            }
            advise.setFacultyId(facultyId);

            ResultMap message = adviseServerService.assignMentor(advise);
            if (message.getCode() == SystemConst.SUCCESS.getCode()) {
                return tempAdviseServerService.updateTempAdvise(id, tempAdvise);
            } else {
                return message;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }
}
