package com.harambase.pioneer.service;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Student;

/**
 * Created by linsh on 7/12/2017.
 */
public interface StudentService {

    HaramMessage transcriptDetail(String studentid);

    HaramMessage update(String studentId, Student student);

    HaramMessage studentList(int start, int length, String search, String order, String orderCol, String type, String status);

    HaramMessage getAvailableCredit(String studentid, String info);

    HaramMessage courseList(String status, String studentId);
}
