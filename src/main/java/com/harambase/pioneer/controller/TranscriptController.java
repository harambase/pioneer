package com.harambase.pioneer.controller;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Transcript;
import com.harambase.pioneer.service.TranscriptService;
import com.harambase.support.util.FileUtil;
import com.harambase.support.util.SessionUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@Controller
@CrossOrigin
@RequestMapping(value = "/transcript")
public class TranscriptController {

    private final TranscriptService transcriptService;

    @Autowired
    public TranscriptController(TranscriptService transcriptService) {
        this.transcriptService = transcriptService;
    }

    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{id}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Transcript transcript) {
        transcript.setOperator(SessionUtil.getUserId());
        HaramMessage haramMessage = transcriptService.updateGrade(id, transcript);
        return new ResponseEntity<>(haramMessage, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach", "student", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/{studentId}/course", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity listByStudentId(@RequestParam(value = "start") Integer start,
                                          @RequestParam(value = "length") Integer length,
                                          @RequestParam(value = "draw") Integer draw,
                                          @RequestParam(value = "search[value]") String search,
                                          @RequestParam(value = "order[0][dir]") String order,
                                          @RequestParam(value = "order[0][column]") String orderCol,
                                          @PathVariable(value = "studentId") String studentId) {

        HaramMessage message = transcriptService.transcriptList(start, length, search, order, orderCol, studentId, "");
        message.put("draw", draw);
        message.put("recordsTotal", ((LinkedHashMap) message.get("page")).get("totalRows"));
        message.put("recordsFiltered", ((LinkedHashMap) message.get("page")).get("totalRows"));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach", "student", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/{crn}/student", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity listByCRN(@RequestParam(value = "start") Integer start,
                                    @RequestParam(value = "length") Integer length,
                                    @RequestParam(value = "draw") Integer draw,
                                    @RequestParam(value = "search[value]") String search,
                                    @RequestParam(value = "order[0][dir]") String order,
                                    @RequestParam(value = "order[0][column]") String orderCol,
                                    @PathVariable(value = "crn") String crn) {

        HaramMessage message = transcriptService.transcriptList(start, length, search, order, orderCol, "", crn);
        message.put("draw", draw);
        message.put("recordsTotal", ((LinkedHashMap) message.get("page")).get("totalRows"));
        message.put("recordsFiltered", ((LinkedHashMap) message.get("page")).get("totalRows"));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "student"}, logical = Logical.OR)
    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start") Integer start,
                               @RequestParam(value = "length") Integer length,
                               @RequestParam(value = "draw") Integer draw,
                               @RequestParam(value = "search[value]") String search,
                               @RequestParam(value = "order[0][dir]") String order,
                               @RequestParam(value = "order[0][column]") String orderCol) {

        HaramMessage message = transcriptService.transcriptList(start, length, search, order, orderCol, SessionUtil.getUserId(), "");
        message.put("draw", draw);
        message.put("recordsTotal", ((LinkedHashMap) message.get("page")).get("totalRows"));
        message.put("recordsFiltered", ((LinkedHashMap) message.get("page")).get("totalRows"));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequiresPermissions(value = {"admin", "teach", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/{studentId}/report", method = RequestMethod.GET)
    public void studentTranscriptReport(@PathVariable(value = "studentId") String studentId, HttpServletResponse response) {
        HaramMessage haramMessage = transcriptService.studentTranscriptReport(studentId);
        FileUtil.downloadFile(studentId + "_transcript_report.pdf", (String) haramMessage.getData(), response);
    }

    @RequiresPermissions(value = {"admin", "student"}, logical = Logical.OR)
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public void transcriptReport(HttpServletResponse response) {
        String studentId = SessionUtil.getUserId();
        HaramMessage haramMessage = transcriptService.studentTranscriptReport(studentId);
        FileUtil.downloadFile(studentId + "_transcript_report.pdf", (String) haramMessage.getData(), response);
    }
}
