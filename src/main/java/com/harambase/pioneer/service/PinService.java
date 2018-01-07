package com.harambase.pioneer.service;

import com.harambase.common.HaramMessage;

public interface PinService {

    HaramMessage validate(Integer pin);

    HaramMessage generateAll(String startTime, String endTime, String role, String info, String remark);

    HaramMessage generateOne(String startTime, String endTime, int role, String info, String remark, String userId);

    HaramMessage deleteAllByInfo(String info);

    HaramMessage deleteSingleByPin(String pin);

    HaramMessage listByInfo(int start, int length, String search, String order, String orderColumn, String info);

    HaramMessage sendAdvisorPin(String info, String senderId);

    HaramMessage sendFacultyPin(String info, String senderId);

    HaramMessage getAllInfo();
}
