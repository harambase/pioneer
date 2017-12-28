package com.harambase.pioneer.server;

import com.harambase.common.HaramMessage;
import com.harambase.support.util.BuildUrlUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class RoleServer {

    public HaramMessage findRoleByRoleid(String ip, int port, int roleId) {
        String remotePath = "/role/" + roleId;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-UserType", "application/json;charset=UTF-8");
        HttpEntity httpEntity = new HttpEntity<>(params, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<HaramMessage> responseMessage = restTemplate.exchange(requestUrl.toString(), HttpMethod.GET, httpEntity, HaramMessage.class);

        return responseMessage.getBody();
    }

}
