package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Pin;

public interface PinServerService {

    ResultMap validate(Integer pin, String userId);

    ResultMap generateAll(String startTime, String endTime, int role, String info, String remark);

    ResultMap generateOne(String startTime, String endTime, int role, String info, String remark, String userId);

    ResultMap deleteByInfo(String info);

    ResultMap deleteByPinNum(Integer pin);

    ResultMap updateByPinNum(Integer pinNum, Pin pin);

    ResultMap updateComment(Integer pinNum, String comment);

    ResultMap list(String currentPage, String pageSize, String search, String order, String orderColumn, String info, String ownerId, String role);

    ResultMap getAllInfo();

    //发送识别码： Send Pin
    ResultMap sendFacultyPin(String info, String senderId);

    ResultMap sendStudentPin(String info, String senderId);

    ResultMap sendAdvisorPin(String info, String senderId);

    ResultMap resendPin(Pin pin, String userId);

    ResultMap sendSelfFeedbackPin(String info, String senderId);

    ResultMap sendOtherFeedbackPin(String info, String senderId);
}
