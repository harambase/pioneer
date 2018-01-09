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
    @RequestMapping("/myCourse")
    public String myCourse() {
        return "teach/myCourse";
    }

    //系统管理
    @RequiresPermissions(value = {"system", "admin", "user"}, logical = Logical.OR)
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

//todo: URL加密
//
//    @RequestMapping("/MavenTest/testpage/base64test")
//    public String test() {
//        return "test";
//    }
//
//    @RequestMapping("/base64test")
//    @ResponseBody
//    public String base64test(HttpServletRequest request) {
//        String address = request.getParameter("amp;address");
//        String name = request.getParameter("name");
//        String convStr = StringEscapeUtils.unescapeHtml4("&name=1009");
//        System.out.println("base64传输前");
//        System.out.println("name:" + name + "   address:" + address);
//        System.out.println("base64取值后");
//        byte[] result1 = Base64.decodeBase64(name);
//        byte[] result2 = Base64.decodeBase64(address);
//        String str1 = new String(result1);
//        String str2 = new String(result2);
//        System.out.println("name:" + str1 + "   address:" + str2);
//        String result = "name:" + str1 + "   address:" + str2;
//        return result;
//    }

}
