package com.harambase.pioneer.controller;

import com.alibaba.fastjson.JSON;
import com.harambase.common.HaramMessage;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.service.MonitorService;
import com.harambase.pioneer.service.PersonService;
import com.harambase.support.util.SessionUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@RestController
@CrossOrigin
@RequestMapping("/system")
public class SystemController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MonitorService monitorService;
    private final PersonService personService;

    @Autowired
    public SystemController(MonitorService monitorService,
                            PersonService personService) {
        this.monitorService = monitorService;
        this.personService = personService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody Person person) {
        HaramMessage message = personService.login(person);
        if (message.getCode() == 2001) {
            try {
                BeanUtils.populate(person, (LinkedHashMap) message.getData());
                UsernamePasswordToken token = new UsernamePasswordToken(person.getUserId(), person.getPassword().toCharArray());
                Subject subject = SecurityUtils.getSubject();

                //将用户信息放入session中
                subject.getSession().setAttribute("user", person);

                if(StringUtils.isNotEmpty(person.getProfile()))
                    subject.getSession().setAttribute("profile", (JSON.parseObject(person.getProfile())).getString("path"));

                subject.login(token); //完成登录

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                message.setMsg(FlagDict.SYSTEM_ERROR.getM());
                message.setCode(FlagDict.SYSTEM_ERROR.getV());
                return new ResponseEntity<>(message, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout")
    public void logout(HttpServletResponse response) throws Exception{
        response.sendRedirect("/login");
    }

    @RequestMapping(value = "/swagger")
    @RequiresPermissions("admin")
    public void swagger(HttpServletResponse response) throws Exception{
        response.setHeader("userId", SessionUtil.getUserId());
        response.sendRedirect("http://localhost:8080/?userId=" +  SessionUtil.getUserId());
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public ResponseEntity systemInfo() {
        HaramMessage message = monitorService.systemInfo();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/relation", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public ResponseEntity relationChart() {
        HaramMessage haramMessage = monitorService.getRelationChart();
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/count", method = RequestMethod.GET)
    @RequiresPermissions("user")
    public ResponseEntity userCount() {
        HaramMessage haramMessage = monitorService.getUserChart();
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

}
