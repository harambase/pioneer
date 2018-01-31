package com.harambase.pioneer.server;

import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.server.pojo.base.Student;
import com.harambase.pioneer.server.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentServer {

    private final StudentService studentService;

    @Autowired
    public StudentServer(StudentService studentService) {
        this.studentService = studentService;
    }

    public HaramMessage getTranscriptDetail(String studentid) {
        return studentService.transcriptDetail(studentid);
    }

    public HaramMessage getAvailableCredit(String studentId, String info) {
        return studentService.getAvailableCredit(studentId, info);
    }

    public HaramMessage update(String studentId, Student student) {
        return studentService.update(studentId, student);
    }

    public HaramMessage list(Integer start, Integer length, String search, String order, String orderCol, String status) {
        return studentService.studentList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, status);
    }

    public HaramMessage courseList(String status, String studentId) {
        return studentService.courseList(status, studentId);
    }

}
