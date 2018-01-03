package com.harambase.pioneer.controller;

import com.alibaba.fastjson.JSONObject;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.TempUser;
import com.harambase.pioneer.service.RequestService;
import com.harambase.support.util.SessionUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@CrossOrigin
@RequestMapping("/request")

public class RequestController {

    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateRequest(@PathVariable Integer id, @RequestBody TempUser tempUser) {
        tempUser.setOperatorId(SessionUtil.getUserId());
        HaramMessage message = requestService.updateTempUser(id, tempUser);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody JSONObject jsonObject) {
        HaramMessage haramMessage = requestService.register(jsonObject);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "system"}, logical = Logical.OR)
    @RequestMapping(value = "/user/list", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity userList(@RequestParam(value = "start") Integer start,
                                   @RequestParam(value = "length") Integer length,
                                   @RequestParam(value = "draw") Integer draw,
                                   @RequestParam(value = "search[value]") String search,
                                   @RequestParam(value = "order[0][dir]") String order,
                                   @RequestParam(value = "order[0][column]") String orderCol,
                                   @RequestParam(value = "viewStatus") String viewStatus) {

        HaramMessage message = requestService.tempUserList(start, length, search, order, orderCol, viewStatus);
        message.put("draw", draw);
        message.put("recordsTotal", ((LinkedHashMap) message.get("page")).get("totalRows"));
        message.put("recordsFiltered", ((LinkedHashMap) message.get("page")).get("totalRows"));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

//    @RequestMapping(value = "/teach/list", produces = "application/json", method = RequestMethod.GET)
//    public ResponseEntity courseList(@RequestParam(value = "start") Integer start,
//                                     @RequestParam(value = "length") Integer length,
//                                     @RequestParam(value = "draw") Integer draw,
//                                     @RequestParam(value = "search[value]") String search,
//                                     @RequestParam(value = "order[0][dir]") String order,
//                                     @RequestParam(value = "order[0][column]") String orderCol,
//                                     HttpSession session){
//        Map<String, Object> map = new HashMap<>();
//        try {
//            HaramMessage message = courseService.tempCourseList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol);
//            map.put("draw", draw);
//            map.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
//            map.put("recordsFiltered", ((Page) message.get("page")).getTotalRows());
//            map.put("data", message.getData());
//        } catch (Exception e) {
//            e.printStackTrace();
//            map.put("draw", 1);
//            map.put("data", new ArrayList<>());
//            map.put("recordsTotal", 0);
//            map.put("recordsFiltered", 0);
//        }
//        return new ResponseEntity<>(map, HttpStatus.OK);
//    }

}
