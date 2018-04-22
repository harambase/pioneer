package com.harambase.pioneer.controller;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.Page;
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
    public ResponseEntity create(@RequestBody Message message, HttpServletRequest request) {
        message.setSenderId(TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        ResultMap ResultMap = messageService.create(message);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //@RequiresPermissions("user")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {
        ResultMap ResultMap = messageService.delete(id);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //@RequiresPermissions("user")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable(value = "id") Integer id,
                                 @RequestBody Message message) {
        ResultMap ResultMap = messageService.update(id, message);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //@RequiresPermissions("user")
    @RequestMapping(value = "/{id}/status", method = RequestMethod.PUT)
    public ResponseEntity updateStatus(@PathVariable(value = "id") Integer id,
                                       @RequestParam(value = "status") String status) {
        ResultMap ResultMap = messageService.updateStatus(id, status);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //@RequiresPermissions("user")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@RequestParam(value = "id") Integer id) {
        ResultMap ResultMap = messageService.get(id);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //@RequiresPermissions("user")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResponseEntity count(@RequestParam(value = "status") String status,
                                @RequestParam(value = "box") String box,
                                HttpServletRequest request) {
        String userId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        ResultMap ResultMap = messageService.countMessageByStatus(userId, box.toLowerCase(), status.toLowerCase());
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //@RequiresPermissions("user")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                               @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                               @RequestParam(value = "search", required = false, defaultValue = "") String search,
                               @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                               @RequestParam(value = "orderCol", required = false, defaultValue = "id") String orderCol,
                               @RequestParam(value = "box") String box,
                               HttpServletRequest request) {

        String userId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        ResultMap message = messageService.list(start * length - 1, length, search, order, orderCol, userId, box);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
