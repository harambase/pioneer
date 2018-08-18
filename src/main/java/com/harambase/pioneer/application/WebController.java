package com.harambase.pioneer.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
public class WebController {

    //不需要login
    @RequestMapping("/eas")
    public String login() {
        return "eas";
    }

    //不需要login
    @RequestMapping({"/", "/index"})
    public String homePage() {
        return "homePage";
    }

    @RequestMapping("/404")
    public String pageNotFound() {
        return "common/404";
    }


}
