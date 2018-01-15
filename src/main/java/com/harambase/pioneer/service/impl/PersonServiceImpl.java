package com.harambase.pioneer.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.server.PersonServer;
import com.harambase.pioneer.service.PersonService;
import com.harambase.support.util.FileUtil;
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
public class PersonServiceImpl implements PersonService {

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PersonServer personServer;

    @Autowired
    public PersonServiceImpl(PersonServer personServer) {
        this.personServer = personServer;
    }

    @Override
    public HaramMessage login(Person person) {
        return personServer.login(IP, PORT, person);
    }

    @Override
    public HaramMessage createPerson(Person person) {
        return personServer.create(IP, PORT, person);
    }

    @Override
    public HaramMessage deletePerson(String userid) {
        return personServer.delete(IP, PORT, userid);
    }

    @Override
    public HaramMessage updatePerson(String userId, Person person) {
        return personServer.update(IP, PORT, userId, person);
    }

    @Override
    public HaramMessage get(String userid) {
        return personServer.get(IP, PORT, userid);
    }

    @Override
    public HaramMessage list(int start, int length, String search, String order, String orderColumn,
                             String type, String status) {
        return personServer.list(IP, PORT, start, length, search, order, orderColumn, type, status);
    }

    @Override
    public HaramMessage search(String search, String type, String status) {
        return personServer.getPersonBySearch(IP, PORT, search, type, status);
    }

    @Override
    public HaramMessage upload(String userId, MultipartFile file, String mode) {

        HaramMessage message = new HaramMessage();
        JSONObject jsonObject = new JSONObject();

        try {
            LinkedHashMap personMap = (LinkedHashMap) personServer.get(IP, PORT, userId).getData();
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

                    fileUri = FileUtil.uploadFileToPath(file, "/upload/image/profile");

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

            message = personServer.update(IP, PORT, userId, person);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message.setMsg("上传失败");
            message.setCode(FlagDict.FAIL.getV());
            return message;
        }
        logger.info("注意：" + userId + "的文件任务完成！mode=" + mode);
        message.setMsg("上传成功");
        message.setData(jsonObject);
        return message;
    }

}
