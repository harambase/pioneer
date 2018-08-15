package com.harambase.pioneer.controller;

import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.helper.TokenHelper;
import com.harambase.pioneer.server.pojo.base.Transcript;
import com.harambase.pioneer.service.TranscriptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
@CrossOrigin
@RequestMapping(value = "/transcript")
public class TranscriptController {

    private final TranscriptService transcriptService;

    @Autowired
    public TranscriptController(TranscriptService transcriptService) {
        this.transcriptService = transcriptService;
    }

    @PreAuthorize("hasAnyRole('TEACH','FACULTY','ADMIN')")
    @RequestMapping(value = "/{id}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Transcript transcript, HttpServletRequest request) {
        transcript.setOperatorId(TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)));
        ResultMap ResultMap = transcriptService.updateGrade(id, transcript);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN','STUDENT', 'FACULTY', 'ADVISOR')")
    @RequestMapping(value = {"/list", "/course/student"}, produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity listForTeach(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                       @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                                       @RequestParam(value = "search", required = false, defaultValue = "") String search,
                                       @RequestParam(value = "order", required = false, defaultValue = "") String order,
                                       @RequestParam(value = "orderCol", required = false, defaultValue = "id") String orderCol,
                                       @RequestParam(value = "crn", required = false) String crn,
                                       @RequestParam(value = "studentId", required = false) String studentId,
                                       @RequestParam(value = "info", required = false) String info) {
        ResultMap message;
        if (StringUtils.isNotEmpty(crn) || StringUtils.isNotEmpty(studentId) || StringUtils.isNotEmpty(info)) {
            message = transcriptService.transcriptList(start * length - 1, length, search, order, orderCol, studentId, crn, info, "");
            message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        } else {
            message = new ResultMap();
            message.setData(new ArrayList<>());
            message.put("recordsTotal", 0);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('STUDENT','ADMIN')")
    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity listForStudent(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                         @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                                         @RequestParam(value = "search", required = false, defaultValue = "") String search,
                                         @RequestParam(value = "order", required = false, defaultValue = "") String order,
                                         @RequestParam(value = "orderCol", required = false, defaultValue = "id") String orderCol,
                                         @RequestParam(value = "complete", required = false) String complete,
                                         HttpServletRequest request) {

        ResultMap message = transcriptService.transcriptList(start * length - 1, length, search, order, orderCol, TokenHelper.getUserIdFromToken(TokenHelper.getToken(request)), "", "", complete);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/report/{studentId}", method = RequestMethod.GET)
    public void studentTranscriptReport(@PathVariable(value = "studentId") String studentId, @RequestParam String token, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(token)) {
            ResultMap ResultMap = transcriptService.studentTranscriptReport(studentId);
            try {
                FileUtil.downloadFile(studentId + "_transcript_report.pdf", (String) ResultMap.getData(), response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public void transcriptReport(@RequestParam String token, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(token)) {
            String studentId = TokenHelper.getUserIdFromToken(token);
            ResultMap ResultMap = transcriptService.studentTranscriptReport(studentId);
            try {
                FileUtil.downloadFile(studentId + "_transcript_report.pdf", (String) ResultMap.getData(), response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/report/all", method = RequestMethod.GET)
    public void transcriptAllReport(@RequestParam String token, @RequestParam(required = false) String style,
                                    @RequestParam(required = false) String info, HttpServletResponse response) {
        if (StringUtils.isNotEmpty(token)) {
            ResultMap ResultMap = transcriptService.allTranscripts(Integer.parseInt(style), info);
            try {
                FileUtil.downloadFile(info + "成绩单报告.csv", (String) ResultMap.getData(), response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
