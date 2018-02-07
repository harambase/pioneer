package com.harambase.pioneer.controller;

import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.helper.TokenHelper;
import com.harambase.pioneer.server.pojo.base.Message;
import com.harambase.pioneer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

@RestController
@CrossOrigin
@RequestMapping(value = "/message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    //@RequiresPermissions("user")
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Message message) {
        message.setSenderId(SessionUtil.getUserId());
        HaramMessage haramMessage = messageService.create(message);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    //@RequiresPermissions("user")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {
        HaramMessage haramMessage = messageService.delete(id);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    //@RequiresPermissions("user")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable(value = "id") Integer id,
                                 @RequestBody Message message) {
        HaramMessage haramMessage = messageService.update(id, message);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    //@RequiresPermissions("user")
    @RequestMapping(value = "/{id}/status", method = RequestMethod.PUT)
    public ResponseEntity updateStatus(@PathVariable(value = "id") Integer id,
                                       @RequestParam(value = "status") String status) {
        HaramMessage haramMessage = messageService.updateStatus(id, status);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    //@RequiresPermissions("user")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@RequestParam(value = "id") Integer id,
                              HttpServletRequest request) {
        TokenHelper.validateToken(TokenHelper.getToken(request))
        HaramMessage haramMessage = messageService.get(id);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    //@RequiresPermissions("user")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResponseEntity count(@RequestParam(value = "status") String status,
                                @RequestParam(value = "box") String box,
                                HttpServletRequest request) {
        String userId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        HaramMessage haramMessage = messageService.countMessageByStatus(useId, box.toLowerCase(), status.toLowerCase());
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    //@RequiresPermissions("user")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start") Integer start,
                               @RequestParam(value = "length") Integer length,
                               @RequestParam(value = "draw") Integer draw,
                               @RequestParam(value = "search[value]") String search,
                               @RequestParam(value = "order[0][dir]") String order,
                               @RequestParam(value = "order[0][column]") String orderCol,
                               @RequestParam(value = "box") String box,
                               HttpServletRequest request) {

        String userid = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        HaramMessage message = messageService.list(start, length, search, order, orderCol, userid, box);
        message.put("draw", draw);
        message.put("recordsTotal", ((LinkedHashMap) message.get("page")).get("totalRows"));
        message.put("recordsFiltered", ((LinkedHashMap) message.get("page")).get("totalRows"));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
