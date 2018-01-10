package com.harambase.pioneer.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.pojo.TempCourse;
import com.harambase.pioneer.pojo.TempUser;
import com.harambase.pioneer.server.RequestServer;
import com.harambase.pioneer.service.RequestService;
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
public class RequestServiceImpl implements RequestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final RequestServer requestServer;

    @Autowired
    public RequestServiceImpl(RequestServer requestServer) {
        this.requestServer = requestServer;
    }

    @Override
    public HaramMessage deleteTempUser(Integer id) {
        return requestServer.deleteTempUser(IP, PORT, id);
    }

    @Override
    public HaramMessage registerNewUser(JSONObject jsonObject) {
        return requestServer.registerNewUser(IP, PORT, jsonObject);
    }

    @Override
    public HaramMessage updateTempUser(Integer id, TempUser tempUser) {
        return requestServer.updateTempUser(IP, PORT, id, tempUser);
    }

    @Override
    public HaramMessage tempUserList(int start, int length, String search, String order, String orderColumn, String viewStatus) {
        return requestServer.tempUserList(IP, PORT, start, length, search, order, orderColumn, viewStatus);
    }

    @Override
    public HaramMessage updateTempCourse(Integer id, TempCourse tempCourse) {
        return requestServer.updateTempCourse(IP, PORT, id, tempCourse);
    }

    @Override
    public HaramMessage registerNewCourse(String facultyId, JSONObject jsonObject) {
        return requestServer.registerNewCourse(IP, PORT, facultyId, jsonObject);
    }

    @Override
    public HaramMessage deleteTempCourse(Integer id) {
        return requestServer.deleteTempCourse(IP, PORT, id);
    }

    @Override
    public HaramMessage tempCourseList(Integer start, Integer length, String search, String order, String orderCol, String viewStatus, String facultyId) {
        return requestServer.tempCourseList(IP, PORT, start, length, search, order, orderCol, viewStatus, facultyId);
    }

    @Override
    public HaramMessage getTempUser(Integer id) {
        return requestServer.getTempUser(IP, PORT, id);
    }

    @Override
    public HaramMessage getTempCourse(Integer id) {
        return requestServer.getTempCourse(IP, PORT, id);
    }

    @Override
    public HaramMessage uploadCourseInfo(Integer id, MultipartFile file) {
        HaramMessage message = new HaramMessage();
        JSONObject jsonObject = new JSONObject();

        try {
            LinkedHashMap tempCourseMap = (LinkedHashMap) requestServer.getTempCourse(IP, PORT, id).getData();
            TempCourse tempCourse = new TempCourse();
            BeanUtils.populate(tempCourse, tempCourseMap);
            String name = file.getOriginalFilename();

            String fileUri;

            String oldInfo = "";
            JSONObject courseJson = JSONObject.parseObject(tempCourse.getCourseJson());

            if(courseJson.get("courseInfo") != null)
                oldInfo = (JSON.parseObject(courseJson.getString("courseInfo"))).getString("path");

            if (StringUtils.isNotEmpty(oldInfo)) {
                File oldFile = new File(Config.serverPath + oldInfo);
                oldFile.delete();
            }

            fileUri = FileUtil.uploadFileToPath(file, "/static/upload/document/courseInfo");

            jsonObject.put("name", name);
            jsonObject.put("size", file.getSize());
            jsonObject.put("type", name.substring(name.lastIndexOf(".") + 1));
            jsonObject.put("path", fileUri);

            courseJson.put("courseJson", jsonObject.toJSONString());

            message = requestServer.updateTempCourse(IP, PORT, id, tempCourse);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message.setMsg("上传失败");
            message.setCode(FlagDict.FAIL.getV());
            return message;
        }

        message.setMsg("上传成功");
        message.setData(jsonObject);
        return message;
    }

}
