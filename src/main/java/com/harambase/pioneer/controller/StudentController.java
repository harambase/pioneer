package com.harambase.pioneer.controller;

import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.helper.TokenHelper;
import com.harambase.pioneer.server.pojo.base.Student;
import com.harambase.pioneer.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

/**
 * Created by linsh on 7/12/2017.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

//    @RequiresPermissions(value = {"admin", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/{studentId}/transcript", method = RequestMethod.GET)
    public ResponseEntity getTranscriptDetail(@PathVariable(value = "studentId") String studentid) {
        HaramMessage haramMessage = studentService.transcriptDetail(studentid);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "student", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{studentId}/available/credit", method = RequestMethod.GET)
    public ResponseEntity getAvailableCredit(@PathVariable(value = "studentId") String studentId) {
        HaramMessage haramMessage = studentService.getAvailableCredit(studentId, (String) SessionUtil.getPin().get("info"));
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/{studentId}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String studentId, @RequestBody Student student) {
        HaramMessage haramMessage = studentService.update(studentId, student);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "teach", "system"}, logical = Logical.OR)
    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start") Integer start,
                               @RequestParam(value = "length") Integer length,
                               @RequestParam(value = "draw") Integer draw,
                               @RequestParam(value = "search[value]") String search,
                               @RequestParam(value = "order[0][dir]") String order,
                               @RequestParam(value = "order[0][column]") String orderCol,
                               @RequestParam(value = "status", required = false) String status) {

        HaramMessage message = studentService.studentList(start, length, search, order, orderCol, status);
        message.put("draw", draw);
        message.put("recordsTotal", ((LinkedHashMap) message.get("page")).get("totalRows"));
        message.put("recordsFiltered", ((LinkedHashMap) message.get("page")).get("totalRows"));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "teach", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public ResponseEntity courseList(@RequestParam(value = "status", required = false, defaultValue = "") String status) {
        HaramMessage message = studentService.courseList(status, TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
