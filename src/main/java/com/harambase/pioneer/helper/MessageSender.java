package com.harambase.pioneer.helper;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.message.Labels;
import com.harambase.pioneer.common.message.Status;
import com.harambase.pioneer.common.message.Tags;
import com.harambase.pioneer.common.support.util.DateUtil;
import com.harambase.pioneer.common.support.util.IDUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.pojo.base.*;
import com.harambase.pioneer.server.service.AdviseService;
import com.harambase.pioneer.server.service.MessageService;
import com.harambase.pioneer.server.service.PersonService;
import com.harambase.pioneer.server.service.PinService;
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
    private final PinService pinService;
    private final AdviseService adviseService;

    @Autowired
    public MessageSender(MessageService messageService,
                         PersonService personService,
                         PinService pinService, AdviseService adviseService) {
        this.messageService = messageService;
        this.personService = personService;
        this.pinService = pinService;
        this.adviseService = adviseService;
    }

    public void sendImportantSystemMsg(String receiverId, String senderId, String body, String title, String subject) {

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

        Message newMessage = (Message) messageService.createMessage(message).getData();
        if (newMessage == null)
            throw new RuntimeException("信息插入失败!");

    }

    public void sendToAllSystem(TempUser tempUser) {
        List<Person> receiverList = (List<Person>) personService.listUsers("", "", "4", "1", String.valueOf(Integer.MAX_VALUE)).getData();

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

        String receiverId = "";
        for (Person p : receiverList) {
            receiverId += p.getUserId() + "/";
        }
        message.setReceiverId(receiverId);

        Message newMessage = (Message) messageService.createMessage(message).getData();
        if (newMessage == null)
            throw new RuntimeException("信息插入失败!");

    }

    public ResultMap sendFacultyPin(String info, String senderId) {
        try {
            List<Pin> pinInfoList = (List<Pin>) pinService.listByInfo("0", String.valueOf(Integer.MAX_VALUE), "", "desc", "pin", info).getData();

            String date = DateUtil.DateToStr(new Date());

            for (Pin pin : pinInfoList) {
                if (pin.getRole() == 2) {
                    Message message = new Message();
                    message.setDate(date);
                    message.setStatus(Status.UNREAD);
                    message.setTitle("成绩录入的识别码（PIN）");
                    message.setSubject("成绩录入");
                    message.setSenderId(senderId);
                    message.setAttachment(null);
                    message.setLabels(Labels.URGENT);
                    message.setTag(Tags.TEACH);

                    String facultyId = pin.getFacultyId();
                    String body = "您收到一条来自教务的信息：您用于本学期成绩录入的识别码（PIN）为：" + pin.getPin() + "，有效期为："
                            + pin.getStartTime() + "至" + pin.getEndTime() + ", 如有问题请与教务人员联系！";
                    message.setReceiverId(facultyId);
                    message.setBody(body);
                    Message newMessage = (Message) messageService.createMessage(message).getData();
                    if (newMessage == null)
                        throw new RuntimeException("信息插入失败!");
                }
            }

            return ReturnMsgUtil.success(null);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap sendAdvisorPin(String info, String senderId) {
        try {
            List<Pin> pinInfoList = (List<Pin>) pinService.listByInfo("0", String.valueOf(Integer.MAX_VALUE), "", "desc", "pin", info).getData();

            String date = DateUtil.DateToStr(new Date());

            for (Pin pin : pinInfoList) {
                if (pin.getRole() == 1) {

                    Message message = new Message();
                    message.setDate(date);
                    message.setStatus(Status.UNREAD);
                    message.setTitle("辅导学生选课识别码的信息");
                    message.setSubject("选课识别码");
                    message.setSenderId(senderId);
                    message.setAttachment(null);
                    message.setLabels(Labels.URGENT);
                    message.setTag(Tags.TEACH);

                    String studentId = pin.getStudentId();
                    String facultyId = ((Advise) adviseService.getAdviseByStudentId(studentId).getData()).getFacultyId();
                    Person student = (Person) personService.getUser(studentId).getData();
                    String studentName = student.getLastName() + ", " + student.getFirstName();

                    String body = "您的辅导学生" + studentName + "用于选课的PIN是：" + pin.getPin() + "，有效期为："
                            + pin.getStartTime() + "至" + pin.getEndTime() + "请及时告知，谢谢！";
                    message.setReceiverId(facultyId);
                    message.setBody(body);
                    Message newMessage = (Message) messageService.createMessage(message).getData();
                    if (newMessage == null)
                        throw new RuntimeException("信息插入失败!");
                }
            }

            return ReturnMsgUtil.success(null);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }

    }
}
