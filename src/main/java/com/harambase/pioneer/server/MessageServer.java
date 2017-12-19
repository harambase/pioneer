package com.harambase.pioneer.server;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Message;
import com.harambase.pioneer.pojo.base.MessageBase;
import com.harambase.pioneer.pojo.base.MessageWithBLOBs;
import com.harambase.support.util.RestTemplateUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageServer {
    private StringBuilder requestUrl = new StringBuilder();
    Map params = new HashMap();
    
    public HaramMessage create(String ip, int port, Message message) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage delete(String ip, int port, Integer id) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage update(String ip, int port, Integer id, Message message) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage get(String ip, int port, Integer id) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage messageList(String ip, int port, int currentIndex, int pageSize, String search, String order, String orderColumn, String receiverid, String senderid, String box) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage countMessageByStatus(String ip, int port, String receiverId, String senderId, String box, String status) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }
}