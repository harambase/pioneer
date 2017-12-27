package com.harambase.support.util;

import com.alibaba.fastjson.JSON;
import com.harambase.common.HaramMessage;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RestTemplateUtil {

    public static HaramMessage sendRestRequest(String url, HttpMethod method, Object body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Map map= (Map) SecurityUtils.getSubject().getSession().getAttribute("user");
        httpHeaders.set("user", JSON.toJSONString(map));
        httpHeaders.set("Content-UserType", "application/json;charset=UTF-8");
        HttpEntity httpEntity = new HttpEntity<>(body, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HaramMessage> responseEntity = restTemplate.exchange(url, method, httpEntity, HaramMessage.class);
        return HaramMessageUtil.from(responseEntity);
    }

    public static HaramMessage sendOwnerRequest(String url, HttpMethod method, String owner, Object body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Map map= (Map) SecurityUtils.getSubject().getSession().getAttribute("user");
        httpHeaders.set("user", JSON.toJSONString(map));
        httpHeaders.set("owner", owner);
        httpHeaders.set("Content-UserType", "application/json;charset=UTF-8");
        HttpEntity httpEntity = new HttpEntity<>(body, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HaramMessage> responseEntity = restTemplate.exchange(url, method, httpEntity, HaramMessage.class);
        return HaramMessageUtil.from(responseEntity);
    }

    public static HaramMessage sendRestRequest(String url, HttpMethod method, HttpHeaders httpHeaders, Object body) {
        HttpEntity httpEntity = new HttpEntity<>(body, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HaramMessage> responseEntity = restTemplate.exchange(url, method, httpEntity, HaramMessage.class);
        return HaramMessageUtil.from(responseEntity);
    }

    public static HaramMessage executeDataUnitDataMysql(String json, String type, String url, String token) {
        String requestUrl = url + "?runengine=" + type.toLowerCase();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("token", token);
        httpHeaders.set("Content-UserType", "application/json;charset=UTF-8");
        HttpEntity httpEntity = new HttpEntity<>(json, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HaramMessage> response = restTemplate.exchange(requestUrl, HttpMethod.POST, httpEntity, HaramMessage.class);
        return HaramMessageUtil.from(response);
    }


    public static ResponseEntity sendRestRequestForFile(String url, HttpMethod method, String token, Object body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (token != null && !"".equals(token)) httpHeaders.set("token", token);
        HttpEntity httpEntity = new HttpEntity<>(body, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, method, httpEntity, byte[].class);
    }

    public static HaramMessage sendRequestWithOwner(String url, HttpMethod method, Object body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Map map= (Map) SecurityUtils.getSubject().getSession().getAttribute("user");
        httpHeaders.set("user", JSON.toJSONString(map));
        httpHeaders.set("owner", (String)map.get("account"));
        httpHeaders.set("Content-UserType", "application/json;charset=UTF-8");
        HttpEntity httpEntity = new HttpEntity<>(body, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HaramMessage> responseEntity = restTemplate.exchange(url, method, httpEntity, HaramMessage.class);
        return HaramMessageUtil.from(responseEntity);
    }
}
