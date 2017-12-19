package com.harambase.pioneer.controller;

import com.harambase.common.HaramMessage;
import com.harambase.common.Page;
import com.harambase.support.util.SessionUtil;
import com.harambase.pioneer.pojo.Student;
import com.harambase.pioneer.service.StudentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linsh on 7/12/2017.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @RequiresPermissions({"admin", "student"})
    @RequestMapping(value = "/{studentId}/transcript", method = RequestMethod.GET)
    public ResponseEntity getTranscriptDetail(@PathVariable(value = "studentId") String studentid) {
        HaramMessage haramMessage = studentService.transcriptDetail(studentid);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions({"admin", "student", "teach"})
    @RequestMapping(value = "/{studentId}/available/credit", method = RequestMethod.GET)
    public ResponseEntity getAvailableCredit(@PathVariable(value = "studentId") String studentId){
        HaramMessage haramMessage = studentService.getAvailableCredit(studentId, SessionUtil.getPin().getInfo());
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions({"admin", "student"})
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Student student){
        HaramMessage haramMessage = studentService.update(student);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions({"admin", "teach", "system"})
    @RequestMapping(value = "/list", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start") Integer start,
                               @RequestParam(value = "length") Integer length,
                               @RequestParam(value = "draw") Integer draw,
                               @RequestParam(value = "search[value]") String search,
                               @RequestParam(value = "order[0][dir]") String order,
                               @RequestParam(value = "order[0][column]") String orderCol,
                               @RequestParam(value = "type", required = false) String type,
                               @RequestParam(value = "status", required = false) String status) {

        HaramMessage message = studentService.studentList(String.valueOf(start / length + 1), String.valueOf(length), search,
                order, orderCol, type, status);
        message.put("draw", draw);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        message.put("recordsFiltered", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
