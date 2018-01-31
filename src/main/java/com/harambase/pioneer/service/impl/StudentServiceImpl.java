package com.harambase.pioneer.service.impl;

import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.pojo.Student;
import com.harambase.pioneer.server.StudentServer;
import com.harambase.pioneer.service.StudentService;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final StudentServer studentServer;

    @Autowired
    public StudentServiceImpl(StudentServer studentServer) {
        this.studentServer = studentServer;
    }

    @Override
    public HaramMessage transcriptDetail(String studentid) {
        try {
            return studentServer.transcriptDetail(IP, PORT, studentid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage update(String studentId, Student student) {
        try {
            return studentServer.updateByPrimaryKey(IP, PORT, studentId, student);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage studentList(int start, int length, String search, String order, String orderColumn,
                                    String type, String status) {
        try {
            return studentServer.studentList(IP, PORT, start, length, search, order, orderColumn, type, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage getAvailableCredit(String studentid, String info) {
        try {
            return studentServer.getAvailableCredit(IP, PORT, studentid, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage courseList(String status, String studentId) {
        try {
            return studentServer.courseList(IP, PORT, status, studentId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }
}
