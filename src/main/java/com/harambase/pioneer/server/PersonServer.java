package com.harambase.pioneer.server;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Person;
import com.harambase.support.util.BuildUrlUtil;
import com.harambase.support.util.RestTemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PersonServer {

    public HaramMessage login(String ip, int port, Person person) {
        String remotePath = "/user/login";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.POST, person);
    }

    public HaramMessage create(String ip, int port, Person person) {
        String remotePath = "/user";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.POST, person);
    }

    public HaramMessage delete(String ip, int port, String userid) {
        String remotePath = "/user/" + userid;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.DELETE, params);
    }

    public HaramMessage update(String ip, int port, Person person) {
        String remotePath = "/user";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.PUT, person);
    }

    public HaramMessage get(String ip, int port, String userid) {
        String remotePath = "/user/" + userid;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage list(String ip, int port, int start, int length, String search, String order,
                             String orderColumn, String type, String status) {
        String remotePath = "/user";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        requestUrl.append("?start=")
                .append(start)
                .append("&length=")
                .append(length)
                .append("&search=")
                .append(search)
                .append("&order=").append(order).append("orderCol=").append(orderColumn);
        if(StringUtils.isNotEmpty(type))
            requestUrl.append("&type=").append(type);
        if(StringUtils.isNotEmpty(status))
            requestUrl.append("&status=").append(status);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage getPersonBySearch(String ip, int port, String search, String type, String status) {
        String remotePath = "/user/search?search=";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        if(StringUtils.isNotEmpty(search))
            requestUrl.append(search);
        if(StringUtils.isNotEmpty(type))
            requestUrl.append("&type=").append(type);
        if(StringUtils.isNotEmpty(status))
            requestUrl.append("&status=").append(status);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

}