package com.harambase.pioneer.controller;

import com.harambase.common.HaramMessage;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.pojo.Pin;
import com.harambase.pioneer.service.PinService;
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
@RequestMapping(value = "/pin")
public class PinController {

    private final PinService pinService;

    @Autowired
    public PinController(PinService pinService) {
        this.pinService = pinService;
    }

    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestParam(value = "startTime") String startTime,
                                 @RequestParam(value = "endTime") String endTime,
                                 @RequestParam(value = "role") String role,
                                 @RequestParam(value = "info") String info,
                                 @RequestParam(value = "remark") String remark) {
        HaramMessage haramMessage = pinService.generateAll(startTime, endTime, role, info, remark);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public ResponseEntity createOne(@RequestParam(value = "startTime") String startTime,
                                    @RequestParam(value = "endTime") String endTime,
                                    @RequestParam(value = "role") int role,
                                    @RequestParam(value = "info") String info,
                                    @RequestParam(value = "remark") String remark,
                                    @PathVariable(value = "userId")String userId) {
        HaramMessage haramMessage = pinService.generateOne(startTime, endTime, role, info, remark, userId);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{pin}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "pin") String pin) {
        HaramMessage haramMessage = pinService.deleteSingleByPin(pin);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{info}/all", method = RequestMethod.DELETE)
    public ResponseEntity deleteAll(@PathVariable(value = "info") String info) {
        HaramMessage haramMessage = pinService.deleteAllByInfo(info);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/{pin}", method = RequestMethod.GET)
    public ResponseEntity validate(@PathVariable(value = "pin") Integer pin) {
        HaramMessage haramMessage = pinService.validate(pin);
        if (haramMessage.getCode() == FlagDict.SUCCESS.getV())
            SessionUtil.setPin(haramMessage.getData());
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public ResponseEntity sessionValidate() {
        LinkedHashMap pin = SessionUtil.getPin();
        HaramMessage haramMessage;
        if(pin != null) {
            haramMessage = pinService.validate((Integer) pin.get("pin"));
            if (haramMessage.getCode() == FlagDict.SUCCESS.getV())
                SessionUtil.setPin(haramMessage.getData());
        }else{
            haramMessage = new HaramMessage();
            haramMessage.setCode(FlagDict.FAIL.getV());
        }
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/send/faculty/{info}", method = RequestMethod.GET)
    public ResponseEntity sendFacultyPin(@PathVariable(value = "info") String info) {
        HaramMessage haramMessage = pinService.sendFacultyPin(info, SessionUtil.getUserId());
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/send/advisor/{info}", method = RequestMethod.GET)
    public ResponseEntity sendAdvisorPin(@PathVariable(value = "info") String info) {
        HaramMessage haramMessage = pinService.sendAdvisorPin(info, SessionUtil.getUserId());
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity getAllInfo() {
        HaramMessage haramMessage = pinService.getAllInfo();
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start") Integer start,
                               @RequestParam(value = "length") Integer length,
                               @RequestParam(value = "draw") Integer draw,
                               @RequestParam(value = "search[value]") String search,
                               @RequestParam(value = "order[0][dir]") String order,
                               @RequestParam(value = "order[0][column]") String orderCol,
                               @RequestParam(value = "info", required = false) String info) {

        HaramMessage message = pinService.listByInfo(start, length, search, order, orderCol, info);
        message.put("draw", draw);
        message.put("recordsTotal", ((LinkedHashMap) message.get("page")).get("totalRows"));
        message.put("recordsFiltered", ((LinkedHashMap) message.get("page")).get("totalRows"));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    //todo:updatePerson
}
