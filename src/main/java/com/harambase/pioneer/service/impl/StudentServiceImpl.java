package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Student;
import com.harambase.pioneer.server.StudentServer;
import com.harambase.pioneer.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final StudentServer studentServer;

    @Autowired
    public StudentServiceImpl(StudentServer studentServer) {
        this.studentServer = studentServer;
    }

    @Override
    public HaramMessage transcriptDetail(String studentid) {
        return studentServer.transcriptDetail(IP, PORT, studentid);
    }

    @Override
    public HaramMessage update(String studentId, Student student) {
        return studentServer.updateByPrimaryKey(IP, PORT, studentId, student);
    }

    @Override
    public HaramMessage studentList(int start, int length, String search, String order, String orderColumn,
                                    String type, String status) {
        return studentServer.studentList(IP, PORT, start, length, search, order, orderColumn, type, status);
    }

    @Override
    public HaramMessage getAvailableCredit(String studentid, String info) {
        return studentServer.getAvailableCredit(IP, PORT, studentid, info);
    }
}
