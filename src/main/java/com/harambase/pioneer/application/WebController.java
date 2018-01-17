package com.harambase.pioneer.application;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
public class WebController {

    //不需要login
    @RequestMapping({"/", "/login"})
    public String login() {
        return "login";
    }

    @RequestMapping("/register")
    public String signUp() {
        return "registerNewUser";
    }

    @RequestMapping("/403")
    public String authError() {
        return "common/403";
    }

    @RequestMapping("/404")
    public String pageNotFound() {
        return "common/404";
    }
    @RequestMapping("/chatPage")
    public String chat() {
        return "common/chat";
    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    //只需要用户权限
    @RequiresPermissions("user")
    @RequestMapping("/index")
    public String welcome() {
        return "index";
    }

    @RequiresPermissions("user")
    @RequestMapping("/message")
    public String message() {
        return "message";
    }

    @RequiresPermissions("user")
    @RequestMapping("/personalCenter")
    public String personalCenter() {
        return "system/user";
    }

    //系统管理
    @RequiresPermissions(value = {"system", "admin"}, logical = Logical.OR)
    @RequestMapping("/system/monitor")
    public String monitor() {
        return "system/monitor";
    }

    @RequiresPermissions(value = {"system", "admin"}, logical = Logical.OR)
    @RequestMapping("/system/user/manage")
    public String user() {
        return "system/user";
    }

    @RequiresPermissions(value = {"system", "admin"}, logical = Logical.OR)
    @RequestMapping("/system/user/request")
    public String viewRegister() {
        return "system/registration";
    }

    @RequiresPermissions(value = {"system", "admin"}, logical = Logical.OR)
    @RequestMapping("/system/role")
    public String viewRole() {
        return "system/role";
    }

    //教学系统
    @RequiresPermissions(value = {"student", "admin"}, logical = Logical.OR)
    @RequestMapping("/course/choose")
    public String courseChoose() {
        return "course/choose";
    }

    @RequiresPermissions(value = {"student", "admin"}, logical = Logical.OR)
    @RequestMapping("/course/transcript")
    public String studentTranscript() {
        return "course/transcript";
    }

    @RequiresPermissions(value = {"faculty", "admin"}, logical = Logical.OR)
    @RequestMapping("/course/create")
    public String courseRequest() {
        return "teach/course";
    }

    @RequiresPermissions("user")
    @RequestMapping("/course/view")
    public String viewCourse() {
        return "course/course";
    }

    @RequiresPermissions(value = {"faculty", "admin"}, logical = Logical.OR)
    @RequestMapping("/course/request")
    public String courseRequestView() {
        return "teach/tempCourse";
    }

    @RequiresPermissions("user")
    @RequestMapping("/course/detail")
    public String courseDetail() {
        return "course/detail";
    }

    @RequiresPermissions("user")
    @RequestMapping("course/view/detail")
    public String courseViewDetail() {
        return "teach/course";
    }

    //教务管理
    @RequiresPermissions(value = {"teach", "admin"}, logical = Logical.OR)
    @RequestMapping("/teach/pin")
    public String pinInfo() {
        return "teach/pin";
    }

    @RequiresPermissions(value = {"teach", "admin"}, logical = Logical.OR)
    @RequestMapping("/teach/student/credit")
    public String setCredit() {
        return "teach/credit";
    }

    @RequiresPermissions(value = {"teach", "admin"}, logical = Logical.OR)
    @RequestMapping("/teach/create")
    public String createCourse() {
        return "teach/course";
    }

    @RequiresPermissions(value = {"teach", "admin"}, logical = Logical.OR)
    @RequestMapping("/teach/course")
    public String editCourse() {
        return "teach/course";
    }

    @RequiresPermissions(value = {"teach", "admin"}, logical = Logical.OR)
    @RequestMapping("/teach/advise")
    public String viewAdvising() {
        return "teach/advise";
    }

    @RequiresPermissions(value = {"teach", "admin"}, logical = Logical.OR)
    @RequestMapping("/teach/transcript")
    public String viewTranscript() {
        return "teach/transcript";
    }

    @RequiresPermissions(value = {"teach", "admin"}, logical = Logical.OR)
    @RequestMapping("/teach/request")
    public String viewCourseRequest() {
        return "teach/tempCourse";
    }

    //后勤管理
    @RequiresPermissions(value = {"logistic", "admin"}, logical = Logical.OR)
    @RequestMapping("/logistic/leave")
    public String viewLeave() {
        return "logistic/leave";
    }

    @RequiresPermissions(value = {"logistic", "admin"}, logical = Logical.OR)
    @RequestMapping("/logistic/dorm")
    public String viewDorm() {
        return "logistic/dorm";
    }

}
