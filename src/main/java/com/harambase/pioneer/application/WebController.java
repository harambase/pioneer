package com.harambase.pioneer.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
public class WebController {

    //不需要login
    @RequestMapping({"/pcp", "/eas"})
    public String login() {
        return "pcp";
    }

    //不需要login
    @RequestMapping({"/", "/index"})
    public String homePage() {
        return "index";
    }

    @RequestMapping("/404")
    public String pageNotFound() {
        return "404";
    }

}
