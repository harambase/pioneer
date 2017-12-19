package com.harambase.pioneer.server;

import com.harambase.common.HaramMessage;
import com.harambase.support.util.RestTemplateUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.harambase.support.util.BuildUrlUtil.buildUrl;

@Component
public class RoleServer {
    private StringBuilder requestUrl = new StringBuilder();
    Map params = new HashMap();

    public HaramMessage getUserByAccount(String ip, int port, String account) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }
}
