package com.harambase.pioneer.server;

import com.harambase.pioneer.common.HaramMessage;
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

    public HaramMessage create(Message message) {
        return messageService.createMessage(message);
    }

    public HaramMessage delete(Integer id) {
        return messageService.delete(id);
    }

    public HaramMessage update(Integer id, Message message) {
        return messageService.update(id, message);
    }

    public HaramMessage updateStatus(Integer id, String status) {
        return messageService.updateStatus(id, status);
    }

    public HaramMessage get(Integer id) {
        return messageService.getMessageView(id);
    }

    public HaramMessage count(String status, String box, String userId) {

        String receiverId = null;
        String senderid = null;

        if (box.contains("inbox") || box.contains("important"))
            receiverId = userId;
        if (box.contains("sent") || box.contains("draft"))
            senderid = userId;
        if (box.contains("trash")) {
            receiverId = userId;
            senderid = userId;
        }

        return messageService.countMessageByStatus(receiverId, senderid, box.toLowerCase(), status.toLowerCase());
    }

    public HaramMessage list(Integer start, Integer length, String search, String order, String orderCol, String box, String userId) {
        String receiverId = null;
        String senderid = null;

        if (box.contains("inbox") || box.contains("important"))
            receiverId = userId;
        if (box.contains("sent") || box.contains("draft"))
            senderid = userId;
        if (box.contains("trash")) {
            receiverId = userId;
            senderid = userId;
        }
        return messageService.list(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, receiverId, senderid, box);
    }

}
