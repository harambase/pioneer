package com.harambase.pioneer.controller;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.service.ReportService;
import com.harambase.support.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/report")
@CrossOrigin
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping(value = "/{studentId}/transcript", method = RequestMethod.GET)
    public void studentTranscriptReport(@PathVariable(value = "studentId") String studentId, HttpServletResponse response) {
        HaramMessage haramMessage = reportService.studentTranscriptReport(studentId);
        FileUtil.downloadFile(studentId + "_transcript_report.pdf", (String) haramMessage.getData(), response);
    }

}
