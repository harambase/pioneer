package com.harambase.pioneer.controller;

import com.harambase.common.HaramMessage;
import com.harambase.common.Page;
import com.harambase.support.util.SessionUtil;
import com.harambase.pioneer.pojo.base.MessageWithBLOBs;
import com.harambase.pioneer.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/message")
@Api(value = "/message", description = "消息中心接口")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    @RequiresPermissions("user")
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody MessageWithBLOBs message){
        message.setSenderid(SessionUtil.getUserId());
        HaramMessage haramMessage = messageService.createMessage(message);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {
        HaramMessage haramMessage = messageService.delete(id);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable(value = "id") Integer id,
                                 @RequestBody MessageWithBLOBs message) {
        HaramMessage haramMessage = messageService.update(id, message);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@RequestParam(value = "id") Integer id) {
        HaramMessage haramMessage = messageService.getMessageView(id);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResponseEntity count(@RequestParam(value = "status") String status,
                                @RequestParam(value = "box") String box){
        String userid = SessionUtil.getUserId();
        String receiverid = null;
        String senderid = null;

        if(box.contains("inbox") || box.contains("important"))
            receiverid = userid;
        if(box.contains("sent") || box.contains("draft"))
            senderid = userid;
        if(box.contains("trash")) {
            receiverid =userid;
            senderid = userid;
        }

        HaramMessage haramMessage = messageService.countMessageByStatus(receiverid, senderid, box.toLowerCase(), status.toLowerCase());
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start") Integer start,
                               @RequestParam(value = "length") Integer length,
                               @RequestParam(value = "draw") Integer draw,
                               @RequestParam(value = "search[value]") String search,
                               @RequestParam(value = "order[0][dir]") String order,
                               @RequestParam(value = "order[0][column]") String orderCol,
                               @RequestParam(value = "box") String box) {

        String userid = SessionUtil.getUserId();
        String receiverid = null;
        String senderid = null;

        if(box.contains("inbox") || box.contains("important"))
            receiverid = userid;
        if(box.contains("sent") || box.contains("draft"))
            senderid = userid;
        if(box.contains("trash")) {
            receiverid =userid;
            senderid = userid;
        }

        Map<String, Object> map = new HashMap<>();
        try {
            HaramMessage message = messageService.list(String.valueOf(start / length + 1), String.valueOf(length), search,
                    order, orderCol, receiverid, senderid, box);
            map.put("draw", draw);
            map.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
            map.put("recordsFiltered", ((Page) message.get("page")).getTotalRows());
            map.put("data", message.getData());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("draw", 1);
            map.put("data", new ArrayList<>());
            map.put("recordsTotal", 0);
            map.put("recordsFiltered", 0);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
