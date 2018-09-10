package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Student;

/**
 * Created by linsh on 7/12/2017.
 */
public interface StudentService {

    ResultMap transcriptDetail(String studentId);

    ResultMap update(String studentId, Student student);

    ResultMap studentList(String s, String s1, String search, String order, String orderCol, String status);

    ResultMap getAvailableCredit(String studentId, String info);

    ResultMap courseList(String status, String studentId);

}
