package com.harambase.pioneer.controller;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.helper.TokenHelper;
import com.harambase.pioneer.server.pojo.base.Feedback;
import com.harambase.pioneer.service.FeedbackService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController
@CrossOrigin
@RequestMapping(value = "/feedback")
public class FeedbackController {

    private FeedbackService feedbackService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'LOGISTIC', 'ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestParam(value = "info") String info,
                                 @RequestParam(value = "password") String password,
                                 HttpServletRequest request) {
        String opId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        password = passwordEncoder().encode(password);
        ResultMap ResultMap = feedbackService.generateAll(info, password, opId);
        return new ResponseEntity<>(ResultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'LOGISTIC', 'ADMIN')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public ResponseEntity createOne(@RequestParam(value = "info") String info,
                                    @PathVariable(value = "userId") String userId,
                                    @RequestParam(value = "password") String password,
                                    HttpServletRequest request) {
        String opId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        password = passwordEncoder().encode(password);
        ResultMap ResultMap = feedbackService.generateOne(info, userId, password, opId);
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
    @RequestMapping(value = "/validate/{info}", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity validate(@PathVariable String info,
                                   @RequestParam String password) {
        ResultMap message = feedbackService.validate(info, password);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'LOGISTIC', 'ADMIN')")
    @RequestMapping(value = "/other/{id}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity updateOther(@PathVariable Integer id,
                                      @RequestBody Feedback feedback, HttpServletRequest request) {
        String opId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        feedback.setOperatorId(opId);
        ResultMap message = feedbackService.updateFeedbackOther(id, feedback);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('FACULTY', 'LOGISTIC', 'ADMIN')")
    @RequestMapping(value = "/encrypt/{info}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity encrypt(@PathVariable String info,
                                  @RequestParam(value = "password") String password,
                                  @RequestParam(value = "old") String oldPassword,
                                  HttpServletRequest request) {
        String opId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        password = passwordEncoder().encode(password);
        ResultMap message = feedbackService.encrypt(info, password, oldPassword, opId);
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

    @PreAuthorize("hasAnyRole('LOGISTIC', 'ADMIN')")
    @RequestMapping(value = "/decrypt", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity decryptList(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                      @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                                      @RequestParam(value = "search", required = false, defaultValue = "") String search,
                                      @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                                      @RequestParam(value = "orderCol", required = false, defaultValue = "fname") String orderCol,
                                      @RequestParam(value = "facultyId", required = false) String facultyId,
                                      @RequestParam(value = "password", required = false) String password,
                                      @RequestParam(value = "info", required = false, defaultValue = "") String info) {
        search = search.replace("'", "");
        ResultMap message;

        if (StringUtils.isNotEmpty(info)) {
            message = feedbackService.decryptList(start * length - 1, length, search, order, orderCol, facultyId, password, info);
            message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        } else {
            message = new ResultMap();
            message.setData(new ArrayList<>());
            message.put("recordsTotal", 0);
        }
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

    @RequestMapping(value = "/info/{info}", method = RequestMethod.GET)
    public void downloadFeedbackByInfo(@RequestParam String token,
                                       @RequestParam String password,
                                       @PathVariable String info,
                                       HttpServletResponse response) {
        if (StringUtils.isNotEmpty(token)) {
            ResultMap resultMap = feedbackService.downloadFeedbackByInfo(info, password);
            try {
                FileUtil.downloadFile(info + "年度评价表.csv", (String) resultMap.getData(), response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
