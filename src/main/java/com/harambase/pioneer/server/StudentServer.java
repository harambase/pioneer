package com.harambase.pioneer.server;

import com.harambase.pioneer.common.ResultMap;
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

    public ResultMap getTranscriptDetail(String studentid) {
        return studentService.transcriptDetail(studentid);
    }

    public ResultMap getAvailableCredit(String studentId, String info) {
        return studentService.getAvailableCredit(studentId, info);
    }

    public ResultMap update(String studentId, Student student) {
        return studentService.update(studentId, student);
    }

    public ResultMap list(Integer start, Integer length, String search, String order, String orderCol, String status) {
        return studentService.studentList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, status);
    }

    public ResultMap courseList(String status, String studentId) {
        return studentService.courseList(status, studentId);
    }

//    public ResultMap getContract(String studentId) {
//        return studentService.getContract(studentId);
//    }
//
//    public ResultMap updateContract(String studentId, String contractString) {
//        return studentService.updateContract(studentId, contractString);
//    }
}
