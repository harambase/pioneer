package com.harambase.pioneer.application;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Controller
@CrossOrigin
public class ResourceController extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("./static/eas/**").addResourceLocations("classpath:/static/eas/");
        registry.addResourceHandler("/static/eas/**").addResourceLocations("classpath:/static/eas/");
        registry.addResourceHandler("/static/static/eas/**").addResourceLocations("classpath:/static/eas/");
        registry.addResourceHandler("./static/home/**").addResourceLocations("classpath:/static/home/");
        registry.addResourceHandler("/static/home/**").addResourceLocations("classpath:/static/home/");
        registry.addResourceHandler("/static/static/home/**").addResourceLocations("classpath:/static/home/");
        registry.addResourceHandler("/static/pioneer/**").addResourceLocations("classpath:/static/pioneer/");
        registry.addResourceHandler("/static/images/**").addResourceLocations("classpath:/static/home/images/");
        registry.addResourceHandler("/static/img/**").addResourceLocations("classpath:/static/eas/img/");

        super.addResourceHandlers(registry);
    }
}
