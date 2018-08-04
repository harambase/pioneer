package com.harambase.pioneer.server;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Message;
import com.harambase.pioneer.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageServer {

    private final MessageService messageService;

    @Autowired
    public MessageServer(MessageService messageService) {
        this.messageService = messageService;
    }

    public ResultMap create(Message message) {
        return messageService.createMessage(message);
    }

    public ResultMap delete(Integer id) {
        return messageService.delete(id);
    }

    public ResultMap update(Integer id, Message message) {
        return messageService.update(id, message);
    }

    public ResultMap updateStatus(Integer id, String status) {
        return messageService.updateStatus(id, status);
    }

    public ResultMap get(Integer id) {
        return messageService.getMessageView(id);
    }

    public ResultMap count(String userId, String box, String status) {

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

        return messageService.countMessageByStatus(receiverId, senderId, box.toLowerCase(), status.toLowerCase());
    }

    public ResultMap list(Integer start, Integer length, String search, String order, String orderCol, String userId, String box) {
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
        return messageService.list(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, receiverId, senderId, box);
    }

}
