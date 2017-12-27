package com.harambase.pioneer.controller;

import com.harambase.common.HaramMessage;
import com.harambase.common.Page;
import com.harambase.support.util.SessionUtil;
import com.harambase.pioneer.pojo.base.AdviseBase;
import com.harambase.pioneer.service.AdviseService;
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
@RequestMapping(value = "/advise")
public class AdviseController {

    private AdviseService adviseService;
    
    @Autowired
    public  AdviseController(AdviseService adviseService){
        this.adviseService = adviseService;
    }

    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody AdviseBase advise) {
        HaramMessage message = adviseService.assignMentor(advise);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {
        HaramMessage message = adviseService.removeMentor(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(value = "/{id}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Integer id,
                                 @RequestParam(value = "studentId") String studentId,
                                 @RequestParam(value = "facultyId") String facultyId){
        HaramMessage message = adviseService.updateAdvise(id, studentId, facultyId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions({"admin", "teach", "student", "faculty"})
    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@RequestParam(value = "id") Integer id) {
        HaramMessage message = adviseService.getMentor(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions({"admin", "teach", "student", "faculty"})
    @RequestMapping(value = "/list", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start") Integer start,
                               @RequestParam(value = "length") Integer length,
                               @RequestParam(value = "draw") Integer draw,
                               @RequestParam(value = "search[value]") String search,
                               @RequestParam(value = "order[0][dir]") String order,
                               @RequestParam(value = "order[0][column]") String orderCol,
                               @RequestParam(value = "studentid", required = false) String studentid,
                               @RequestParam(value = "facultyid", required = false) String facultyid,
                               @RequestParam(value = "mode", required = false) String mode) {

        if(mode != null && mode.equals("faculty"))
            facultyid = SessionUtil.getUserId();
        HaramMessage message = adviseService.advisingList(start, length, search, order, orderCol, studentid, facultyid);
        message.put("draw", draw);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        message.put("recordsFiltered", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
