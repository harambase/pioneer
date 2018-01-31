package com.harambase.pioneer.server;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.common.constant.FlagDict;
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

    public HaramMessage getUserRequest(Integer id) {
        return tempUserService.get(id);
    }

    public HaramMessage updateRequest(Integer id, TempUser tempUser) {
        if (tempUser.getStatus().equals("1")) {
            HaramMessage message = personService.addUser(JSONObject.parseObject(tempUser.getUserJson(), Person.class));
            if (message.getCode() == FlagDict.SUCCESS.getV()) {
                return tempUserService.updateTempUser(id, tempUser);
            }
        }
        return tempUserService.updateTempUser(id, tempUser);
    }

    public HaramMessage register(JSONObject jsonObject) {
        return tempUserService.register(jsonObject);
    }

    public HaramMessage removeUserRequest(Integer id) {
        return tempUserService.deleteTempUserById(id);
    }

    public HaramMessage userList(Integer start, Integer length, String search,
                                 String order, String orderCol, String status) {
        return tempUserService.tempUserList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, status);
    }

    public HaramMessage getCourseRequest(Integer id) {
        return tempCourseService.get(id);
    }

    public HaramMessage updateCourseRequest(Integer id, TempCourse tempCourse) {
        if (tempCourse.getStatus().equals("1")) {
            HaramMessage message = courseService.addCourse(JSONObject.parseObject(tempCourse.getCourseJson(), Course.class));
            if (message.getCode() == FlagDict.SUCCESS.getV()) {
                return tempCourseService.updateTempCourse(id, tempCourse);
            }
        }
        return tempCourseService.updateTempCourse(id, tempCourse);
    }

    public HaramMessage registerNewCourse(String facultyId, JSONObject jsonObject) {
        return tempCourseService.register(facultyId, jsonObject);
    }

    public HaramMessage removeCourseRequest(Integer id) {
        return tempCourseService.deleteTempCourseById(id);
    }

    public HaramMessage courseList(Integer start, Integer length, String search,
                                   String order, String orderCol, String status, String facultyId) {
        return tempCourseService.tempCourseList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, status, facultyId);
    }

    public HaramMessage newAdvisorRequest(String studentId, JSONObject jsonObject) {
        return tempAdviseService.register(studentId, jsonObject);
    }

    public HaramMessage removeAdvisorRequest(Integer id) {
        return tempAdviseService.deleteTempAdviseById(id);
    }

    public HaramMessage getAdviseRequest(Integer id) {
        return tempAdviseService.get(id);
    }

    public HaramMessage updateAdviseRequest(Integer id, TempAdvise tempAdvise) {
        return tempAdviseService.updateTempAdvise(id, tempAdvise);
    }

    public HaramMessage adviseList(@RequestParam(value = "start") Integer start,
                                   @RequestParam(value = "length") Integer length,
                                   @RequestParam(value = "search", required = false, defaultValue = "") String search,
                                   @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                                   @RequestParam(value = "orderCol", required = false, defaultValue = "0") String orderCol) {
        return tempAdviseService.tempAdviseList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol);
    }

}
