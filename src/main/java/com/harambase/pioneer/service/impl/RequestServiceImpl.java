package com.harambase.pioneer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.TempUser;
import com.harambase.pioneer.server.MessageServer;
import com.harambase.pioneer.server.TempCourseServer;
import com.harambase.pioneer.server.TempUserServer;
import com.harambase.pioneer.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final TempUserServer tempUserServer;
    private final TempCourseServer tempCourseServer;
    private final MessageServer messageServer;

    @Autowired
    public RequestServiceImpl(TempUserServer tempUserServer,
                              TempCourseServer tempCourseServer,
                              MessageServer messageServer) {
        this.tempCourseServer = tempCourseServer;
        this.tempUserServer = tempUserServer;
        this.messageServer = messageServer;
    }

    @Override
    public HaramMessage deleteTempUserById(Integer id) {
        return tempUserServer.deleteByPrimaryKey(IP, PORT, id);
    }

    @Override
    public HaramMessage register(JSONObject jsonObject) {
        return tempUserServer.register(IP, PORT, jsonObject);
    }

    @Override
    public HaramMessage updateTempUser(Integer id, TempUser tempUser) {
        return tempUserServer.updateTempUser(IP, PORT, id, tempUser);
    }

    @Override
    public HaramMessage tempUserList(int start, int length, String search, String order, String orderColumn, String viewStatus) {
        return tempUserServer.tempUserList(IP, PORT, start, length, search, order, orderColumn, viewStatus);
    }

}
