package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Message;

public interface MessageService {

    ResultMap list(String currentPage, String pageSize, String search, String order,
                   String orderColumn, String receiverid, String senderid, String box);

    ResultMap getMessageView(Integer id);

    ResultMap countMessageByStatus(String receiverid, String senderid, String box, String status);

    ResultMap createMessage(Message message);

    ResultMap delete(Integer id);

    ResultMap update(Integer id, Message message);

    ResultMap updateStatus(Integer id, String status);
}
