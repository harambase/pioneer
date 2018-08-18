package com.harambase.pioneer.controller;

import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.helper.TokenHelper;
import com.harambase.pioneer.server.pojo.base.Student;
import com.harambase.pioneer.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @PreAuthorize("hasAnyRole('TEACH','STUDENT','ADMIN')")
    @RequestMapping(value = "/{studentId}/transcript", method = RequestMethod.GET)
    public ResponseEntity getTranscriptDetail(@PathVariable(value = "studentId") String studentid) {
        ResultMap resultMap = studentService.transcriptDetail(studentid);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','STUDENT','ADMIN')")
    @RequestMapping(value = "/{studentId}/available/credit", method = RequestMethod.GET)
    public ResponseEntity getAvailableCredit(@PathVariable(value = "studentId") String studentId,
                                             @RequestParam(value = "info") String info) {
        ResultMap resultMap = studentService.getAvailableCredit(studentId, info);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','STUDENT','ADMIN')")
    @RequestMapping(value = "/{studentId}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String studentId, @RequestBody Student student) {
        ResultMap resultMap = studentService.update(studentId, student);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','STUDENT','ADMIN')")
    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                               @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                               @RequestParam(value = "search", required = false, defaultValue = "") String search,
                               @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                               @RequestParam(value = "orderCol", required = false, defaultValue = "student_id") String orderCol,
                               @RequestParam(value = "status", required = false) String status) {
        search = search.replace("'", "");
        ResultMap message = studentService.studentList(start * length - 1, length, search, order, orderCol, status);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','STUDENT','ADMIN')")
    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public ResponseEntity courseList(@RequestParam(value = "status", required = false, defaultValue = "") String status,
                                     HttpServletRequest request) {
        ResultMap message = studentService.courseList(status, TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
