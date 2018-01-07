package com.harambase.pioneer.server;

import com.harambase.common.HaramMessage;
import com.harambase.support.util.BuildUrlUtil;
import com.harambase.support.util.RestTemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PinServer {

    public HaramMessage generatAll(String ip, int port, String startTime, String endTime, int role, String info, String remark) {
        String remotePath = "/pin";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        requestUrl.append("?startTime=").append(startTime)
                .append("&endTime=").append(endTime)
                .append("&role=").append(role)
                .append("&info=").append(info)
                .append("&remark=").append(remark);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.POST, params);
    }

    public HaramMessage generateOne(String ip, int port, String startTime, String endTime, int role, String info, String remark, String userId) {
        String remotePath = "/pin/" + userId;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        requestUrl.append("?startTime=").append(startTime)
                .append("&endTime=").append(endTime)
                .append("&role=").append(role)
                .append("&info=").append(info)
                .append("&remark=").append(remark);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.POST, params);
    }

    public HaramMessage deleteSingleByPin(String ip, int port, String pin) {
        String remotePath = "/pin/" + pin;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.DELETE, params);
    }

    public HaramMessage deleteAllByInfo(String ip, int port, String info) {
        String remotePath = "/pin/all?info=" + info;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.DELETE, params);
    }

    public HaramMessage validate(String ip, int port, Integer pinNum) {
        String remotePath = "/pin/" + pinNum;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage listByInfo(String ip, int port, int start, int length, String search, String order,
                                   String orderColumn, String info) {
        String remotePath = "/pin";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        requestUrl.append("?start=").append(start)
                .append("&length=").append(length)
                .append("&search=").append(search)
                .append("&order=").append(order)
                .append("&orderCol=").append(orderColumn);
        if (StringUtils.isNotEmpty(info))
            requestUrl.append("&info=").append(info);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage sendFacultyPin(String ip, int port, String info, String senderId) {
        String remotePath = "/pin/send/faculty/" + info + "?senderId=" + senderId;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage sendAdvisorPin(String ip, int port, String info, String senderId) {
        String remotePath = "/pin/send/advisor/" + info + "?senderId=" + senderId;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage getAllInfo(String ip, int port) {
        String remotePath = "/pin/info";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }
}