package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Student;

public interface StudentServerService {

    ResultMap update(String studentId, Student student);

    ResultMap list(String currentPage, String pageSize, String search, String order, String orderCol, String status);

    ResultMap getCreditInfo(String studentId, String info);

    ResultMap courseList(String status, String studentId);

    ResultMap transcriptList(String studentId);

}
