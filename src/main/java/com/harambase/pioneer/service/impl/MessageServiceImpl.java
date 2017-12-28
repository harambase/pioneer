package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Message;
import com.harambase.pioneer.server.MessageServer;
import com.harambase.pioneer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final MessageServer messageServer;

    @Autowired
    public MessageServiceImpl(MessageServer messageServer) {
        this.messageServer = messageServer;
    }

    @Override
    public HaramMessage create(Message message) {
        return messageServer.create(IP, PORT, message);
    }

    @Override
    public HaramMessage delete(Integer id) {
        return messageServer.delete(IP, PORT, id);
    }

    @Override
    public HaramMessage update(Integer id, Message message) {
        return messageServer.update(IP, PORT, id, message);
    }

    @Override
    public HaramMessage get(Integer id) {
        return messageServer.get(IP, PORT, id);
    }

    @Override
    public HaramMessage list(int start, int length, String search, String order, String orderColumn, String userId, String box) {
        return messageServer.messageList(IP, PORT, start, length, search, order, orderColumn, userId, box);
    }

    @Override
    public HaramMessage countMessageByStatus(String userId, String box, String status) {
        return messageServer.countMessageByStatus(IP, PORT, userId, box, status);
    }

}
