package com.harambase.pioneer.server;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.server.pojo.base.*;
import com.harambase.pioneer.server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class RequestServer {

    private final TempUserService tempUserService;
    private final PersonService personService;
    private final TempCourseService tempCourseService;
    private final CourseService courseService;
    private final TempAdviseService tempAdviseService;

    @Autowired
    public RequestServer(TempUserService tempUserService, PersonService personService,
                         TempCourseService tempCourseService, CourseService courseService,
                         TempAdviseService tempAdviseService) {
        this.tempUserService = tempUserService;
        this.personService = personService;
        this.tempCourseService = tempCourseService;
        this.courseService = courseService;
        this.tempAdviseService = tempAdviseService;
    }

    public ResultMap getUserRequest(Integer id) {
        return tempUserService.get(id);
    }

    public ResultMap updateRequest(Integer id, TempUser tempUser) {
        if (tempUser.getStatus().equals("1")) {
            ResultMap message = personService.addUser(JSONObject.parseObject(tempUser.getUserJson(), Person.class));
            if (message.getCode() == SystemConst.SUCCESS.getCode()) {
                return tempUserService.updateTempUser(id, tempUser);
            }
        }
        return tempUserService.updateTempUser(id, tempUser);
    }

    public ResultMap register(JSONObject jsonObject) {
        return tempUserService.register(jsonObject);
    }

    public ResultMap removeUserRequest(Integer id) {
        return tempUserService.deleteTempUserById(id);
    }

    public ResultMap userList(Integer start, Integer length, String search,
                                 String order, String orderCol, String status) {
        return tempUserService.tempUserList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, status);
    }

    public ResultMap getCourseRequest(Integer id) {
        return tempCourseService.get(id);
    }

    public ResultMap updateCourseRequest(Integer id, TempCourse tempCourse) {
        if (tempCourse.getStatus().equals("1")) {
            ResultMap message = courseService.addCourse(JSONObject.parseObject(tempCourse.getCourseJson(), Course.class));
            if (message.getCode() == SystemConst.SUCCESS.getCode()) {
                return tempCourseService.updateTempCourse(id, tempCourse);
            }
        }
        return tempCourseService.updateTempCourse(id, tempCourse);
    }

    public ResultMap registerNewCourse(String facultyId, JSONObject jsonObject) {
        return tempCourseService.register(facultyId, jsonObject);
    }

    public ResultMap removeCourseRequest(Integer id) {
        return tempCourseService.deleteTempCourseById(id);
    }

    public ResultMap courseList(Integer start, Integer length, String search,
                                   String order, String orderCol, String status, String facultyId) {
        return tempCourseService.tempCourseList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, status, facultyId);
    }

    public ResultMap newAdvisorRequest(String studentId, JSONObject jsonObject) {
        return tempAdviseService.register(studentId, jsonObject);
    }

    public ResultMap removeAdvisorRequest(Integer id) {
        return tempAdviseService.deleteTempAdviseById(id);
    }

    public ResultMap getAdviseRequest(Integer id) {
        return tempAdviseService.get(id);
    }

    public ResultMap updateAdviseRequest(Integer id, TempAdvise tempAdvise) {
        return tempAdviseService.updateTempAdvise(id, tempAdvise);
    }

    public ResultMap adviseList(@RequestParam(value = "start") Integer start,
                                   @RequestParam(value = "length") Integer length,
                                   @RequestParam(value = "search", required = false, defaultValue = "") String search,
                                   @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                                   @RequestParam(value = "orderCol", required = false, defaultValue = "0") String orderCol) {
        return tempAdviseService.tempAdviseList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol);
    }

}
