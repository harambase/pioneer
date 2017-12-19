package com.harambase.pioneer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.harambase.common.*;
import com.harambase.common.constant.FlagDict;
import com.harambase.support.util.DateUtil;
import com.harambase.support.util.IDUtil;
import com.harambase.support.util.PageUtil;
import com.harambase.pioneer.server.MessageServer;
import com.harambase.pioneer.server.TempCourseServer;
import com.harambase.pioneer.server.TempUserServer;
import com.harambase.pioneer.pojo.base.MessageWithBLOBs;
import com.harambase.pioneer.pojo.TempUser;
import com.harambase.pioneer.pojo.Advise;
import com.harambase.pioneer.service.RequestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                              MessageServer messageServer){
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
    public HaramMessage updateTempUser(TempUser tempUser) {
        return tempUserServer.updateTempUser(IP, PORT, tempUser);
    }

    @Override
    public HaramMessage tempUserList(String currentPage, String pageSize, String search, String order, String orderColumn, String viewStatus) {
        Page page = new Page();
        page.setCurrentPage(PageUtil.getcPg(currentPage));
        page.setPageSize(PageUtil.getLimit(pageSize));
        return tempUserServer.tempUserList(IP, PORT, page.getCurrentIndex(), page.getPageSize(), search, order, orderColumn, viewStatus);
    }

}
