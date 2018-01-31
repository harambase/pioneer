package com.harambase.pioneer.service.impl;

import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.server.StudentServer;
import com.harambase.pioneer.server.pojo.base.Student;
import com.harambase.pioneer.service.StudentService;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StudentServer studentServer;

    @Autowired
    public StudentServiceImpl(StudentServer studentServer) {
        this.studentServer = studentServer;
    }

    @Override
    public HaramMessage transcriptDetail(String studentid) {
        try {
            return studentServer.getTranscriptDetail(studentid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage update(String studentId, Student student) {
        try {
            return studentServer.update(studentId, student);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage studentList(int start, int length, String search, String order, String orderColumn, String status) {
        try {
            return studentServer.list(start, length, search, order, orderColumn, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage getAvailableCredit(String studentid, String info) {
        try {
            return studentServer.getAvailableCredit(studentid, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage courseList(String status, String studentId) {
        try {
            return studentServer.courseList(status, studentId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }
}
