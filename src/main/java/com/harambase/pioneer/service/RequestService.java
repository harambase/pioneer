package com.harambase.pioneer.service;

import com.alibaba.fastjson.JSONObject;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.TempCourse;
import com.harambase.pioneer.pojo.TempUser;

public interface RequestService {

    HaramMessage deleteTempUser(Integer id);

    HaramMessage registerNewUser(JSONObject jsonObject);

    HaramMessage updateTempUser(Integer id, TempUser tempUser);

    HaramMessage tempUserList(int start, int length, String search, String order, String orderCol, String viewStatus);

    HaramMessage updateTempCourse(Integer id, TempCourse tempCourse);

    HaramMessage registerNewCourse(JSONObject jsonObject);

    HaramMessage deleteTempCourse(Integer id);

    HaramMessage tempCourseList(Integer start, Integer length, String search, String order, String orderCol, String viewStatus, String facultyId);
}
