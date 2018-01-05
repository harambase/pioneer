package com.harambase.pioneer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.TempCourse;
import com.harambase.pioneer.pojo.TempUser;
import com.harambase.pioneer.server.RequestServer;
import com.harambase.pioneer.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {

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
    public HaramMessage registerNewCourse(JSONObject jsonObject) {
        return requestServer.registerNewCourse(IP, PORT, jsonObject);
    }

    @Override
    public HaramMessage deleteTempCourse(Integer id) {
        return requestServer.deleteTempCourse(IP, PORT, id);
    }

    @Override
    public HaramMessage tempCourseList(Integer start, Integer length, String search, String order, String orderCol, String viewStatus, String facultyId) {
        return requestServer.tempCourseList(IP, PORT, start, length, search, order, orderCol, viewStatus, facultyId);
    }

}
