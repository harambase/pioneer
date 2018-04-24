package com.harambase.pioneer.helper;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.message.Labels;
import com.harambase.pioneer.common.message.Status;
import com.harambase.pioneer.common.message.Tags;
import com.harambase.pioneer.common.support.util.DateUtil;
import com.harambase.pioneer.common.support.util.IDUtil;
import com.harambase.pioneer.server.pojo.base.Message;
import com.harambase.pioneer.server.pojo.base.Person;
import com.harambase.pioneer.server.pojo.base.TempUser;
import com.harambase.pioneer.server.service.MessageService;
import com.harambase.pioneer.server.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MessageSender {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MessageService messageService;
    private final PersonService personService;

    @Autowired
    public MessageSender(MessageService messageService,
                         PersonService personService){
        this.messageService = messageService;
        this.personService = personService;
    }

    public void sendImportantSystemMsg(String receiverId, String senderId, String body, String title, String subject){

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

        messageService.createMessage(message);

    }

    public void sendToAllSystem(TempUser tempUser) {
        List<Person> receiverList = (List<Person>)personService.listUsers("", "", "s", "1").getData();

        JSONObject jsonObject = JSONObject.parseObject(tempUser.getUserJson());
        String info = jsonObject.getString("lastName") + ", " + jsonObject.get("firstName") + "(" + tempUser.getUserId() + ")";

        Message message = new Message();
        message.setDate(DateUtil.DateToStr(new Date()));
        message.setSenderId(IDUtil.ROOT);
        message.setBody("您接收到来自系统的一条消息:收到来自用户" + info + "的注册申请！请尽快处理，谢谢！");
        message.setTitle("用户申请");
        message.setSubject("用户申请");
        message.setStatus(Status.UNREAD);
        message.setTag(Tags.SYSTEM);
        message.setLabels(Labels.IMPORTANT);

        for(Person p: receiverList) {
            message.setReceiverId(p.getUserId());
            messageService.createMessage(message);
        }
    }
}
