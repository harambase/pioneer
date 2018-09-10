package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Pin;

public interface PinServerService {

    ResultMap validate(Integer pin, String userId);

    ResultMap generateAll(String startTime, String endTime, int role, String info, String remark);

    ResultMap generateOne(String startTime, String endTime, int role, String info, String remark, String userId);

    ResultMap deleteAllByInfo(String info);

    ResultMap listByInfo(String currentPage, String pageSize, String search, String order, String orderColumn, String info, String ownerId, String role);

    ResultMap deleteSingleByPin(Integer pin);

    ResultMap getAllInfo();

    ResultMap updateOne(Integer pinNum, Pin pin);

    ResultMap sendFacultyPin(String info, String senderId);

    ResultMap sendStudentPin(String info, String senderId);

    ResultMap sendAdvisorPin(String info, String senderId);

    ResultMap resend(Pin pin, String userId);
}
