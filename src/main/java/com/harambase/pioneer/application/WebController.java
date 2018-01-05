package com.harambase.pioneer.application;

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
        return "register";
    }

    @RequestMapping("/403")
    public String authError() {
        return "web/403";
    }

    @RequestMapping("/404")
    public String pageNotFound() {
        return "web/404";
    }

    //只需要用户权限
    @RequestMapping("/index")
    public String welcome() {
        return "index";
    }

    @RequestMapping("/message")
    public String message() {
        return "web/message";
    }

    @RequestMapping("/myCourse")
    public String myCourse() {
        return "teach/myCourse";
    }

    //系统设置
    @RequestMapping("/system/user/manage")
    public String user() {
        return "system/user";
    }

    @RequestMapping("/system/user/request")
    public String viewRegister() {
        return "system/registration";
    }

    @RequestMapping("/system/role")
    public String viewRole() {
        return "system/role";
    }

    //教务系统
    @RequestMapping("/teach/create")
    public String createCourse() {
        return "teach/course";
    }

    @RequestMapping("/teach/view")
    public String viewCourse() {
        return "teach/course";
    }

    @RequestMapping("/teach/edit")
    public String editCourse() {
        return "teach/course";
    }

    @RequestMapping("/teach/choose")
    public String courseChoose() {
        return "teach/courseChoose";
    }

    @RequestMapping("/teach/pin")
    public String pinInfo() {
        return "teach/pin";
    }
//    @RequestMapping("/teach/advise")
//    public String fViewAdvising(){
//        return "administration/viewAdvising";
//    }

    @RequestMapping("/student/course/view")
    public String sViewCourse() {
        return "student/teach/viewCourse";
    }

    @RequestMapping("/student/transcript/view")
    public String sViewTranscript() {
        return "student/teach/viewTranscript";
    }

    @RequestMapping("/manage/transcript/view")
    public String viewTranscript() {
        return "admin/teach/viewTranscript";
    }

    @RequestMapping("/manage/transcript/edit")
    public String editTranscript() {
        return "admin/teach/editTranscript";
    }

    @RequestMapping("/manage/student/credit")
    public String setCredit() {
        return "admin/teach/setCredit";
    }

    @RequestMapping("/manage/course/request")
    public String viewCourseRequest() {
        return "admin/teach/viewCourseRequest";
    }

    //后勤管理
    @RequestMapping("/logistic/leave")
    public String viewLeave() {
        return "logistic/viewLeave";
    }

    @RequestMapping("/logistic/dorm")
    public String viewDorm() {
        return "logistic/viewDorm";
    }

}
