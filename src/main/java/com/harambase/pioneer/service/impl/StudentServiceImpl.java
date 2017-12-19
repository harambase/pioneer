package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.common.Page;
import com.harambase.pioneer.pojo.Student;
import com.harambase.pioneer.server.StudentServer;
import com.harambase.pioneer.service.StudentService;
import com.harambase.support.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final StudentServer studentServer;

    @Autowired
    public StudentServiceImpl(StudentServer studentServer){
        this.studentServer = studentServer;
    }

    @Override
    public HaramMessage transcriptDetail(String studentid) {
        return studentServer.transcriptDetail(IP, PORT, studentid);
    }

    @Override
    public HaramMessage update(Student student) {
        return studentServer.updateByPrimaryKey(IP, PORT, student);
    }

    @Override
    public HaramMessage studentList(String currentPage, String pageSize, String search, String order, String orderColumn,
                                    String type, String status) {
        Page page = new Page();
        page.setCurrentPage(PageUtil.getcPg(currentPage));
        page.setPageSize(PageUtil.getLimit(pageSize));
        return studentServer.studentList(IP, PORT, page.getCurrentIndex(), page.getPageSize(), search, order, orderColumn, type, status);
    }

    @Override
    public HaramMessage getAvailableCredit(String studentid, String info) {
        return studentServer.getAvailableCredit(IP, PORT, studentid, info);
    }
}
