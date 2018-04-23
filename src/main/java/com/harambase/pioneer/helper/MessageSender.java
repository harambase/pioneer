package com.harambase.pioneer.helper;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.message.Labels;
import com.harambase.pioneer.common.message.Status;
import com.harambase.pioneer.common.message.Tags;
import com.harambase.pioneer.common.support.util.DateUtil;
import com.harambase.pioneer.common.support.util.IDUtil;
import com.harambase.pioneer.server.pojo.base.Message;
import com.harambase.pioneer.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MessageSender {

    private final MessageService messageService;

    @Autowired
    public MessageSender(MessageService messageService){
        this.messageService = messageService;
    }

    public ResultMap sendImportantSystemMsg(String receiverId, String senderId, String body, String title, String subject){

        Message message = new Message();
        message.setDate(DateUtil.DateToStr(new Date()));
        message.setReceiverId(receiverId);
        message.setSenderId(senderId);
        message.setBody(body);
        message.setTitle(title);
        message.setSubject(subject);
        message.setStatus(Status.UNREAD);
        message.setTag(Tags.SYSTEM);
        message.setLabels(Labels.IMPORTANT);

        return messageService.createMessage(message);
    }
}
