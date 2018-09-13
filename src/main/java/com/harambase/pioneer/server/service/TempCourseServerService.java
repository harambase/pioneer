package com.harambase.pioneer.server.service;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.TempCourse;

public interface TempCourseServerService {

    ResultMap list(String currentPage, String pageSize, String search, String order, String orderCol, String status, String facultyId);

    ResultMap create(String facultyId, JSONObject jsonObject);

    ResultMap delete(Integer id);

    ResultMap update(Integer id, TempCourse tempCourse);

    ResultMap retrieve(Integer id);
    
}
