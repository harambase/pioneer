package com.harambase.pioneer.server;

import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.common.support.util.BuildUrlUtil;
import com.harambase.pioneer.common.support.util.RestTemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RoleServer {

    public HaramMessage findRoleByRoleid(String ip, int port, int roleId) {
        String remotePath = "/role/" + roleId;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage list(String search, String order, String orderCol) {
        String remotePath = "/role";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, Config.SERVER_IP, Config.SERVER_PORT);
        requestUrl.append("?order=").append(order)
                .append("&orderCol=").append(orderCol);
        if (StringUtils.isNotEmpty(search))
            requestUrl.append("&search=").append(search);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }
}
