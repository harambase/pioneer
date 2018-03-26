package com.harambase.pioneer.controller;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.helper.TokenHelper;
import com.harambase.pioneer.server.pojo.base.TempAdvise;
import com.harambase.pioneer.server.pojo.base.TempCourse;
import com.harambase.pioneer.server.pojo.base.TempUser;
import com.harambase.pioneer.service.RequestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    //    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserRequest(@PathVariable Integer id) {
        ResultMap message = requestService.getTempUser(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUserRequest(@PathVariable Integer id, @RequestBody TempUser tempUser, HttpServletRequest request) {
        tempUser.setOperatorId(TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        ResultMap message = requestService.updateTempUser(id, tempUser);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ResponseEntity registerNewUser(@RequestBody JSONObject jsonObject) {
        ResultMap ResultMap = requestService.registerNewUser(jsonObject);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTempUser(@PathVariable Integer id) {
        ResultMap ResultMap = requestService.deleteTempUser(id);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(value = "/user", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity userList(@RequestParam(value = "start") Integer start,
                                   @RequestParam(value = "length") Integer length,
                                   @RequestParam(value = "search") String search,
                                   @RequestParam(value = "order") String order,
                                   @RequestParam(value = "order") String orderCol,
                                   @RequestParam(value = "viewStatus") String viewStatus) {

        ResultMap message = requestService.tempUserList(start, length, search, order, orderCol, viewStatus);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/course/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateCourseRequest(@PathVariable Integer id, @RequestBody TempCourse tempCourse, HttpServletRequest request) {
        tempCourse.setOperatorId(TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        ResultMap message = requestService.updateTempCourse(id, tempCourse);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/course/{id}", method = RequestMethod.GET)
    public ResponseEntity getCourseRequest(@PathVariable Integer id) {
        ResultMap message = requestService.getTempCourse(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/course/register", method = RequestMethod.POST)
    public ResponseEntity registerNewCourse(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        String facultyId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        ResultMap ResultMap = requestService.registerNewCourse(facultyId, jsonObject);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/course/info/{id}", method = RequestMethod.PUT)
    public ResponseEntity uploadCourseInfo(@RequestParam MultipartFile file, @PathVariable Integer id) {
        ResultMap message = requestService.uploadCourseInfo(id, file);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/course/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTempCourse(@PathVariable Integer id) {
        ResultMap ResultMap = requestService.deleteTempCourse(id);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/course/info/{id}", method = RequestMethod.GET)
    public void downloadCourseInfo(@PathVariable Integer id, @RequestParam String token, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(token)) {
            ResultMap message = requestService.getTempCourse(id);
            JSONObject courseJson = JSONObject.parseObject(((TempCourse) message.getData()).getCourseJson());
            if (StringUtils.isNotEmpty(courseJson.getString("courseInfo"))) {
                JSONObject info = JSONObject.parseObject(courseJson.getString("courseInfo"));
                try {
                    FileUtil.downloadFile(info.getString("name"), info.getString("path"), response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/course", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity courseList(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                     @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                                     @RequestParam(value = "search", required = false, defaultValue = "") String search,
                                     @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                                     @RequestParam(value = "orderCol", required = false, defaultValue = "crn") String orderCol,
                                     @RequestParam(value = "viewStatus", required = false, defaultValue = "") String viewStatus,
                                     @RequestParam(value = "mode", required = false) String mode,
                                     HttpServletRequest request) {
        String facultyId = "";
        if (StringUtils.isNotEmpty(mode) && mode.equals("faculty"))
            facultyId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));

        ResultMap message = requestService.tempCourseList(start * length - 1, length, search, order, orderCol, viewStatus, facultyId);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/advise", method = RequestMethod.POST)
    public ResponseEntity newAdvisorRequest(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        ResultMap ResultMap = requestService.registerTempAdvise(TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)), jsonObject);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/advise/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeAdvisorRequest(@PathVariable Integer id) {
        ResultMap ResultMap = requestService.deleteTempAdviseById(id);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "student", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/advise/{id}", method = RequestMethod.GET)
    public ResponseEntity getAdviseRequest(@PathVariable Integer id) {
        ResultMap ResultMap = requestService.getTempAdvise(id);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/advise/{id}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity updateAdviseRequest(@PathVariable Integer id, @RequestBody TempAdvise tempAdvise) {
        ResultMap message = requestService.updateTempAdvise(id, tempAdvise);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "faculty", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/advise ", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity adviseList(@RequestParam(value = "start") Integer start,
                                     @RequestParam(value = "length") Integer length,
                                     @RequestParam(value = "search", required = false, defaultValue = "") String search,
                                     @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                                     @RequestParam(value = "orderCol", required = false, defaultValue = "0") String orderCol) {
        ResultMap message = requestService.tempAdviseList(start, length, search, order, orderCol);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
