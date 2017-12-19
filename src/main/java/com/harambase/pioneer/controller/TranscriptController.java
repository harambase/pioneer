package com.harambase.pioneer.controller;

import com.harambase.common.HaramMessage;
import com.harambase.common.Page;
import com.harambase.support.util.SessionUtil;
import com.harambase.pioneer.pojo.base.TranscriptBase;
import com.harambase.pioneer.service.TranscriptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping(value = "/transcript")
public class TranscriptController {
    
    private final TranscriptService transcriptService;
    
    @Autowired
    public TranscriptController(TranscriptService transcriptService){
        this.transcriptService = transcriptService;
    }

    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody TranscriptBase transcript) {
        transcript.setOperator(SessionUtil.getUserId());
        HaramMessage haramMessage = transcriptService.updateGrade(transcript);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions({"admin", "teach", "student", "faculty"})
    @RequestMapping(value = {"/{studentId}/course","/{crn}/student"}, produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start") Integer start,
                               @RequestParam(value = "length") Integer length,
                               @RequestParam(value = "draw") Integer draw,
                               @RequestParam(value = "search[value]") String search,
                               @RequestParam(value = "order[0][dir]") String order,
                               @RequestParam(value = "order[0][column]") String orderCol,
                               @PathVariable(value = "studentId") String studentId,
                               @PathVariable(value = "crn") String crn) {
        Map<String, Object> map = new HashMap<>();
        try {
            HaramMessage message = transcriptService.transcriptList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, studentId, crn);
            map.put("draw", draw);
            map.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
            map.put("recordsFiltered", ((Page) message.get("page")).getTotalRows());
            map.put("data", message.getData());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("draw", 1);
            map.put("recordsTotal", 0);
            map.put("recordsFiltered", 0);
            map.put("data", new ArrayList<>());
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
//    @RequiresPermissions({"admin", "teach", "advisor", "student"})
//    @RequestMapping(value = "/{studentId}/report", method = RequestMethod.GET)
//    public void getStudentReport(@PathVariable(value = "studentId") String studentId, HttpServletResponse httpServletResponse){
//
//    }
}
