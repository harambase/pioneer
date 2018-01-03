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
        return "release/login";
    }

    @RequestMapping("/register")
    public String signUp() {
        return "release/register";
    }

    @RequestMapping("/403")
    public String authError() {
        return "release/web/403";
    }

    @RequestMapping("/404")
    public String pageNotFound() {
        return "release/web/404";
    }

    //只需要用户权限
    @RequestMapping("/index")
    public String welcome() {
        return "release/index";
    }

    @RequestMapping("/message")
    public String message() {
        return "release/web/message";
    }

    @RequestMapping("/myCourse")
    public String myCourse() {
        return "release/teach/myCourse";
    }

    //系统设置
    @RequestMapping("/system/user/manage")
    public String user() {
        return "release/system/user";
    }

    @RequestMapping("/system/user/request")
    public String viewRegister() {
        return "release/system/registration";
    }

    @RequestMapping("/system/role")
    public String viewRole() {
        return "release/system/role";
    }

    //教务系统
    @RequestMapping("/teach/create")
    public String createCourse() {
        return "release/teach/course";
    }

    @RequestMapping("/teach/view")
    public String viewCourse() {
        return "release/teach/course";
    }

    @RequestMapping("/teach/edit")
    public String editCourse() {
        return "release/teach/course";
    }

    @RequestMapping("/teach/choose")
    public String courseChoose() {
        return "release/teach/courseChoose";
    }

    @RequestMapping("/teach/pin")
    public String pinInfo() {
        return "release/teach/pin";
    }
//    @RequestMapping("/teach/advise")
//    public String fViewAdvising(){
//        return "release/administration/viewAdvising";
//    }

    @RequestMapping("/student/course/view")
    public String sViewCourse() {
        return "release/student/teach/viewCourse";
    }

    @RequestMapping("/student/transcript/view")
    public String sViewTranscript() {
        return "release/student/teach/viewTranscript";
    }

    @RequestMapping("/manage/transcript/view")
    public String viewTranscript() {
        return "release/admin/teach/viewTranscript";
    }

    @RequestMapping("/manage/transcript/edit")
    public String editTranscript() {
        return "release/admin/teach/editTranscript";
    }

    @RequestMapping("/manage/student/credit")
    public String setCredit() {
        return "release/admin/teach/setCredit";
    }

    @RequestMapping("/manage/course/request")
    public String viewCourseRequest() {
        return "release/admin/teach/viewCourseRequest";
    }

    //后勤管理
    @RequestMapping("/logistic/leave")
    public String viewLeave() {
        return "release/logistic/viewLeave";
    }

    @RequestMapping("/logistic/dorm")
    public String viewDorm() {
        return "release/logistic/viewDorm";
    }

}
