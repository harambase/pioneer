package com.harambase.pioneer.controller;

import com.alibaba.fastjson.JSONObject;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.TempCourse;
import com.harambase.pioneer.pojo.TempUser;
import com.harambase.pioneer.service.RequestService;
import com.harambase.support.util.FileUtil;
import com.harambase.support.util.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@RestController
@CrossOrigin
@RequestMapping("/request")

public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserRequest(@PathVariable Integer id) {
        HaramMessage message = requestService.getTempUser(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUserRequest(@PathVariable Integer id, @RequestBody TempUser tempUser) {
        tempUser.setOperatorId(SessionUtil.getUserId());
        HaramMessage message = requestService.updateTempUser(id, tempUser);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ResponseEntity registerNewUser(@RequestBody JSONObject jsonObject) {
        HaramMessage haramMessage = requestService.registerNewUser(jsonObject);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTempUser(@PathVariable Integer id) {
        HaramMessage haramMessage = requestService.deleteTempUser(id);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(value = "/user", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity userList(@RequestParam(value = "start") Integer start,
                                   @RequestParam(value = "length") Integer length,
                                   @RequestParam(value = "draw") Integer draw,
                                   @RequestParam(value = "search[value]") String search,
                                   @RequestParam(value = "order[0][dir]") String order,
                                   @RequestParam(value = "order[0][column]") String orderCol,
                                   @RequestParam(value = "viewStatus") String viewStatus) {

        HaramMessage message = requestService.tempUserList(start, length, search, order, orderCol, viewStatus);
        message.put("draw", draw);
        message.put("recordsTotal", ((LinkedHashMap) message.get("page")).get("totalRows"));
        message.put("recordsFiltered", ((LinkedHashMap) message.get("page")).get("totalRows"));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/course/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateCourseRequest(@PathVariable Integer id, @RequestBody TempCourse tempCourse) {
        tempCourse.setOperatorId(SessionUtil.getUserId());
        HaramMessage message = requestService.updateTempCourse(id, tempCourse);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/course/{id}", method = RequestMethod.GET)
    public ResponseEntity getCourseRequest(@PathVariable Integer id) {
        HaramMessage message = requestService.getTempCourse(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/course/register", method = RequestMethod.POST)
    public ResponseEntity registerNewCourse(@RequestBody JSONObject jsonObject) {
        String facultyId = SessionUtil.getUserId();
        HaramMessage haramMessage = requestService.registerNewCourse(facultyId, jsonObject);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/course/info/{id}", method = RequestMethod.PUT)
    public ResponseEntity uploadCourseInfo(@RequestParam MultipartFile file, @PathVariable Integer id) {
        HaramMessage message = requestService.uploadCourseInfo(id, file);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/course/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTempCourse(@PathVariable Integer id) {
        HaramMessage haramMessage = requestService.deleteTempCourse(id);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/course/info/{id}", method = RequestMethod.GET)
    public void downloadCourseInfo(@PathVariable Integer id, HttpServletResponse response) {
        HaramMessage message = requestService.getTempCourse(id);
        JSONObject courseJson = JSONObject.parseObject((String) ((LinkedHashMap) message.getData()).get("courseJson"));
        if (StringUtils.isNotEmpty(courseJson.getString("courseInfo"))) {
            JSONObject info = JSONObject.parseObject(courseJson.getString("courseInfo"));
            FileUtil.downloadFile(info.getString("name"), info.getString("path"), response);
        }
    }

    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/course", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity courseList(@RequestParam(value = "start") Integer start,
                                     @RequestParam(value = "length") Integer length,
                                     @RequestParam(value = "draw") Integer draw,
                                     @RequestParam(value = "search[value]") String search,
                                     @RequestParam(value = "order[0][dir]") String order,
                                     @RequestParam(value = "order[0][column]") String orderCol,
                                     @RequestParam(value = "viewStatus") String viewStatus,
                                     @RequestParam(value = "mode", required = false) String mode) {
        String facultyId = "";
        if (StringUtils.isNotEmpty(mode) && mode.equals("faculty"))
            facultyId = SessionUtil.getUserId();

        HaramMessage message = requestService.tempCourseList(start, length, search, order, orderCol, viewStatus, facultyId);
        message.put("draw", draw);
        message.put("recordsTotal", ((LinkedHashMap) message.get("page")).get("totalRows"));
        message.put("recordsFiltered", ((LinkedHashMap) message.get("page")).get("totalRows"));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
