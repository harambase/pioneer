package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.common.UploadFile;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.server.PersonServer;
import com.harambase.pioneer.service.PersonService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class PersonServiceImpl implements PersonService {

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

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
        Map<String, Object> map = new HashMap<>();

        try {
            LinkedHashMap personMap = (LinkedHashMap) personServer.get(IP, PORT, userId).getData();
            Person person = new Person();
            BeanUtils.populate(person, personMap);
            String name = file.getOriginalFilename();

            String fileUri;

            switch (mode) {
                case "p":
                    String oldProfile = person.getProfile();

                    if (StringUtils.isNotEmpty(oldProfile)) {
                        File oldfile = new File(Config.serverPath + oldProfile);
                        oldfile.delete();
                    }

                    fileUri = UploadFile.uploadFileToPath(file, "/static/upload/image/profile");

                    map.put("name", name);
                    map.put("size", file.getSize());
                    map.put("type", name.substring(name.lastIndexOf(".") + 1));
                    map.put("path", fileUri);

                    person.setProfile(fileUri);
                    break;

                case "f":
                    String oldInfo = person.getInfo();

                    if (StringUtils.isNotEmpty(oldInfo)) {
                        File oldfile = new File(Config.serverPath + oldInfo);
                        oldfile.delete();
                    }

                    fileUri = UploadFile.uploadFileToPath(file, "/static/upload/document/userInfo");

                    map.put("name", name);
                    map.put("size", file.getSize());
                    map.put("type", name.substring(name.lastIndexOf(".") + 1));
                    map.put("path", fileUri);

                    person.setInfo(fileUri);
                    break;
            }

            message = personServer.update(IP, PORT, userId, person);

        } catch (Exception e) {
            e.printStackTrace();
            message.setMsg("上传失败");
            message.setCode(FlagDict.FAIL.getV());
            return message;
        }

        message.setMsg("上传成功");
        message.setData(map);
        return message;
    }

}
