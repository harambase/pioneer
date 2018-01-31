package com.harambase.pioneer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.common.constant.FlagDict;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.PersonServer;
import com.harambase.pioneer.server.pojo.base.Person;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.LinkedHashMap;

@Service
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PersonServer personServer;

    @Autowired
    public PersonService(PersonServer personServer) {
        this.personServer = personServer;
    }

    
    public HaramMessage login(Person person) {
        try {
            return personServer.login(person);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage createPerson(Person person) {
        try {
            return personServer.create(person);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage deletePerson(String userid) {
        try {
            return personServer.delete(userid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage updatePerson(String userId, Person person) {
        try {
            return personServer.update(userId, person);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage get(String userid) {
        try {
            return personServer.get(userid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage list(int start, int length, String search, String order, String orderColumn,
                             String type, String status) {
        try {
            return personServer.list(start, length, search, order, orderColumn, type, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage search(String search, String type, String status) {
        try {
            return personServer.search(search, type, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public HaramMessage upload(String userId, MultipartFile file, String mode) {

        HaramMessage message = new HaramMessage();
        JSONObject jsonObject = new JSONObject();

        try {
            LinkedHashMap personMap = (LinkedHashMap) personServer.get(userId).getData();
            Person person = new Person();
            BeanUtils.populate(person, personMap);
            String name = file.getOriginalFilename();

            String fileUri;

            switch (mode) {
                case "p":

                    if (StringUtils.isNotEmpty(person.getProfile())) {
                        String oldProfilePath = (JSON.parseObject(person.getProfile())).getString("path");
                        File oldFile = new File(Config.TEMP_FILE_PATH + oldProfilePath);
                        oldFile.delete();
                    }

                    fileUri = FileUtil.uploadFileToPath(file, "/image/profile");

                    jsonObject.put("name", name);
                    jsonObject.put("size", file.getSize());
                    jsonObject.put("type", name.substring(name.lastIndexOf(".") + 1));
                    jsonObject.put("path", fileUri);

                    person.setProfile(jsonObject.toJSONString());
                    break;

                case "f":

                    if (StringUtils.isNotEmpty(person.getUserInfo())) {
                        String oldInfoPath = (JSON.parseObject(person.getUserInfo())).getString("path");
                        File oldFile = new File(Config.TEMP_FILE_PATH + oldInfoPath);
                        oldFile.delete();
                    }

                    fileUri = FileUtil.uploadFileToPath(file, "/document/userInfo");

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
            message.setCode(FlagDict.FAIL.getV());
            return message;
        }
        logger.info("The file task - work mode is " + mode + " - for " + userId + " has completed!");
        message.setMsg("上传成功");
        message.setData(jsonObject);
        return message;
    }

}
