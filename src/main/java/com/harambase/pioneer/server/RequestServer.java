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
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class RequestServer {

    private final TempUserService tempUserService;
    private final PersonService personService;
    private final TempCourseService tempCourseService;
    private final CourseService courseService;
    private final TempAdviseService tempAdviseService;

    private final MessageSender messageSender;

    @Autowired
    public RequestServer(TempUserService tempUserService, PersonService personService,
                         TempCourseService tempCourseService, CourseService courseService,
                         TempAdviseService tempAdviseService, MessageSender messageSender) {
        this.tempUserService = tempUserService;
        this.personService = personService;
        this.tempCourseService = tempCourseService;
        this.courseService = courseService;
        this.tempAdviseService = tempAdviseService;
        this.messageSender = messageSender;
    }

    public ResultMap getUserRequest(Integer id) {
        return tempUserService.get(id);
    }

    public ResultMap updateRequest(Integer id, TempUser tempUser) {
        ResultMap message;
        if (tempUser.getStatus().equals("1")) {
            Person newUser = JSONObject.parseObject(tempUser.getUserJson(), Person.class);
            message = personService.addUser(newUser);
            if (message.getCode() == SystemConst.SUCCESS.getCode()) {
                Person person = ((Person) message.getData());
                String info = person.getLastName() + ", " + person.getFirstName() + "(" + person.getUserId() + ")";
                message = tempUserService.updateTempUser(id, tempUser);

                if (message.getCode() == SystemConst.SUCCESS.getCode()) {
                    messageSender.sendImportantSystemMsg(person.getUserId(), tempUser.getOperatorId(),
                            "您接收到来自系统的一条消息:您的账户已创建！欢迎来到先锋！", "用户创建", "用户申请");
                    messageSender.sendImportantSystemMsg(tempUser.getOperatorId(), IDUtil.ROOT,
                            "您接收到来自系统的一条消息:来自用户 " + info + " 批准已通过！", "批准操作成功", "用户申请");
                }
            }
        } else if (tempUser.getStatus().equals("-1")) {
            message = tempUserService.updateTempUser(id, tempUser);
            JSONObject jsonObject = JSONObject.parseObject(tempUser.getUserJson());
            String info = jsonObject.getString("lastName") + ", " + jsonObject.get("firstName") + "(" + tempUser.getUserId() + ")";
            if (message.getCode() == SystemConst.SUCCESS.getCode()) {
                messageSender.sendImportantSystemMsg(tempUser.getOperatorId(), IDUtil.ROOT,
                        "您接收到来自系统的一条消息:来自用户 " + info + " 批准已拒绝！", "拒绝操作成功", "用户申请");
            } else {
                messageSender.sendImportantSystemMsg(tempUser.getOperatorId(), IDUtil.ROOT,
                        "您接收到来自系统的一条消息:来自用户 " + info + " 批准已拒绝！", "拒绝操作失败", "用户申请");
            }

        } else {
            message = tempUserService.updateTempUser(id, tempUser);
        }

        return message;
    }

    public ResultMap register(JSONObject jsonObject) {
        ResultMap resultMap = tempUserService.register(jsonObject);
        if (resultMap.getCode() == SystemConst.SUCCESS.getCode()) {
            messageSender.sendToAllSystem((TempUser) resultMap.getData());
        }
        return resultMap;
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
            } else {
                return message;
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

    public ResultMap newAdvisorRequest(TempAdvise tempAdvise) {
        //todo:向教务发送消息
        return tempAdviseService.register(tempAdvise);
    }

    public ResultMap removeAdvisorRequest(Integer id) {
        return tempAdviseService.deleteTempAdviseById(id);
    }

    public ResultMap getAdviseRequest(String studentId) {
        return tempAdviseService.get(studentId);
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
