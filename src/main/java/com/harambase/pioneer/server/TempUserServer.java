package com.harambase.pioneer.server;

import com.alibaba.fastjson.JSONObject;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Advise;
import com.harambase.pioneer.pojo.TempUser;
import com.harambase.support.util.RestTemplateUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class TempUserServer {
    private StringBuilder requestUrl = new StringBuilder();
    Map params = new HashMap();

    public HaramMessage deleteByPrimaryKey(String ip, int port, Integer id) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage register(String ip, int port, JSONObject jsonObject) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage updateTempUser(String ip, int port, TempUser tempUser) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage tempUserList(String ip, int port, int currentIndex, int pageSize, String search, String order, String orderColumn, String viewStatus) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }
}