package com.harambase.pioneer.server.service.impl;

import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.DateUtil;
import com.harambase.pioneer.common.support.util.PageUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.dao.base.MessageDao;
import com.harambase.pioneer.server.dao.repository.MessageRepository;
import com.harambase.pioneer.server.dao.repository.PersonRepository;
import com.harambase.pioneer.server.pojo.base.Message;
import com.harambase.pioneer.server.pojo.base.Person;
import com.harambase.pioneer.server.pojo.view.MessageView;
import com.harambase.pioneer.server.service.MessageServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessageServerServiceImpl implements MessageServerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MessageRepository messageRepository;
    private final PersonRepository personRepository;
    private final MessageDao messageDao;

    @Autowired
    public MessageServerServiceImpl(MessageRepository messageRepository,
                                    PersonRepository personRepository,
                                    MessageDao messageDao) {
        this.messageRepository = messageRepository;
        this.personRepository = personRepository;
        this.messageDao = messageDao;
    }

    @Override
    public ResultMap list(String currentPage, String pageSize, String search, String order, String orderColumn,
                          String receiverId, String senderId, String box) {
        try {
            ResultMap message = new ResultMap();

            long totalSize = messageDao.getCountByMapPageSearchOrdered(receiverId, senderId, box, search); //startTime, endTime);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            List<MessageView> msgs = messageDao.getByMapPageSearchOrdered(receiverId, senderId, box, search,
                    page.getCurrentIndex(), page.getPageSize(), order, orderColumn);

            message.setData(msgs);
            message.put("page", page);
            message.setMsg(SystemConst.SUCCESS.getMsg());
            message.setCode(SystemConst.SUCCESS.getCode());
            return message;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap retrieve(Integer id) {
        try {
            MessageView messageView = messageDao.findOne(id);
            String[] receiverIds = messageView.getReceiver().split("/");
            String receiverNames = "";
            for (String receiverId : receiverIds) {
                Optional<Person> receiver = personRepository.findById(receiverId);
                receiverNames += receiver.map(person -> person.getLastName()).orElse("") + ", "
                        + receiver.map(person -> person.getFirstName()).orElse("") + "/";
            }
            messageView.setReceiver(receiverNames);
            return ReturnMsgUtil.success(messageView);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap countByStatus(String receiverId, String senderId, String box, String status) {
        try {
            int count = messageDao.countMessageByStatus(receiverId, senderId, box, status);
            return ReturnMsgUtil.success(count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap update(Integer id, Message message) {
        try {
            message.setId(id);
            Message newMessage = messageRepository.save(message);
            return newMessage != null ? ReturnMsgUtil.success(newMessage) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap updateStatus(Integer id, String status) {
        try {
            Optional<Message> message = messageRepository.findById(id);
            Message msg = message.orElseThrow(RuntimeException::new);
            Message newMessage = messageRepository.save(msg);
            return ReturnMsgUtil.success(newMessage);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap create(Message message) {
        try {
            message.setDate(DateUtil.DateToStr(new Date()));
            Message newMessage = messageRepository.save(message);
            return newMessage != null ? ReturnMsgUtil.success(newMessage) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap delete(Integer id) {
        try {
            messageRepository.deleteById(id);
            return messageRepository.existsById(id) ? ReturnMsgUtil.fail() : ReturnMsgUtil.success(null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
