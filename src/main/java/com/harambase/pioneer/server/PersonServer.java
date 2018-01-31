package com.harambase.pioneer.server;

import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.common.support.util.BuildUrlUtil;
import com.harambase.pioneer.common.support.util.RestTemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    public HaramMessage update(String ip, int port, String userId, Person person) {
        String remotePath = "/user/" + userId;
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
        requestUrl.append("?start=").append(start)
                .append("&length=").append(length)
                .append("&search=").append(search)
                .append("&order=").append(order)
                .append("&orderCol=").append(orderColumn);
        if (StringUtils.isNotEmpty(type))
            requestUrl.append("&type=").append(type);
        if (StringUtils.isNotEmpty(status))
            requestUrl.append("&status=").append(status);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage getPersonBySearch(String ip, int port, String search, String type, String status) {
        String remotePath = "/user/search?search=";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        if (StringUtils.isNotEmpty(search))
            requestUrl.append(search);
        if (StringUtils.isNotEmpty(type))
            requestUrl.append("&type=").append(type);
        if (StringUtils.isNotEmpty(status))
            requestUrl.append("&status=").append(status);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage uploadProfile(String ip, int port, String userId, String fileUri) {
        String remotePath = "/user/profile/" + userId;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        requestUrl.append("?profile").append(fileUri);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.PUT, params);
    }
}