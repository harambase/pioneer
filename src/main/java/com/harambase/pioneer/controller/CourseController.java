package com.harambase.pioneer.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Course;
import com.harambase.pioneer.pojo.dto.Option;
import com.harambase.pioneer.service.CourseService;
import com.harambase.support.util.FileUtil;
import com.harambase.support.util.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        HaramMessage haramMessage = courseService.create(course);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "crn") String crn) {
        HaramMessage haramMessage = courseService.delete(crn);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String crn, @RequestBody Course course) {
        HaramMessage haramMessage = courseService.update(crn, course);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

//    @RequiresPermissions("user")
    @RequestMapping(value = "/{crn}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable("crn") String crn) {
        HaramMessage haramMessage = courseService.getCourseByCrn(crn);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

//    @RequiresPermissions("user")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                               @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                               @RequestParam(value = "draw", required = false, defaultValue = "1") Integer draw,
                               @RequestParam(value = "search[value]", required = false, defaultValue = "") String search,
                               @RequestParam(value = "order[0][dir]", required = false, defaultValue = "") String order,
                               @RequestParam(value = "order[0][column]", required = false, defaultValue = "") String orderCol,
                               @RequestParam(value = "mode", required = false) String mode,
                               @RequestParam(value = "info", required = false, defaultValue = "") String info,
                               @RequestParam(value = "facultyId", required = false, defaultValue = "") String facultyId) {

        if (StringUtils.isNotEmpty(mode) && mode.equals("faculty"))
            facultyId = SessionUtil.getUserId();
        if (StringUtils.isNotEmpty(mode) && mode.equals("choose") && SessionUtil.getPin() != null)
            info = (String) SessionUtil.getPin().get("info");

        HaramMessage message = courseService.courseList(start, length, search, order, orderCol, facultyId, info);
        message.put("draw", draw);
        message.put("recordsTotal", ((LinkedHashMap) message.get("page")).get("totalRows"));
        message.put("recordsFiltered", ((LinkedHashMap) message.get("page")).get("totalRows"));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "teach", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/student/{crn}", method = RequestMethod.GET)
    public ResponseEntity studentList(@PathVariable String crn,
                                      @RequestParam(required = false, defaultValue = "") String search) {
        HaramMessage message = courseService.studentList(crn, search);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequiresPermissions("user")
    @RequestMapping(value = "/zTree/list", method = RequestMethod.GET)
    public ResponseEntity zTreeList(@RequestParam(value = "mode", required = false) String mode) {

        String facultyId = "";
        String info = "";
        if (mode != null && mode.equals("faculty"))
            facultyId = SessionUtil.getUserId();
        if (mode != null && mode.equals("choose"))
            info = (String) SessionUtil.getPin().get("info");

        HaramMessage message = courseService.courseTreeList(facultyId, info);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequiresPermissions("user")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity search(@RequestParam(required = false, defaultValue = "") String search,
                                 @RequestParam(required = false, defaultValue = "") String status) {
        HaramMessage haramMessage = courseService.getCourseBySearch(search, status);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

//    @RequiresPermissions("user")
    @RequestMapping(value = "/{crn}/precourse", method = RequestMethod.GET)
    public ResponseEntity preCourseList(@PathVariable("crn") String crn) {
        HaramMessage haramMessage = courseService.preCourseList(crn);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}/student/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity removeStuFromCourse(@PathVariable(value = "crn") String crn,
                                              @PathVariable(value = "userId") String studentId) {
        HaramMessage haramMessage = courseService.removeStuFromCou(crn, studentId);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}/student/{userId}", method = RequestMethod.PUT)
    public ResponseEntity addStudent2Course(@PathVariable(value = "crn") String crn,
                                            @PathVariable(value = "userId") String studentId,
                                            @RequestBody Option option) {
        HaramMessage haramMessage = courseService.addStu2Cou(crn, studentId, option);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}/faculty/{userId}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity assignFac2Course(@PathVariable(value = "crn") String crn,
                                           @PathVariable(value = "userId") String facultyId) {
        HaramMessage haramMessage = courseService.assignFac2Cou(crn, facultyId);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/choose", method = RequestMethod.POST)
    public ResponseEntity courseChoice(@RequestBody JSONObject choiceList) {
        HaramMessage message = courseService.reg2Course(SessionUtil.getUserId(), choiceList);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity courseInfoList(@RequestParam(required = false, defaultValue = "") String search) {
        HaramMessage message = courseService.courseInfoList(search);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/info/{crn}", method = RequestMethod.PUT)
    public ResponseEntity uploadInfo(@RequestParam MultipartFile file, @PathVariable String crn) {
        HaramMessage message = courseService.uploadInfo(crn, file);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/info/{crn}", method = RequestMethod.GET)
    public void downloadCourseInfo(@PathVariable String crn, HttpServletResponse response) {
        HaramMessage message = courseService.getCourseByCrn(crn);
        String courseInfo = (String) ((LinkedHashMap) message.getData()).get("courseInfo");
        if (StringUtils.isNotEmpty(courseInfo)) {
            JSONObject info = JSONObject.parseObject(courseInfo);
            FileUtil.downloadFile(info.getString("name"), info.getString("path"), response);
        }
    }

//    @RequiresPermissions(value = {"admin", "teach", "faculty", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/assignment/{crn}", method = RequestMethod.GET)
    public ResponseEntity getAssignmentList(@PathVariable String crn) {
        HaramMessage message = courseService.getAssignmentList(crn);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/assignment/{crn}", method = RequestMethod.PUT)
    public ResponseEntity updateAssignment(@PathVariable String crn, @RequestParam JSONArray assignment) {
        HaramMessage message = courseService.updateAssignment(crn, assignment);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/assignment/attachment/{crn}", method = RequestMethod.PUT)
    public ResponseEntity uploadAssignmentAttachment(@PathVariable String crn, @RequestParam MultipartFile multipartFile) {
        HaramMessage message = courseService.uploadAssignmentAttachment(crn, multipartFile);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/assignment/student/attachment/{crn}", method = RequestMethod.PUT)
    public ResponseEntity submitAssignment(@PathVariable String crn,
                                           @RequestParam String assignmentName,
                                           @RequestParam String createTime,
                                           @RequestParam MultipartFile multipartFile) {
        HaramMessage message = courseService.submitAssignment(crn, assignmentName, createTime, SessionUtil.getUserId(), multipartFile);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
