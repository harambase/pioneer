package com.harambase.pioneer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.helper.Name;
import com.harambase.pioneer.server.pojo.dto.PersonReportOnly;
import com.harambase.pioneer.server.pojo.view.TranscriptView;
import com.harambase.pioneer.server.service.PersonServerService;
import com.harambase.pioneer.server.pojo.base.Person;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PersonServerService personServerService;

    @Autowired
    public PersonService(PersonServerService personServerService) {
        this.personServerService = personServerService;
    }


    public ResultMap login(Person person) {
        try {
            return personServerService.login(person);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap createPerson(Person person) {
        try {
            return personServerService.create(person);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap deletePerson(String userId) {
        try {
            return personServerService.delete(userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap updatePerson(String userId, Person person) {
        try {
            return personServerService.update(userId, person);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap get(String userId) {
        try {
            return personServerService.retrieve(userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap list(int start, int length, String search, String order, String orderColumn,
                          String type, String status, String role) {
        try {
            return personServerService.list(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderColumn, type, status, role);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap search(String search, String type, String status, String role) {
        try {
            return personServerService.search(search, type, status, role, "5");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap upload(String userId, MultipartFile file, String mode) {

        ResultMap message = new ResultMap();
        JSONObject jsonObject = new JSONObject();

        try {
            Person person = (Person) personServerService.retrieve(userId).getData();
            String name = file.getOriginalFilename();

            String fileUri;

            switch (mode) {
                case "p":

                    if (StringUtils.isNotEmpty(person.getProfile())) {
                        String oldInfoPath = (JSON.parseObject(person.getProfile())).getString("path");
                        FileUtil.deleteFileFromFTP(oldInfoPath, Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);
                    }

                    fileUri = FileUtil.uploadFileToFtpServer(file, Config.FTP_PATH + "/image/profile/", Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);

                    jsonObject.put("name", name);
                    jsonObject.put("size", file.getSize());
                    jsonObject.put("type", name.substring(name.lastIndexOf(".") + 1));
                    jsonObject.put("path", fileUri);

                    person.setProfile(jsonObject.toJSONString());

                    if (StringUtils.isNotEmpty(fileUri)) {
                        String fileName = FileUtil.getFileLogicalName(fileUri);
                        String localPath = Config.serverPath + "/static/" + FileUtil.getFileDirPath(fileUri);
                        File localFile = new File(localPath + fileName);
                        if (!localFile.exists())
                            FileUtil.saveFileToLocal(file, fileName, localPath);
                    }
                    break;

                case "f":

                    if (StringUtils.isNotEmpty(person.getUserInfo())) {
                        String oldInfoPath = (JSON.parseObject(person.getUserInfo())).getString("path");
                        FileUtil.deleteFileFromFTP(oldInfoPath, Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);
                    }

                    fileUri = FileUtil.uploadFileToFtpServer(file, Config.FTP_PATH + "/document/userInfo/", Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);

                    jsonObject.put("name", name);
                    jsonObject.put("size", file.getSize());
                    jsonObject.put("type", name.substring(name.lastIndexOf(".") + 1));
                    jsonObject.put("path", fileUri);

                    person.setUserInfo(jsonObject.toJSONString());
                    break;
            }

            message = personServerService.update(userId, person);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message.setMsg("上传失败");
            message.setCode(SystemConst.FAIL.getCode());
            return message;
        }
        logger.info("The file task - work mode is " + mode + " - for " + userId + " has completed!");
        message.setMsg("上传成功");
        message.setData(jsonObject);
        message.setCode(SystemConst.SUCCESS.getCode());
        return message;
    }

    public Person verifyUser(Person user) {
        Person existUser = (Person) personServerService.retrieve(user.getUserId()).getData();
        return existUser != null &&
                existUser.getBirthday().equals(user.getBirthday()) &&
                existUser.getTel().equals(user.getTel()) ? existUser : null;
    }

    public ResultMap updateLastLoginTime(String username) {
        try {
            return personServerService.updateLastLoginTime(username);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap downloadUserList(String status, String type, String role) {
        FileOutputStream fos = null;
        String csvPath = Config.serverPath + "user_list.csv";
        StringBuilder exportInfoSb = new StringBuilder();

        try {
            File outputFile = new File(csvPath);
            if (outputFile.exists()) {
                outputFile.delete();
                outputFile = new File(csvPath);
            }
            fos = new FileOutputStream(outputFile, true);
            //Solve for Chinese Character errors while using excel:
            fos.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});

            Field[] titleList = PersonReportOnly.class.getDeclaredFields();
            List<Person> personList = (List<Person>) personServerService.list("1", String.valueOf(Integer.MAX_VALUE), "", "asc",
                    "user_id", type, status, role).getData();

            exportInfoSb.append("序号,");
            for (int i = 0; i < titleList.length; i++) {
                if (i != 0) exportInfoSb.append(",");
                Name name = titleList[i].getAnnotation(Name.class);
                exportInfoSb.append("\"" + name.value() + "\"");
            }
            exportInfoSb.append("\n");

            for (int i = 0; i < personList.size(); i++) {
                Map<String, String> map = BeanUtils.describe(personList.get(i));
                exportInfoSb.append((i + 1) + ",");
                for (int j = 0; j < titleList.length; j++) {
                    if (j != 0) exportInfoSb.append(",");
                    String value = map.get(titleList[j].getName());
                    if (StringUtils.isNotEmpty(value))
                        exportInfoSb.append("\"" + value + "\"");
                    else
                        exportInfoSb.append("\"" + "\"");
                }
                exportInfoSb.append("\n");
            }

            exportInfoSb.append("总数:" + personList.size());
            fos.write(exportInfoSb.toString().getBytes("UTF-8"));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        logger.info("Reporting task for all students has completed.");
        ResultMap restMessage = new ResultMap();
        restMessage.setCode(SystemConst.SUCCESS.getCode());
        restMessage.setData("user_list.csv");
        return restMessage;
    }
}
