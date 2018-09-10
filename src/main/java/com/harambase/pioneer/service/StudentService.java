package com.harambase.pioneer.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.service.StudentServerService;
import com.harambase.pioneer.server.pojo.base.Student;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StudentServerService studentServerService;

    @Autowired
    public StudentService(StudentServerService studentServerService) {
        this.studentServerService = studentServerService;
    }


    public ResultMap transcriptDetail(String studentid) {
        try {
            return studentServerService.transcriptDetail(studentid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap update(String studentId, Student student) {
        try {
            return studentServerService.update(studentId, student);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap studentList(int start, int length, String search, String order, String orderColumn, String status) {
        try {
            return studentServerService.studentList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderColumn, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap getAvailableCredit(String studentid, String info) {
        try {
            return studentServerService.getAvailableCredit(studentid, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap courseList(String status, String studentId) {
        try {
            return studentServerService.courseList(status, studentId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
