package com.harambase.pioneer.controller;

import com.harambase.common.HaramMessage;
import com.harambase.common.Page;
import com.harambase.pioneer.pojo.base.TranscriptBase;
import com.harambase.pioneer.service.TranscriptService;
import com.harambase.support.util.SessionUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping(value = "/transcript")
public class TranscriptController {

    private final TranscriptService transcriptService;

    @Autowired
    public TranscriptController(TranscriptService transcriptService) {
        this.transcriptService = transcriptService;
    }

    @RequiresPermissions({"admin", "teach"})
    @RequestMapping(value = "/{id}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Integer id, @RequestBody TranscriptBase transcript) {
        transcript.setOperator(SessionUtil.getUserId());
        HaramMessage haramMessage = transcriptService.updateGrade(id, transcript);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions({"admin", "teach", "student", "faculty"})
    @RequestMapping(value = {"/{studentId}/course", "/{crn}/student"}, produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start") Integer start,
                               @RequestParam(value = "length") Integer length,
                               @RequestParam(value = "draw") Integer draw,
                               @RequestParam(value = "search[value]") String search,
                               @RequestParam(value = "order[0][dir]") String order,
                               @RequestParam(value = "order[0][column]") String orderCol,
                               @PathVariable(value = "studentId") String studentId,
                               @PathVariable(value = "crn") String crn) {

        HaramMessage message = transcriptService.transcriptList(start, length, search, order, orderCol, studentId, crn);
        message.put("draw", draw);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        message.put("recordsFiltered", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
