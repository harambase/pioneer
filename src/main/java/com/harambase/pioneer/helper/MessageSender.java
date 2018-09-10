package com.harambase.pioneer.helper;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.message.Labels;
import com.harambase.pioneer.common.message.Status;
import com.harambase.pioneer.common.message.Tags;
import com.harambase.pioneer.common.support.util.DateUtil;
import com.harambase.pioneer.common.support.util.IDUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.dao.base.AdviseDao;
import com.harambase.pioneer.server.dao.base.PersonDao;
import com.harambase.pioneer.server.dao.base.PinDao;
import com.harambase.pioneer.server.dao.repository.MessageRepository;
import com.harambase.pioneer.server.pojo.base.*;
import com.harambase.pioneer.server.pojo.view.AdviseView;

import com.harambase.pioneer.server.pojo.view.PinView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Transactional
public class MessageSender {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MessageRepository messageRepository;

    private final AdviseDao adviseDao;
    private final PinDao pinDao;
    private final PersonDao personDao;

    @Autowired
    public MessageSender(MessageRepository messageRepository,
                         PersonDao personDao,
                         PinDao pinDao, AdviseDao adviseDao) {
        this.messageRepository = messageRepository;
        this.personDao = personDao;
        this.pinDao = pinDao;
        this.adviseDao = adviseDao;
    }

    public void sendImportantSystemMsg(String receiverId, String senderId, String body, String title, String subject) {
        try {
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

            Message newMessage = messageRepository.save(message);
            if (newMessage == null)
                throw new RuntimeException("信息插入失败!");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void sendToAllSystem(TempUser tempUser) {
        try {
            List<Person> receiverList = personDao.getPersonBySearch("", "", "4", "1", String.valueOf(Integer.MAX_VALUE));

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

            Message newMessage = messageRepository.save(message);
            if (newMessage == null)
                throw new RuntimeException("信息插入失败!");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    public ResultMap sendFacultyPinByInfo(String info, String senderId) {
        try {
            List<PinView> pinInfoList = pinDao.getByMapPageSearchOrdered(0, Integer.MAX_VALUE, "", "desc", "pin", info, "", "");

            for (PinView pin : pinInfoList) {
                if (pin.getRole() == 2) {
                    ResultMap resultMap = sendFacultyPin(pin, senderId);
                    if (resultMap.getCode() != 2001)
                        throw new RuntimeException("信息插入失败!");
                }
            }

            return ReturnMsgUtil.success(null);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap sendAdvisorPinByInfo(String info, String senderId) {
        try {
            List<PinView> pinInfoList = pinDao.getByMapPageSearchOrdered(0, Integer.MAX_VALUE, "", "desc", "pin", info, "", "");

            for (PinView pin : pinInfoList) {
                if (pin.getRole() == 1) {
                    ResultMap resultMap = sendAdvisorPin(pin, senderId);
                    if (resultMap.getCode() != 2001)
                        throw new RuntimeException("信息插入失败!");
                }
            }

            return ReturnMsgUtil.success(null);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }

    }

    public ResultMap sendStudentPinByInfo(String info, String senderId) {
        try {
            List<PinView> pinInfoList = pinDao.getByMapPageSearchOrdered(0, Integer.MAX_VALUE, "", "desc", "pin", info, "", "");

            for (PinView pin : pinInfoList) {
                if (pin.getRole() == 3) {
                    ResultMap resultMap = sendStudentPin(pin, senderId);
                    if (resultMap.getCode() != 2001)
                        throw new RuntimeException("信息插入失败!");
                }
            }

            return ReturnMsgUtil.success(null);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap resendPin(Pin pin, String senderId) {
        ResultMap resultMap;
        try {
            switch (pin.getRole()) {
                case 1:
                    resultMap = sendAdvisorPin(pin, senderId);
                    break;
                case 2:
                    resultMap = sendFacultyPin(pin, senderId);
                    break;
                default:
                    throw new RuntimeException("识别码信息错误!");
            }

            return resultMap.getCode() == 2001 ? ReturnMsgUtil.success(null) : ReturnMsgUtil.fail();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }

    }

    public ResultMap sendFacultyPin(PinView pinView, String senderId) {
        Pin pin = new Pin();
        pin.setOwnerId(pinView.getOwnerId());
        pin.setPin(pinView.getPin());
        pin.setRole(pinView.getRole());
        pin.setStartTime(pinView.getStartTime());
        pin.setEndTime(pinView.getEndTime());
        return this.sendFacultyPin(pin, senderId);
    }

    public ResultMap sendFacultyPin(Pin pin, String senderId) {
        try {
            Message message = new Message();
            message.setDate(DateUtil.DateToStr(new Date()));
            message.setStatus(Status.UNREAD);
            message.setTitle("成绩录入的识别码(PIN)的信息");
            message.setSubject("成绩录入");
            message.setSenderId(senderId);
            message.setAttachment(null);
            message.setLabels(Labels.URGENT);
            message.setTag(Tags.TEACH);

            String facultyId = pin.getOwnerId();
            String body = "您收到一条来自教务的信息：您用于本学期成绩录入的识别码（PIN）为：" + pin.getPin() + "，有效期为："
                    + pin.getStartTime() + "至" + pin.getEndTime() + ", 如有问题请与教务人员联系！";
            message.setReceiverId(facultyId);
            message.setBody(body);
            Message newMessage = messageRepository.save(message);

            return newMessage != null ? ReturnMsgUtil.success(null) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap sendAdvisorPin(PinView pinView, String senderId) {
        Pin pin = new Pin();
        pin.setOwnerId(pinView.getOwnerId());
        pin.setPin(pinView.getPin());
        pin.setRole(pinView.getRole());
        pin.setStartTime(pinView.getStartTime());
        pin.setEndTime(pinView.getEndTime());
        return this.sendAdvisorPin(pin, senderId);
    }

    public ResultMap sendAdvisorPin(Pin pin, String senderId) {

        try {
            String studentId = pin.getOwnerId();

            AdviseView adviseView = adviseDao.getAdviseViewByStudentId(studentId);

            Message message = new Message();
            message.setDate(DateUtil.DateToStr(new Date()));
            message.setStatus(Status.UNREAD);
            message.setTitle("您所辅导的学生选课识别码(PIN)的信息");
            message.setSubject("选课识别码");
            message.setSenderId(senderId);
            message.setAttachment(null);
            message.setLabels(Labels.URGENT);
            message.setTag(Tags.TEACH);

            String studentName = adviseView.getSname();
            String body = "您的辅导学生" + studentName + "用于选课的PIN是：" + pin.getPin() + "，有效期为："
                    + pin.getStartTime() + "至" + pin.getEndTime() + "。请及时告知，谢谢！";

            message.setReceiverId(adviseView.getFacultyId());
            message.setBody(body);
            Message newMessage = messageRepository.save(message);

            return newMessage != null ? ReturnMsgUtil.success(null) : ReturnMsgUtil.fail();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap sendStudentPin(PinView pinView, String senderId) {
        Pin pin = new Pin();
        pin.setOwnerId(pinView.getOwnerId());
        pin.setPin(pinView.getPin());
        pin.setRole(pinView.getRole());
        pin.setStartTime(pinView.getStartTime());
        pin.setEndTime(pinView.getEndTime());
        return this.sendStudentPin(pin, senderId);
    }

    public ResultMap sendStudentPin(Pin pin, String senderId) {

        try {
            Message message = new Message();
            message.setDate(DateUtil.DateToStr(new Date()));
            message.setStatus(Status.UNREAD);
            message.setTitle("选导师的识别码(PIN)的信息");
            message.setSubject("选导师");
            message.setSenderId(senderId);
            message.setAttachment(null);
            message.setLabels(Labels.URGENT);
            message.setTag(Tags.TEACH);

            String facultyId = pin.getOwnerId();
            String body = "您收到一条来自教务的信息：您用于本学期选导师的识别码（PIN）为：" + pin.getPin() + "，有效期为："
                    + pin.getStartTime() + "至" + pin.getEndTime() + ", 如有问题请与教务人员联系！";
            message.setReceiverId(facultyId);
            message.setBody(body);
            Message newMessage = messageRepository.save(message);

            return newMessage != null ? ReturnMsgUtil.success(null) : ReturnMsgUtil.fail();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
