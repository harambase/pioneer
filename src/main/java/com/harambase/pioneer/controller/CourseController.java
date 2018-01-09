package com.harambase.pioneer.controller;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Course;
import com.harambase.pioneer.pojo.dto.Option;
import com.harambase.pioneer.service.CourseService;
import com.harambase.support.util.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Course course) {
        HaramMessage haramMessage = courseService.create(course);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "crn") String crn) {
        HaramMessage haramMessage = courseService.delete(crn);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String crn, @RequestBody Course course) {
        HaramMessage haramMessage = courseService.update(crn, course);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/{crn}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable("crn") String crn) {
        HaramMessage haramMessage = courseService.getCourseByCrn(crn);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start") Integer start,
                               @RequestParam(value = "length") Integer length,
                               @RequestParam(value = "draw") Integer draw,
                               @RequestParam(value = "search[value]") String search,
                               @RequestParam(value = "order[0][dir]") String order,
                               @RequestParam(value = "order[0][column]") String orderCol,
                               @RequestParam(value = "mode", required = false) String mode,
                               @RequestParam(value = "info", required = false, defaultValue = "") String info,
                               @RequestParam(value = "facultyId", required = false, defaultValue = "") String facultyId) {

        if (StringUtils.isNotEmpty(mode) && mode.equals("faculty"))
            facultyId = SessionUtil.getUserId();
        if (StringUtils.isNotEmpty(mode) && mode.equals("choose"))
            info = (String)SessionUtil.getPin().get("info");

        HaramMessage message = courseService.courseList(start, length, search, order, orderCol, facultyId, info);
        message.put("draw", draw);
        message.put("recordsTotal", ((LinkedHashMap) message.get("page")).get("totalRows"));
        message.put("recordsFiltered", ((LinkedHashMap) message.get("page")).get("totalRows"));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/zTree/list", method = RequestMethod.GET)
    public ResponseEntity zTreeList(@RequestParam(value = "mode", required = false) String mode) {

        String facultyId = "";
        String info = "";
        if (mode != null && mode.equals("faculty"))
            facultyId = SessionUtil.getUserId();
        if (mode != null && mode.equals("choose"))
            info = (String)SessionUtil.getPin().get("info");

        HaramMessage message = courseService.courseTreeList(facultyId, info);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity search(@RequestParam("search") String search,
                                 @RequestParam(value = "status", required = false, defaultValue = "") String status) {
        HaramMessage haramMessage = courseService.getCourseBySearch(search, status);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/{crn}/precourse", method = RequestMethod.GET)
    public ResponseEntity preCourseList(@PathVariable("crn") String crn) {
        HaramMessage haramMessage = courseService.preCourseList(crn);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}/student/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity removeStuFromCourse(@PathVariable(value = "crn") String crn,
                                              @PathVariable(value = "userId") String studentId) {
        HaramMessage haramMessage = courseService.removeStuFromCou(crn, studentId);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}/student/{userId}", method = RequestMethod.PUT)
    public ResponseEntity assignStu2Course(@PathVariable(value = "crn") String crn,
                                           @PathVariable(value = "userId") String studentId,
                                           @RequestBody Option option) {
        HaramMessage haramMessage = courseService.addStu2Cou(crn, studentId, option);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}/faculty/{userId}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity assignFac2Course(@PathVariable(value = "crn") String crn,
                                           @PathVariable(value = "userId") String facultyId) {
        HaramMessage haramMessage = courseService.assignFac2Cou(crn, facultyId);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/choose", method = RequestMethod.POST)
    public ResponseEntity courseChoice(@RequestParam(value = "choiceList[]") String[] choices) {
        HaramMessage message = courseService.reg2Course(SessionUtil.getUserId(), choices);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity courseInfoList(@RequestParam(required = false, defaultValue = "") String search){
        HaramMessage message = courseService.courseInfoList(search);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
        
    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/info/{crn}", method = RequestMethod.PUT)
    public ResponseEntity uploadInfo(@RequestParam MultipartFile file, @PathVariable String crn){
        HaramMessage message = courseService.uploadInfo(crn, file);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
