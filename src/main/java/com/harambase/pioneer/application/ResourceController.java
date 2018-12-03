package com.harambase.pioneer.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Controller
@CrossOrigin
public class ResourceController extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("./static/eas/**").addResourceLocations("classpath:/static/eas/");
        registry.addResourceHandler("/static/eas/**").addResourceLocations("classpath:/static/eas/");
        registry.addResourceHandler("/static/static/eas/**").addResourceLocations("classpath:/static/eas/");
        registry.addResourceHandler("/static/pioneer/**").addResourceLocations("classpath:/static/pioneer/");
        registry.addResourceHandler("/static/images/**").addResourceLocations("classpath:/static/home/images/");
        registry.addResourceHandler("/static/img/**").addResourceLocations("classpath:/static/eas/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/home/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/home/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/home/img/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/home/images/");

        super.addResourceHandlers(registry);
    }

    @RequestMapping(value = "/staff", method = RequestMethod.GET)
    public ResponseEntity faculty_staff() {
        return new ResponseEntity<>(StartUpPrepare.getDatasource("staffList"), HttpStatus.OK);
    }

    @RequestMapping(value = "/school", method = RequestMethod.GET)
    public ResponseEntity school() {
        return new ResponseEntity<>(StartUpPrepare.getDatasource("schoolList"), HttpStatus.OK);
    }

    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public ResponseEntity article() {
        return new ResponseEntity<>(StartUpPrepare.getDatasource("articleList"), HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/wechat", method = RequestMethod.GET)
    public ResponseEntity weChat() {
        return new ResponseEntity<>(StartUpPrepare.getDatasource("weChatList"), HttpStatus.OK);
    }

    @Scheduled(cron = "0 0 0,3,6,9,12,15,18,21 * * ? ")
    public void refresh() {
        StartUpPrepare.initHome();
    }
}
