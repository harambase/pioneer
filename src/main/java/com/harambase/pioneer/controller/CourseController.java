package com.harambase.pioneer.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.helper.TokenHelper;
import com.harambase.pioneer.server.pojo.base.Course;
import com.harambase.pioneer.server.pojo.dto.Option;
import com.harambase.pioneer.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@RestController
@CrossOrigin
@RequestMapping(value = "/course")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Course course) {
        ResultMap ResultMap = courseService.create(course);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "crn") String crn) {
        ResultMap ResultMap = courseService.delete(crn);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String crn, @RequestBody Course course) {
        ResultMap ResultMap = courseService.update(crn, course);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions("user")
    @RequestMapping(value = "/{crn}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable("crn") String crn) {
        ResultMap ResultMap = courseService.getCourseByCrn(crn);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                               @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                               @RequestParam(value = "search", required = false, defaultValue = "") String search,
                               @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                               @RequestParam(value = "orderCol", required = false, defaultValue = "crn") String orderCol,
                               @RequestParam(value = "mode", required = false) String mode,
                               @RequestParam(value = "info", required = false, defaultValue = "") String info,
                               @RequestParam(value = "facultyId", required = false, defaultValue = "") String facultyId,
                               HttpServletRequest request) {

        if (StringUtils.isNotEmpty(mode) && mode.equals("faculty"))
            facultyId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        ResultMap message = courseService.courseList(start * length - 1, length, search, order, orderCol, facultyId, info);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/student/{crn}", method = RequestMethod.GET)
    public ResponseEntity studentList(@PathVariable String crn,
                                      @RequestParam(required = false, defaultValue = "") String search) {
        ResultMap message = courseService.studentList(crn, search);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions("user")
    @RequestMapping(value = "/zTree/list", method = RequestMethod.GET)
    public ResponseEntity zTreeList(@RequestParam(value = "mode", required = false) String mode,
                                    @RequestParam(value = "info", required = false) String info,
                                    HttpServletRequest request) {

        String facultyId = "";
        if (mode != null && mode.equals("faculty"))
            facultyId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));

        ResultMap message = courseService.courseTreeList(facultyId, info);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions("user")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity search(@RequestParam(required = false, defaultValue = "") String search,
                                 @RequestParam(required = false, defaultValue = "") String status) {
        ResultMap ResultMap = courseService.getCourseBySearch(search, status);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions("user")
    @RequestMapping(value = "/{crn}/precourse", method = RequestMethod.GET)
    public ResponseEntity preCourseList(@PathVariable("crn") String crn) {
        ResultMap ResultMap = courseService.preCourseList(crn);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}/student/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity removeStuFromCourse(@PathVariable(value = "crn") String crn,
                                              @PathVariable(value = "userId") String studentId) {
        ResultMap ResultMap = courseService.removeStuFromCou(crn, studentId);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}/student/{userId}", method = RequestMethod.PUT)
    public ResponseEntity addStudent2Course(@PathVariable(value = "crn") String crn,
                                            @PathVariable(value = "userId") String studentId,
                                            @RequestBody Option option) {
        ResultMap ResultMap = courseService.addStu2Cou(crn, studentId, option);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}/faculty/{userId}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity assignFac2Course(@PathVariable(value = "crn") String crn,
                                           @PathVariable(value = "userId") String facultyId) {
        ResultMap ResultMap = courseService.assignFac2Cou(crn, facultyId);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/choose", method = RequestMethod.POST)
    public ResponseEntity courseChoice(@RequestBody JSONObject choiceList, HttpServletRequest request) {
        ResultMap message = courseService.reg2Course(TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)), choiceList);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity courseInfoList(@RequestParam(required = false, defaultValue = "") String search) {
        ResultMap message = courseService.courseInfoList(search);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/info/{crn}", method = RequestMethod.PUT)
    public ResponseEntity uploadInfo(@RequestParam MultipartFile file, @PathVariable String crn) {
        ResultMap message = courseService.uploadInfo(crn, file);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/info/{crn}", method = RequestMethod.GET)
    public void downloadCourseInfo(@PathVariable String crn, HttpServletResponse response) {
        ResultMap message = courseService.getCourseByCrn(crn);
        String courseInfo = (String) ((LinkedHashMap) message.getData()).get("courseInfo");
        if (StringUtils.isNotEmpty(courseInfo)) {
            JSONObject info = JSONObject.parseObject(courseInfo);
            try {
                FileUtil.downloadFile(info.getString("name"), info.getString("path"), response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/assignment/{crn}", method = RequestMethod.GET)
    public ResponseEntity getAssignmentList(@PathVariable String crn) {
        ResultMap message = courseService.getAssignmentList(crn);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/assignment/{crn}", method = RequestMethod.PUT)
    public ResponseEntity updateAssignment(@PathVariable String crn, @RequestParam JSONArray assignment) {
        ResultMap message = courseService.updateAssignment(crn, assignment);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/assignment/attachment/{crn}", method = RequestMethod.PUT)
    public ResponseEntity uploadAssignmentAttachment(@PathVariable String crn, @RequestParam MultipartFile multipartFile) {
        ResultMap message = courseService.uploadAssignmentAttachment(crn, multipartFile);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/assignment/student/attachment/{crn}", method = RequestMethod.PUT)
    public ResponseEntity submitAssignment(@PathVariable String crn,
                                           @RequestParam String assignmentName,
                                           @RequestParam String createTime,
                                           @RequestParam MultipartFile multipartFile,
                                           HttpServletRequest request) {
        ResultMap message = courseService.submitAssignment(crn, assignmentName, createTime, TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)), multipartFile);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
