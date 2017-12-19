package com.harambase.pioneer.server;

import com.harambase.common.HaramMessage;
import com.harambase.support.util.RestTemplateUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.harambase.support.util.BuildUrlUtil.buildUrl;

@Component
public class RoleServer {
    public HaramMessage getUserByAccount(String ip, int port, String account) {
        String remotePath = "/md/user/account";
        StringBuilder requestUrl = buildUrl(remotePath, ip, port);
        requestUrl.append("?account=")
                .append(account);
        HashMap<String, String> params = new HashMap<>();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }
}
