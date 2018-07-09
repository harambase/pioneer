package com.harambase.pioneer.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.Tags;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.helper.TokenHelper;
import com.harambase.pioneer.server.pojo.base.Student;
import com.harambase.pioneer.server.pojo.view.CourseView;
import com.harambase.pioneer.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //    @RequiresPermissions(value = {"admin", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/{studentId}/transcript", method = RequestMethod.GET)
    public ResponseEntity getTranscriptDetail(@PathVariable(value = "studentId") String studentid) {
        ResultMap haramMessage = studentService.transcriptDetail(studentid);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "student", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{studentId}/available/credit", method = RequestMethod.GET)
    public ResponseEntity getAvailableCredit(@PathVariable(value = "studentId") String studentId,
                                             @RequestParam(value = "info") String info) {
        ResultMap haramMessage = studentService.getAvailableCredit(studentId, info);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/{studentId}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String studentId, @RequestBody Student student) {
        ResultMap haramMessage = studentService.update(studentId, student);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "system"}, logical = Logical.OR)
    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                               @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                               @RequestParam(value = "search", required = false, defaultValue = "") String search,
                               @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                               @RequestParam(value = "orderCol", required = false, defaultValue = "student_id") String orderCol,
                               @RequestParam(value = "status", required = false) String status) {

        ResultMap message = studentService.studentList(start * length - 1, length, search, order, orderCol, status);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public ResponseEntity courseList(@RequestParam(value = "status", required = false, defaultValue = "") String status,
                                     HttpServletRequest request) {
        ResultMap message = studentService.courseList(status, TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{studentId}/contract", method = RequestMethod.GET)
    public ResponseEntity getContract(@PathVariable(value = "studentId") String studentId) {
        ResultMap resultMap = studentService.getContract(studentId);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{studentId}/contract", method = RequestMethod.POST)
    public ResponseEntity updateContract(@PathVariable(value = "studentId") String studentId,
                                         @RequestBody String contractString) {
        ResultMap resultMap = studentService.updateContract(studentId, contractString);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN','FACULTY')")
    @RequestMapping(value = {"/{studentId}/contract", "/contract"}, method = RequestMethod.GET)
    public void downloadCourseInfo(@PathVariable String studentId, @RequestParam String token, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(token)) {
            ResultMap resultMap = studentService.downloadContractById(studentId);
            try {
                FileUtil.downloadFile(studentId + "导师表.csv", (String) resultMap.getData(), response);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
