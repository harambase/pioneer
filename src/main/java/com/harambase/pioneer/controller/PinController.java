package com.harambase.pioneer.controller;

import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.helper.TokenHelper;
import com.harambase.pioneer.server.pojo.base.Pin;
import com.harambase.pioneer.service.PinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('TEACH','ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestParam(value = "startTime") String startTime,
                                 @RequestParam(value = "endTime") String endTime,
                                 @RequestParam(value = "role") String role,
                                 @RequestParam(value = "info") String info,
                                 @RequestParam(value = "remark") String remark) {
        ResultMap ResultMap = pinService.generateAll(startTime, endTime, role, info, remark);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public ResponseEntity createOne(@RequestParam(value = "startTime") String startTime,
                                    @RequestParam(value = "endTime") String endTime,
                                    @RequestParam(value = "role") String role,
                                    @RequestParam(value = "info") String info,
                                    @RequestParam(value = "remark") String remark,
                                    @PathVariable(value = "userId") String userId) {
        ResultMap ResultMap = pinService.generateOne(startTime, endTime, role, info, remark, userId);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN')")
    @RequestMapping(value = "/{pin}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "pin") Integer pin) {
        ResultMap ResultMap = pinService.deleteSingleByPin(pin);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN')")
    @RequestMapping(value = "/{info}/all", method = RequestMethod.DELETE)
    public ResponseEntity deleteAll(@PathVariable(value = "info") String info) {
        ResultMap ResultMap = pinService.deleteAllByInfo(info);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/{pin}", method = RequestMethod.GET)
    public ResponseEntity validate(@PathVariable(value = "pin") Integer pin, HttpServletRequest request) {
        ResultMap ResultMap = pinService.validate(pin, TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('TEACH','ADMIN')")
    @RequestMapping(value = "/send/faculty/{info}", method = RequestMethod.GET)
    public ResponseEntity sendFacultyPin(@PathVariable(value = "info") String info, HttpServletRequest request) {
        ResultMap ResultMap = pinService.sendFacultyPin(info, TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN')")
    @RequestMapping(value = "/send/advisor/{info}", method = RequestMethod.GET)
    public ResponseEntity sendAdvisorPin(@PathVariable(value = "info") String info, HttpServletRequest request) {
        ResultMap ResultMap = pinService.sendAdvisorPin(info, TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN')")
    @RequestMapping(value = "/send/student/{info}", method = RequestMethod.GET)
    public ResponseEntity sendStudentPin(@PathVariable(value = "info") String info, HttpServletRequest request) {
        ResultMap ResultMap = pinService.sendStudentPin(info, TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN')")
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseEntity resend(@RequestBody Pin pin, HttpServletRequest request) {
        ResultMap ResultMap = pinService.resend(pin, TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN')")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity getAllInfo() {
        ResultMap ResultMap = pinService.getAllInfo();
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN')")
    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                               @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                               @RequestParam(value = "search", required = false, defaultValue = "") String search,
                               @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                               @RequestParam(value = "orderCol", required = false, defaultValue = "pin") String orderCol,
                               @RequestParam(value = "ownerId", required = false, defaultValue = "") String ownerId,
                               @RequestParam(value = "info", required = false) String info) {
        search = search.replace("'", "");
        ResultMap message = pinService.listByInfo(start * length - 1, length, search, order, orderCol, info, ownerId);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN')")
    @RequestMapping(value = "/{pinNum}", method = RequestMethod.PUT)
    public ResponseEntity updateOne(@PathVariable Integer pinNum, @RequestBody Pin pin) {
        ResultMap resultMap = pinService.updateOne(pinNum, pin);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
