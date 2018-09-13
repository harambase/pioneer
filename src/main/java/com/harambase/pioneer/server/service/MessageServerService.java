package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Message;

public interface MessageServerService {

    ResultMap list(String currentPage, String pageSize, String search, String order, String orderColumn, String receiverId, String senderId, String box);

    ResultMap create(Message message);

    ResultMap delete(Integer id);

    ResultMap update(Integer id, Message message);

    ResultMap updateStatus(Integer id, String status);

    ResultMap retrieve(Integer id);

    ResultMap countByStatus(String receiverId, String senderId, String box, String status);

}
