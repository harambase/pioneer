package com.harambase.pioneer.server;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.IDUtil;
import com.harambase.pioneer.helper.MessageSender;
import com.harambase.pioneer.server.pojo.base.*;
import com.harambase.pioneer.server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestServer {

    private final TempUserServerService tempUserServerService;
    private final PersonServerService personServerService;
    private final TempCourseServerService tempCourseServerService;
    private final CourseServerService courseServerService;
    private final TempAdviseServerService tempAdviseServerService;
    private final AdviseServerService adviseServerService;

    private final MessageSender messageSender;

    @Autowired
    public RequestServer(TempUserServerService tempUserServerService, PersonServerService personServerService,
                         TempCourseServerService tempCourseServerService, CourseServerService courseServerService,
                         TempAdviseServerService tempAdviseServerService, MessageSender messageSender,
                         AdviseServerService adviseServerService) {
        this.tempUserServerService = tempUserServerService;
        this.personServerService = personServerService;
        this.tempCourseServerService = tempCourseServerService;
        this.courseServerService = courseServerService;
        this.tempAdviseServerService = tempAdviseServerService;
        this.messageSender = messageSender;
        this.adviseServerService = adviseServerService;
    }

    public ResultMap getUserRequest(Integer id) {
        return tempUserServerService.get(id);
    }

    public ResultMap register(JSONObject jsonObject) {
        ResultMap resultMap = tempUserServerService.register(jsonObject);
        if (resultMap.getCode() == SystemConst.SUCCESS.getCode()) {
            messageSender.sendToAllSystem((TempUser) resultMap.getData());
        }
        return resultMap;
    }

    public ResultMap removeUserRequest(Integer id) {
        return tempUserServerService.deleteTempUserById(id);
    }

    public ResultMap userList(Integer start, Integer length, String search,
                              String order, String orderCol, String status) {
        return tempUserServerService.tempUserList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, status);
    }

    public ResultMap getCourseRequest(Integer id) {
        return tempCourseServerService.get(id);
    }

    public ResultMap registerNewCourse(String facultyId, JSONObject jsonObject) {
        return tempCourseServerService.register(facultyId, jsonObject);
    }

    public ResultMap removeCourseRequest(Integer id) {
        return tempCourseServerService.deleteTempCourseById(id);
    }

    public ResultMap courseList(Integer start, Integer length, String search,
                                String order, String orderCol, String status, String facultyId) {
        return tempCourseServerService.tempCourseList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, status, facultyId);
    }

    public ResultMap newAdvisorRequest(TempAdvise tempAdvise) {
        //todo:向教务发送消息
        return tempAdviseServerService.register(tempAdvise);
    }

    public ResultMap removeAdvisorRequest(Integer id) {
        return tempAdviseServerService.deleteTempAdviseById(id);
    }

    public ResultMap getAdviseRequest(String studentId) {
        return tempAdviseServerService.get(studentId);
    }

    public ResultMap updateAdviseRequest(Integer id, TempAdvise tempAdvise) {
        return tempAdviseServerService.updateTempAdvise(id, tempAdvise);
    }

    public ResultMap adviseList(Integer start, Integer length, String search, String order, String orderCol,
                                String viewStatus, String info, String studentId, String facultyId) {
        return tempAdviseServerService.tempAdviseList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, viewStatus, info, studentId, facultyId);
    }
}
