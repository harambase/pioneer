package com.harambase.pioneer.controller;

import com.harambase.common.HaramMessage;
import com.harambase.common.constant.FlagDict;
import com.harambase.support.util.SessionUtil;
import com.harambase.pioneer.service.PinService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/pin")
public class PinController {

    private final PinService pinService;
    
    @Autowired
    public PinController(PinService pinService){
        this.pinService = pinService;
    }

    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestParam(value = "startTime") String startTime,
                                 @RequestParam(value = "endTime") String endTime,
                                 @RequestParam(value = "role") int role,
                                 @RequestParam(value = "info") String info,
                                 @RequestParam(value = "remark") String remark){
        HaramMessage haramMessage = pinService.generateAll(startTime, endTime, role, info, remark);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(value = "/{pin}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "pin") String pin) {
        HaramMessage haramMessage = pinService.deleteSingleByPin(pin);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    public ResponseEntity deleteAll(@RequestParam(value = "info") String info) {
        HaramMessage haramMessage = pinService.deleteAllByInfo(info);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/{pin}", method = RequestMethod.GET)
    public ResponseEntity validate(@PathVariable(value = "pin") Integer pin) {
        HaramMessage haramMessage = pinService.validate(pin);
        if(haramMessage.getCode() == FlagDict.SUCCESS.getV())
            SessionUtil.setPin(haramMessage.getData());
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public ResponseEntity sessionValidate() {
        HaramMessage haramMessage = pinService.validate(SessionUtil.getPin().getPin());
        if(haramMessage.getCode() == FlagDict.SUCCESS.getV())
            SessionUtil.setPin(haramMessage.getData());
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(value = "/send/faculty/{info}", method = RequestMethod.GET)
    public ResponseEntity sendFacultyPin(@PathVariable(value = "info") String info){
        HaramMessage haramMessage = pinService.sendFacultyPin(info, SessionUtil.getUserId());
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(value = "/send/advisor/{info}", method = RequestMethod.GET)
    public ResponseEntity sendAdvisorPin(@PathVariable(value = "info") String info){
        HaramMessage haramMessage = pinService.sendAdvisorPin(info, SessionUtil.getUserId());
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    //todo:updatePerson,get
}
