package com.harambase.pioneer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.PersonServer;
import com.harambase.pioneer.server.pojo.base.Person;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PersonServer personServer;

    @Autowired
    public PersonService(PersonServer personServer) {
        this.personServer = personServer;
    }


    public ResultMap login(Person person) {
        try {
            return personServer.login(person);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap createPerson(Person person) {
        try {
            return personServer.create(person);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap deletePerson(String userId) {
        try {
            return personServer.delete(userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap updatePerson(String userId, Person person) {
        try {
            return personServer.update(userId, person);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap get(String userId) {
        try {
            return personServer.get(userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap list(int start, int length, String search, String order, String orderColumn,
                          String type, String status, String role) {
        try {
            return personServer.list(start, length, search, order, orderColumn, type, status, role);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap search(String search, String type, String status, String role) {
        try {
            return personServer.search(search, type, status, role);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap upload(String userId, MultipartFile file, String mode) {

        ResultMap message = new ResultMap();
        JSONObject jsonObject = new JSONObject();

        try {
            Person person = (Person) personServer.get(userId).getData();
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

            message = personServer.update(userId, person);

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
        Person existUser = (Person) personServer.get(user.getUserId()).getData();
        return existUser != null &&
                existUser.getBirthday().equals(user.getBirthday()) &&
                existUser.getTel().equals(user.getTel()) ? existUser : null;
    }

    public ResultMap updateLastLoginTime(String username) {
        try {
            return personServer.updateLastLoginTime(username);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }
}
