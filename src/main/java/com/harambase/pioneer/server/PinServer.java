package com.harambase.pioneer.server;

import com.harambase.common.HaramMessage;
import com.harambase.support.util.RestTemplateUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PinServer {
    private StringBuilder requestUrl = new StringBuilder();
    Map params = new HashMap();

    public HaramMessage generatAll(String ip, int port, String startTime, String endTime, int role, String info, String remark) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage generateOne(String ip, int port, String startTime, String endTime, int role, String info, String remark) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage deleteSingleByPin(String ip, int port, String pin) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage deleteAllByInfo(String ip, int port, String info) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage updateAllByInfo(String ip, int port, String info) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage updateSingleByPin(String ip, int port, String pin) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage validate(String ip, int port, Integer pinNum) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage listByInfo(String ip, int port, int currentIndex, int pageSize, String search, String order, String orderColumn, String info) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage sendFacultyPin(String ip, int port, String info, String senderId) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage sendAdvisorPin(String ip, int port, String info, String senderId) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

}