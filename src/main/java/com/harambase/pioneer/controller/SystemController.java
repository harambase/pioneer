package com.harambase.pioneer.controller;

import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.common.constant.FlagDict;
import com.harambase.pioneer.helper.JWTUtil;
import com.harambase.pioneer.server.pojo.base.Person;
import com.harambase.pioneer.helper.SessionUtil;
import com.harambase.pioneer.service.MonitorService;
import com.harambase.pioneer.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/system")
public class SystemController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MonitorService monitorService;
    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public SystemController(MonitorService monitorService,
                            PersonService personService,
                            PasswordEncoder passwordEncoder) {
        this.monitorService = monitorService;
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
    }

    //@PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        HaramMessage message = personService.login(person);
        if (message.getCode() == 2001) {
            try {
                person = (Person) message.getData();
                long nowMillis = System.currentTimeMillis();
                message.setData(JWTUtil.sign(person, nowMillis));
                message.setMsg(FlagDict.SUCCESS.getM());
                message.setCode(FlagDict.SUCCESS.getV());
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
    public void logout(HttpServletResponse response) throws Exception {
//        SecurityUtils.getSubject().logout();
        response.sendRedirect("/login");
    }

    @RequestMapping(value = "/swagger")
    //@RequiresPermissions("admin")
    //@PreAuthorize("hasRole('USER')")
    public void swagger(HttpServletResponse response) throws Exception {
        response.setHeader("userId", SessionUtil.getUserId());
        response.sendRedirect("http://localhost:8080/?userId=" + SessionUtil.getUserId());
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    //@RequiresPermissions("user")
    public ResponseEntity systemInfo() {
        HaramMessage message = monitorService.systemInfo();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/relation", method = RequestMethod.GET)
    //@RequiresPermissions("user")
    public ResponseEntity relationChart() {
        HaramMessage haramMessage = monitorService.getRelationChart();
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/count", method = RequestMethod.GET)
    //@RequiresPermissions("user")
    public ResponseEntity userCount() {
        HaramMessage haramMessage = monitorService.getUserChart();
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

}
