package com.harambase.pioneer.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Created by shilei on 2017/08/07.
 */
@Controller
public class WebController {

    @RequestMapping("/welcomeStudent")
    public String welcomeStudent(){
        return "student/welcomeStudent";
    }
    @RequestMapping("/welcomeAdmin")
    public String welcomeAdmin(){
        return "welcomeAdmin";
    }
    @RequestMapping("/welcomeFaculty")
    public String welcomeFaculty(){
        return "faculty/welcomeFaculty";
    }
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    @RequestMapping("/studentHeader")
    public String header(){
        return "student/studentHeader";
    }
    @RequestMapping("/manageCourse")
    public String adminManageCourse(){
        return "course/manageCourse";
    }
    @RequestMapping("/manageTranscript")
    public String adminManageTranscript(){
        return "transcript/manageTranscript";
    }
    @RequestMapping("/manageUser")
    public String adminManageUser(){
        return "account/manageUser";
    }
    @RequestMapping("/manageAdvising")
    public String adminManageAdvising(){
        return "advising/manageAdvising";
    }
    @RequestMapping("/adminProfile")
    public String adminProfile(){
        return "account/adminProfile";
    }
    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
    
}
