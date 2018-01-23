package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Message;
import com.harambase.pioneer.server.MessageServer;
import com.harambase.pioneer.service.MessageService;
import com.harambase.support.util.ReturnMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final MessageServer messageServer;

    @Autowired
    public MessageServiceImpl(MessageServer messageServer) {
        this.messageServer = messageServer;
    }

    @Override
    public HaramMessage create(Message message) {
        try {
            return messageServer.create(IP, PORT, message);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage delete(Integer id) {
        try {
            return messageServer.delete(IP, PORT, id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage update(Integer id, Message message) {
        try {
            return messageServer.update(IP, PORT, id, message);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage updateStatus(Integer id, String status) {
        try {
            return messageServer.updateStatus(IP, PORT, id, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage get(Integer id) {
        try {
            return messageServer.get(IP, PORT, id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage list(int start, int length, String search, String order, String orderColumn, String userId, String box) {
        try {
            return messageServer.messageList(IP, PORT, start, length, search, order, orderColumn, userId, box);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage countMessageByStatus(String userId, String box, String status) {
        try {
            return messageServer.countMessageByStatus(IP, PORT, userId, box, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
