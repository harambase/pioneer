package com.harambase.pioneer.service;

import com.harambase.pioneer.server.pojo.base.Message;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.service.MessageServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MessageServerService messageServerService;

    @Autowired
    public MessageService(MessageServerService messageServerService) {
        this.messageServerService = messageServerService;
    }

    public ResultMap create(Message message) {
        try {
            return messageServerService.create(message);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap delete(Integer id) {
        try {
            return messageServerService.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap update(Integer id, Message message) {
        try {
            return messageServerService.update(id, message);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap updateStatus(Integer id, String status) {
        try {
            return messageServerService.updateStatus(id, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap get(Integer id) {
        try {
            return messageServerService.retrieve(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap list(int start, int length, String search, String order, String orderColumn, String userId, String box) {
        try {
            String receiverId = null;
            String senderId = null;

            if (box.contains("inbox") || box.contains("important") || box.contains("urgent"))
                receiverId = userId;
            if (box.contains("sent") || box.contains("draft"))
                senderId = userId;
            if (box.contains("trash")) {
                receiverId = userId;
                senderId = userId;
            }
            return messageServerService.list(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderColumn, receiverId, senderId, box);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap countMessageByStatus(String userId, String box, String status) {
        try {
            String receiverId = null;
            String senderId = null;

            if (box.contains("inbox") || box.contains("important") || box.contains("urgent"))
                receiverId = userId;
            if (box.contains("sent") || box.contains("draft"))
                senderId = userId;
            if (box.contains("trash")) {
                receiverId = userId;
                senderId = userId;
            }

            return messageServerService.countByStatus(receiverId, senderId, box.toLowerCase(), status.toLowerCase());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
