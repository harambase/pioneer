package com.harambase.pioneer.application;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping("/course/view")
    public String viewCourse() {
        return "teach/course";
    }

    @RequestMapping("/teach/edit")
    public String editCourse() {
        return "teach/course";
    }

    @RequestMapping("/course/choose")
    public String courseChoose() {
        return "teach/choose";
    }

    @RequestMapping("/teach/pin")
    public String pinInfo() {
        return "teach/pin";
    }

    @RequestMapping("/teach/student/credit")
    public String setCredit() {
        return "teach/credit";
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


    @RequestMapping("/MavenTest/testpage/base64test")
    public String test() {
        return "test";
    }

    @RequestMapping("/base64test")
    @ResponseBody
    public String base64test(HttpServletRequest request) {
        String address = request.getParameter("amp;address");
        String name = request.getParameter("name");
        String convStr = StringEscapeUtils.unescapeHtml4("&name=1009");
        System.out.println("base64传输前");
        System.out.println("name:" + name + "   address:" + address);
        System.out.println("base64取值后");
        byte[] result1 = Base64.decodeBase64(name);
        byte[] result2 = Base64.decodeBase64(address);
        String str1 = new String(result1);
        String str2 = new String(result2);
        System.out.println("name:" + str1 + "   address:" + str2);
        String result = "name:" + str1 + "   address:" + str2;
        return result;
    }

}
