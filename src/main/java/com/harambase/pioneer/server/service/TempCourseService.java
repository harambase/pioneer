package com.harambase.pioneer.server.service;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.TempCourse;

public interface TempCourseService {

    ResultMap register(String facultyId, JSONObject jsonObject);

    ResultMap deleteTempCourseById(Integer id);

    ResultMap updateTempCourse(Integer id, TempCourse tempCourse);

    ResultMap tempCourseList(String s, String s1, String search, String order, String orderCol, String status, String facultyId);

    ResultMap get(Integer id);

}
