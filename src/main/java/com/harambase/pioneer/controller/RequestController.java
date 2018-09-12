package com.harambase.pioneer.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Config;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

    @PreAuthorize("hasAnyRole('ADMIN', 'SYSTEM')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserRequest(@PathVariable Integer id) {
        ResultMap resultMap = requestService.getTempUser(id);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SYSTEM')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUserRequest(@PathVariable Integer id, @RequestBody TempUser tempUser, HttpServletRequest request) {
        tempUser.setOperatorId(TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        ResultMap resultMap = requestService.updateTempUser(id, tempUser);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ResponseEntity registerNewUser(@RequestBody JSONObject jsonObject) {
        ResultMap ResultMap = requestService.registerNewUser(jsonObject);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SYSTEM')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTempUser(@PathVariable Integer id) {
        ResultMap ResultMap = requestService.deleteTempUser(id);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SYSTEM')")
    @RequestMapping(value = "/user", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity userList(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                   @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                                   @RequestParam(value = "search", required = false, defaultValue = "") String search,
                                   @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                                   @RequestParam(value = "orderCol", required = false, defaultValue = "user_id") String orderCol,
                                   @RequestParam(value = "viewStatus", required = false, defaultValue = "") String viewStatus) {

        ResultMap resultMap = requestService.tempUserList(start * length - 1, length, search, order, orderCol, viewStatus);
        resultMap.put("recordsTotal", ((Page) resultMap.get("page")).getTotalRows());
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SYSTEM')")
    @RequestMapping(value = "/user/profile/{id}", method = RequestMethod.PUT)
    public ResponseEntity uploadProfile(@RequestParam(value = "file", required = false) MultipartFile file,
                                        @PathVariable Integer id) {
        ResultMap resultMap = requestService.tempUserUpload(id, file, "p");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SYSTEM')")
    @RequestMapping(value = "/user/info/{id}", method = RequestMethod.PUT)
    public ResponseEntity uploadInfo(@RequestParam(value = "file", required = false) MultipartFile file,
                                     @PathVariable Integer id) {
        ResultMap resultMap = requestService.tempUserUpload(id, file, "f");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/info/{id}", method = RequestMethod.GET)
    public void downloadUserInfo(@PathVariable(value = "id") Integer id, HttpServletResponse response) {
        ResultMap resultMap = requestService.getTempUser(id);
        String userInfo = JSONObject.parseObject(((TempUser) resultMap.getData()).getUserJson()).getString("userInfo");
        if (StringUtils.isNotEmpty(userInfo)) {
            JSONObject info = JSONObject.parseObject(userInfo);
            try {
                FileUtil.downloadFileFromFtpServer(response, info.getString("name"), info.getString("path"), Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACH', 'ADVISOR')")
    @RequestMapping(value = "/course/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateCourseRequest(@PathVariable Integer id, @RequestBody TempCourse tempCourse, HttpServletRequest request) {
        tempCourse.setOperatorId(TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        ResultMap resultMap = requestService.updateTempCourse(id, tempCourse);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACH', 'ADVISOR')")
    @RequestMapping(value = "/course/{id}", method = RequestMethod.GET)
    public ResponseEntity getCourseRequest(@PathVariable Integer id) {
        ResultMap resultMap = requestService.getTempCourse(id);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACH', 'ADVISOR')")
    @RequestMapping(value = "/course/register", method = RequestMethod.POST)
    public ResponseEntity registerNewCourse(@RequestBody JSONObject jsonObject, HttpServletRequest request) {
        String applicantId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        ResultMap ResultMap = requestService.registerNewCourse(applicantId, jsonObject);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACH', 'ADVISOR')")
    @RequestMapping(value = "/course/info/{id}", method = RequestMethod.PUT)
    public ResponseEntity uploadCourseInfo(@RequestParam MultipartFile file, @PathVariable Integer id) {
        ResultMap resultMap = requestService.uploadCourseInfo(id, file);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACH', 'ADVISOR')")
    @RequestMapping(value = "/course/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTempCourse(@PathVariable Integer id) {
        ResultMap ResultMap = requestService.deleteTempCourse(id);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/course/info/{id}", method = RequestMethod.GET)
    public void downloadCourseInfo(@PathVariable Integer id, @RequestParam String token, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(token)) {
            ResultMap resultMap = requestService.getTempCourse(id);
            JSONObject courseJson = JSONObject.parseObject(((TempCourse) resultMap.getData()).getCourseJson());
            if (StringUtils.isNotEmpty(courseJson.getString("courseInfo"))) {
                JSONObject info = JSONObject.parseObject(courseJson.getString("courseInfo"));
                try {
                    FileUtil.downloadFileFromFtpServer(response, info.getString("name"), info.getString("path"), Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACH', 'ADVISOR')")
    @RequestMapping(value = "/course", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity courseList(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                     @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                                     @RequestParam(value = "search", required = false, defaultValue = "") String search,
                                     @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                                     @RequestParam(value = "orderCol", required = false, defaultValue = "crn") String orderCol,
                                     @RequestParam(value = "viewStatus", required = false, defaultValue = "") String viewStatus,
                                     @RequestParam(value = "mode", required = false) String mode,
                                     HttpServletRequest request) {
        search = search.replace("'", "");
        String advisorId = "";
        if (StringUtils.isNotEmpty(mode) && mode.equals("ADVISOR"))
            advisorId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));

        ResultMap resultMap = requestService.tempCourseList(start * length - 1, length, search, order, orderCol, viewStatus, advisorId);
        resultMap.put("recordsTotal", ((Page) resultMap.get("page")).getTotalRows());
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT', 'TEACH', 'ADVISOR')")
    @RequestMapping(value = "/advise/{studentId}", method = RequestMethod.POST)
    public ResponseEntity newAdvisorRequest(@PathVariable(value = "studentId") String studentId,
                                            @RequestBody TempAdvise tempAdvise) {
        tempAdvise.setStudentId(studentId);
        tempAdvise.setOperatorId(studentId);
        ResultMap resultMap = requestService.registerTempAdvise(tempAdvise);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACH')")
    @RequestMapping(value = "/advise/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeAdvisorRequest(@PathVariable Integer id) {
        ResultMap ResultMap = requestService.deleteTempAdviseById(id);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT', 'TEACH', 'ADVISOR')")
    @RequestMapping(value = "/advise/{studentId}", method = RequestMethod.GET)
    public ResponseEntity getAdviseRequest(@PathVariable String studentId) {
        ResultMap ResultMap = requestService.getTempAdvise(studentId);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT', 'TEACH', 'ADVISOR')")
    @RequestMapping(value = "/advise/{id}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity updateAdviseRequest(@PathVariable Integer id, @RequestBody TempAdvise tempAdvise, HttpServletRequest request) {
        tempAdvise.setOperatorId(TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        ResultMap resultMap = requestService.updateTempAdvise(id, tempAdvise);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT', 'TEACH', 'ADVISOR')")
    @RequestMapping(value = "/advise/{id}/{choice}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity assignAdvisor(@PathVariable Integer id, @RequestBody TempAdvise tempAdvise,
                                        @PathVariable String choice, HttpServletRequest request) {
        tempAdvise.setOperatorId(TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        ResultMap resultMap = requestService.assignAdvisor(id, tempAdvise, choice);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT', 'TEACH', 'ADVISOR')")
    @RequestMapping(value = "/advise", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity adviseList(@RequestParam(value = "start") Integer start,
                                     @RequestParam(value = "length") Integer length,
                                     @RequestParam(value = "search", required = false, defaultValue = "") String search,
                                     @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                                     @RequestParam(value = "orderCol", required = false, defaultValue = "id") String orderCol,
                                     @RequestParam(value = "viewStatus", required = false, defaultValue = "") String viewStatus,
                                     @RequestParam(value = "info", required = false, defaultValue = "") String info,
                                     @RequestParam(value = "studentId", required = false, defaultValue = "") String studentId,
                                     @RequestParam(value = "facultyId", required = false, defaultValue = "") String facultyId
    ) {
        ResultMap resultMap;
        search = search.replace("'", "");
        if (StringUtils.isNotEmpty(studentId) || StringUtils.isNotEmpty(facultyId) || StringUtils.isNotEmpty(info)) {
            resultMap = requestService.tempAdviseList(start * length - 1, length, search, order, orderCol, viewStatus, info, studentId, facultyId);
            resultMap.put("recordsTotal", ((Page) resultMap.get("page")).getTotalRows());
        } else {
            resultMap = new ResultMap();
            resultMap.setData(new ArrayList<>());
            resultMap.put("recordsTotal", 0);
        }
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
