package com.harambase.pioneer.service;

import com.harambase.pioneer.server.pojo.base.Message;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.MessageServer;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MessageServer messageServer;

    @Autowired
    public MessageService(MessageServer messageServer) {
        this.messageServer = messageServer;
    }
    
    public ResultMap create(Message message) {
        try {
            return messageServer.create(message);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap delete(Integer id) {
        try {
            return messageServer.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap update(Integer id, Message message) {
        try {
            return messageServer.update(id, message);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap updateStatus(Integer id, String status) {
        try {
            return messageServer.updateStatus(id, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap get(Integer id) {
        try {
            return messageServer.get(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap list(int start, int length, String search, String order, String orderColumn, String userId, String box) {
        try {
            return messageServer.list(start, length, search, order, orderColumn, userId, box);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap countMessageByStatus(String userId, String box, String status) {
        try {
            return messageServer.count(userId, box, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
