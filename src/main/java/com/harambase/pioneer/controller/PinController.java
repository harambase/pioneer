package com.harambase.pioneer.controller;

import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.helper.TokenHelper;
import com.harambase.pioneer.service.PinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/pin")
public class PinController {

    private final PinService pinService;

    @Autowired
    public PinController(PinService pinService) {
        this.pinService = pinService;
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestParam(value = "startTime") String startTime,
                                 @RequestParam(value = "endTime") String endTime,
                                 @RequestParam(value = "role") String role,
                                 @RequestParam(value = "info") String info,
                                 @RequestParam(value = "remark") String remark) {
        ResultMap ResultMap = pinService.generateAll(startTime, endTime, role, info, remark);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public ResponseEntity createOne(@RequestParam(value = "startTime") String startTime,
                                    @RequestParam(value = "endTime") String endTime,
                                    @RequestParam(value = "role") int role,
                                    @RequestParam(value = "info") String info,
                                    @RequestParam(value = "remark") String remark,
                                    @PathVariable(value = "userId") String userId) {
        ResultMap ResultMap = pinService.generateOne(startTime, endTime, role, info, remark, userId);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{pin}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "pin") Integer pin) {
        ResultMap ResultMap = pinService.deleteSingleByPin(pin);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{info}/all", method = RequestMethod.DELETE)
    public ResponseEntity deleteAll(@PathVariable(value = "info") String info) {
        ResultMap ResultMap = pinService.deleteAllByInfo(info);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions("user")
    @RequestMapping(value = "/{pin}", method = RequestMethod.GET)
    public ResponseEntity validate(@PathVariable(value = "pin") Integer pin, HttpServletRequest request) {
        ResultMap ResultMap = pinService.validate(pin, TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }


    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/send/faculty/{info}", method = RequestMethod.GET)
    public ResponseEntity sendFacultyPin(@PathVariable(value = "info") String info, HttpServletRequest request) {
        ResultMap ResultMap = pinService.sendFacultyPin(info, TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/send/advisor/{info}", method = RequestMethod.GET)
    public ResponseEntity sendAdvisorPin(@PathVariable(value = "info") String info, HttpServletRequest request) {
        ResultMap ResultMap = pinService.sendAdvisorPin(info, TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity getAllInfo() {
        ResultMap ResultMap = pinService.getAllInfo();
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start") Integer start,
                               @RequestParam(value = "length") Integer length,
                               @RequestParam(value = "draw") Integer draw,
                               @RequestParam(value = "search[value]") String search,
                               @RequestParam(value = "order[0][dir]") String order,
                               @RequestParam(value = "order[0][column]") String orderCol,
                               @RequestParam(value = "info", required = false) String info) {

        ResultMap message = pinService.listByInfo(start, length, search, order, orderCol, info);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    //todo:updatePerson
}
