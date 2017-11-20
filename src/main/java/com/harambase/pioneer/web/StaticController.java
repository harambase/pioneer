package com.harambase.pioneer.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Controller
public class StaticController extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        registry.addResourceHandler("/static/styles/**").addResourceLocations("classpath:/static/styles/");
        registry.addResourceHandler("/static/fonts/**").addResourceLocations("classpath:/static/fonts/");
        registry.addResourceHandler("/static/scripts/**").addResourceLocations("classpath:/static/scripts/");
        registry.addResourceHandler("/static/plugins/**").addResourceLocations("classpath:/static/plugins/");
        registry.addResourceHandler("/static/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/static/data/**").addResourceLocations("classpath:/static/data/");
        registry.addResourceHandler("/static/profiles/**").addResourceLocations("classpath:/static/profiles/");

        super.addResourceHandlers(registry);
    }
}
