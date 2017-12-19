package com.harambase.pioneer.service;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Message;

public interface MessageService {

    HaramMessage list(String currentPage, String pageSize, String search, String order,
                      String orderColumn,String receiverid, String senderid, String box);

    HaramMessage get(Integer id);
    
    HaramMessage countMessageByStatus(String receiverid, String senderid, String box, String status);

    HaramMessage create(Message message);

    HaramMessage delete(Integer id);

    HaramMessage update(Integer id, Message message);
}
