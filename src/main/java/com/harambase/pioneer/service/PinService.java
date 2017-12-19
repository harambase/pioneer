package com.harambase.pioneer.service;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Person;

public interface PinService {
    
    HaramMessage validate(Integer pin);
    
    HaramMessage generateAll(String startTime, String endTime, int role, String info, String remark);

    HaramMessage generateOne(String startTime, String endTime, int role, String info, String remark);

    HaramMessage deleteAllByInfo(String info);

    HaramMessage deleteSingleByPin(String pin);

    HaramMessage updateAllByInfo(String info);

    HaramMessage updateSingleByPin(String pin);

    HaramMessage listByInfo(String currentPage, String pageSize, String search, String order, String orderColumn, String info);

    HaramMessage sendAdvisorPin(String info, String senderId);

    HaramMessage sendFacultyPin(String info, String senderId);


}
