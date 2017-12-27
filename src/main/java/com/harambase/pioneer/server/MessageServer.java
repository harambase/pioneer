package com.harambase.pioneer.server;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Message;
import com.harambase.pioneer.pojo.base.MessageBase;
import com.harambase.pioneer.pojo.base.MessageWithBLOBs;
import com.harambase.support.util.BuildUrlUtil;
import com.harambase.support.util.RestTemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageServer {
   
    
    public HaramMessage create(String ip, int port, Message message) {
        String remotePath = "/message";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.POST, message);
    }

    public HaramMessage delete(String ip, int port, Integer id) {
        String remotePath = "/message/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.DELETE, params);
    }

    public HaramMessage update(String ip, int port, Integer id, Message message) {
        String remotePath = "/message/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.PUT, message);
    }

    public HaramMessage get(String ip, int port, Integer id) {
        String remotePath = "/message/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage messageList(String ip, int port, int start, int length, String search, String order, String orderColumn,
                                    String userId, String box) {
        String remotePath = "/message";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        requestUrl.append("?start=").append(start)
                .append("&length=").append(length)
                .append("&search=").append(search)
                .append("&order=").append(order)
                .append("orderCol=").append(orderColumn)
                .append("&userId=").append(userId)
                .append("&box=").append(box);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage countMessageByStatus(String ip, int port, String userId, String box, String status) {
        String remotePath = "/message/count?userId=" + userId + "&box=" + box + "&status=" + status;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }
}