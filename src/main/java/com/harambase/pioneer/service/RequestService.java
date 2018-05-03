package com.harambase.pioneer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.RequestServer;
import com.harambase.pioneer.server.pojo.base.TempAdvise;
import com.harambase.pioneer.server.pojo.base.TempCourse;
import com.harambase.pioneer.server.pojo.base.TempUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class RequestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RequestServer requestServer;

    @Autowired
    public RequestService(RequestServer requestServer) {
        this.requestServer = requestServer;
    }


    public ResultMap deleteTempUser(Integer id) {
        try {
            return requestServer.removeUserRequest(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap registerNewUser(JSONObject jsonObject) {
        try {
            return requestServer.register(jsonObject);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap updateTempUser(Integer id, TempUser tempUser) {
        try {
            return requestServer.updateRequest(id, tempUser);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap tempUserList(int start, int length, String search, String order, String orderColumn, String viewStatus) {
        try {
            return requestServer.userList(start, length, search, order, orderColumn, viewStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap updateTempCourse(Integer id, TempCourse tempCourse) {
        try {
            return requestServer.updateCourseRequest(id, tempCourse);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap registerNewCourse(String facultyId, JSONObject jsonObject) {
        try {
            return requestServer.registerNewCourse(facultyId, jsonObject);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap deleteTempCourse(Integer id) {
        try {
            return requestServer.removeCourseRequest(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap tempCourseList(Integer start, Integer length, String search, String order, String orderCol, String viewStatus, String facultyId) {
        try {
            return requestServer.courseList(start, length, search, order, orderCol, viewStatus, facultyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap getTempUser(Integer id) {
        try {
            return requestServer.getUserRequest(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap getTempCourse(Integer id) {
        try {
            return requestServer.getCourseRequest(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap uploadCourseInfo(Integer id, MultipartFile file) {
        ResultMap message = new ResultMap();
        JSONObject jsonObject = new JSONObject();

        try {
            TempCourse tempCourse = (TempCourse) requestServer.getCourseRequest(id).getData();

            //处理老的文件
            JSONObject courseJson = JSONObject.parseObject(tempCourse.getCourseJson());

            if (StringUtils.isNotEmpty(courseJson.getString("courseInfo"))) {
                String oldInfoPath = (JSON.parseObject(courseJson.getString("courseInfo"))).getString("path");
                File oldFile = new File(Config.TEMP_FILE_PATH + oldInfoPath);
                oldFile.delete();
            }

            String fileUri = FileUtil.uploadFileToPath(file, "/document/courseInfo");
            String name = file.getOriginalFilename();

            jsonObject.put("name", name);
            jsonObject.put("size", file.getSize());
            jsonObject.put("type", name.substring(name.lastIndexOf(".") + 1));
            jsonObject.put("path", fileUri);

            courseJson.put("courseInfo", jsonObject.toJSONString());

            tempCourse.setCourseJson(courseJson.toJSONString());
            message = requestServer.updateCourseRequest(id, tempCourse);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message.setMsg("上传失败");
            message.setCode(SystemConst.FAIL.getCode());
            return message;
        }

        message.setMsg("上传成功");
        message.setData(jsonObject);
        return message;
    }


    public ResultMap registerTempAdvise(String studentId, String facultyIds) {
        try {
            return requestServer.newAdvisorRequest(studentId, facultyIds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap deleteTempAdviseById(Integer id) {
        try {
            return requestServer.removeAdvisorRequest(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap getTempAdvise(Integer id) {
        try {
            return requestServer.getAdviseRequest(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap tempAdviseList(Integer start, Integer length, String search, String order, String orderCol) {
        try {
            return requestServer.adviseList(start, length, search, order, orderCol);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap updateTempAdvise(Integer id, TempAdvise tempAdvise) {
        try {
            return requestServer.updateAdviseRequest(id, tempAdvise);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap tempUserUpload(Integer id, MultipartFile file, String mode) {

        ResultMap message = new ResultMap();
        JSONObject jsonObject = new JSONObject();

        try {
            TempUser tempUser = (TempUser) requestServer.getUserRequest(id).getData();
            JSONObject person = JSON.parseObject(tempUser.getUserJson());

            String name = file.getOriginalFilename();

            String fileUri;

            switch (mode) {
                case "p":

                    if (StringUtils.isNotEmpty(person.getString("profile"))) {
                        String oldProfilePath = (JSON.parseObject(person.getString("profile"))).getString("path");
                        File oldFile = new File(Config.TEMP_FILE_PATH + oldProfilePath);
                        oldFile.delete();
                    }

                    fileUri = FileUtil.uploadFileToPath(file, "/image/profile");

                    jsonObject.put("name", name);
                    jsonObject.put("size", file.getSize());
                    jsonObject.put("type", name.substring(name.lastIndexOf(".") + 1));
                    jsonObject.put("path", fileUri);

                    person.put("profile", jsonObject.toJSONString());
                    tempUser.setUserJson(JSON.toJSONString(person));

                case "f":

                    if (StringUtils.isNotEmpty(person.getString("userInfo"))) {
                        String oldInfoPath = (JSON.parseObject(person.getString("userInfo"))).getString("path");
                        File oldFile = new File(Config.TEMP_FILE_PATH + oldInfoPath);
                        oldFile.delete();
                    }

                    fileUri = FileUtil.uploadFileToPath(file, "/document/userInfo");

                    jsonObject.put("name", name);
                    jsonObject.put("size", file.getSize());
                    jsonObject.put("type", name.substring(name.lastIndexOf(".") + 1));
                    jsonObject.put("path", fileUri);

                    person.put("userInfo", jsonObject.toJSONString());
                    tempUser.setUserJson(JSON.toJSONString(person));
                    break;
            }

            message = requestServer.updateRequest(id, tempUser);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message.setMsg("上传失败");
            message.setCode(SystemConst.FAIL.getCode());
            return message;
        }
        logger.info("The file task - user  mode is" + mode + " - for tempUser serial id:" + id + " has completed!");
        message.setMsg("上传成功");
        message.setData(jsonObject);
        return message;
    }
}
