package com.harambase.pioneer.server;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Role;
import com.harambase.support.util.BuildUrlUtil;
import com.harambase.support.util.RestTemplateUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.harambase.support.util.BuildUrlUtil.buildUrl;

@Component
public class RoleServer {

    public HaramMessage findRoleByRoleid(String ip, int port, int roleId) {
        String remotePath = "/role/" + roleId;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

}
