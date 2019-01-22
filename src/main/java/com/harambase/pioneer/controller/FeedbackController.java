package com.harambase.pioneer.controller;

import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.helper.TokenHelper;
import com.harambase.pioneer.server.pojo.base.Feedback;
import com.harambase.pioneer.service.FeedbackService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController
@CrossOrigin
@RequestMapping(value = "/feedback")
public class FeedbackController {

    private FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'LOGISTIC', 'ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestParam(value = "info") String info, HttpServletRequest request) {
        String opId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        ResultMap ResultMap = feedbackService.generateAll(info, opId);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'LOGISTIC', 'ADMIN')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public ResponseEntity createOne(@RequestParam(value = "info") String info,
                                    @PathVariable(value = "userId") String userId,
                                    HttpServletRequest request) {
        String opId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        ResultMap ResultMap = feedbackService.generateOne(info, userId, opId);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('FACULTY', 'LOGISTIC', 'ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {
        ResultMap message = feedbackService.removeFeedback(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'LOGISTIC', 'ADMIN')")
    @RequestMapping(value = "/{id}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Feedback feedback, HttpServletRequest request) {
        String opId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        feedback.setOperatorId(opId);
        ResultMap message = feedbackService.updateFeedback(id, feedback);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'LOGISTIC', 'ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable(value = "id") Integer id) {
        ResultMap message = feedbackService.get(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'LOGISTIC', 'ADMIN')")
    @RequestMapping(value = "/find/{facultyId}", method = RequestMethod.GET)
    public ResponseEntity find(@PathVariable(value = "facultyId") String facultyId) {
        ResultMap message = feedbackService.find(facultyId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'LOGISTIC', 'ADMIN')")
    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                               @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                               @RequestParam(value = "search", required = false, defaultValue = "") String search,
                               @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                               @RequestParam(value = "orderCol", required = false, defaultValue = "fname") String orderCol,
                               @RequestParam(value = "facultyId", required = false) String facultyId,
                               @RequestParam(value = "info", required = false, defaultValue = "") String info) {

        ResultMap message;
        search = search.replace("'", "");

        if (StringUtils.isNotEmpty(info)) {
            message = feedbackService.feedbackList(start * length - 1, length, search, order, orderCol, facultyId, info);
            message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        } else {
            message = new ResultMap();
            message.setData(new ArrayList<>());
            message.put("recordsTotal", 0);
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/download/{info}", method = RequestMethod.GET)
    public void downloadFeedbackByInfo(@RequestParam String token, @PathVariable String info, HttpServletResponse response, HttpServletRequest request) {
        if (StringUtils.isNotEmpty(token)) {
            ResultMap resultMap = feedbackService.downloadFeedbackByInfo(info);
            try {
                FileUtil.downloadFile(info + "评价表.csv", (String) resultMap.getData(), response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
