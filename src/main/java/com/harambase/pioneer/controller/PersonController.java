package com.harambase.pioneer.controller;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.server.pojo.base.Person;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.service.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

//    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Person person) {
        ResultMap message = personService.createPerson(person);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("userId") String userid) {
        ResultMap message = personService.deletePerson(userid);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(value = "/{userId}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("userId") String userid, @RequestBody Person person) {
        ResultMap message = personService.updatePerson(userid, person);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable(value = "userId") String userId) {
        ResultMap ResultMap = personService.get(userId);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public ResponseEntity getCurrentUser(@RequestHeader String token) {
        ResultMap message = new ResultMap();
//        message.setData(JWTUtil.getUser());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity search(@RequestParam(value = "search", required = false) String search,
                                 @RequestParam(value = "type", required = false) String type,
                                 @RequestParam(value = "status", required = false) String status) {
        ResultMap message = personService.search(search, type, status);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start") Integer start,
                               @RequestParam(value = "length") Integer length,
                               @RequestParam(value = "search[value]") String search,
                               @RequestParam(value = "order[0][dir]") String order,
                               @RequestParam(value = "order[0][column]") String orderCol,
                               @RequestParam(value = "type", required = false) String type,
                               @RequestParam(value = "status", required = false) String status) {
        ResultMap message = personService.list(start, length, search, order, orderCol, type, status);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/profile/{userId}", method = RequestMethod.PUT)
    public ResponseEntity uploadProfile(@RequestParam(value = "file", required = false) MultipartFile file,
                                        @PathVariable String userId) {
        ResultMap message = personService.upload(userId, file, "p");
        //更新页面头像信息
        if(message.getCode() == 2001){
//            Subject subject = SecurityUtils.getSubject();
//            subject.getSession().setAttribute("profile", "/pioneer/" + ((JSONObject)message.getData()).getString("path"));
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/info/{userId}", method = RequestMethod.PUT)
    public ResponseEntity updateInfo(@RequestParam(value = "file", required = false) MultipartFile file,
                                     @PathVariable String userId) {
        ResultMap message = personService.upload(userId, file, "f");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/info/{userId}", method = RequestMethod.GET)
    public void downloadUserInfo(@PathVariable(value = "userId") String userId, HttpServletResponse response) {
        ResultMap message = personService.get(userId);
        String userInfo = (String) ((LinkedHashMap) message.getData()).get("userInfo");
        if (StringUtils.isNotEmpty(userInfo)) {
            JSONObject info = JSONObject.parseObject(userInfo);
            try {
                FileUtil.downloadFile(info.getString("name"), info.getString("path"), response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
