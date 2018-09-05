package com.harambase.pioneer.controller;

import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.helper.TokenHelper;
import com.harambase.pioneer.server.pojo.base.Advise;
import com.harambase.pioneer.service.AdviseService;
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
@RequestMapping(value = "/advise")
public class AdviseController {

    private AdviseService adviseService;

    @Autowired
    public AdviseController(AdviseService adviseService) {
        this.adviseService = adviseService;
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN')")
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Advise advise, HttpServletRequest request) {
        String opId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        advise.setOperatorId(opId);
        ResultMap message = adviseService.assignMentor(advise);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {
        ResultMap message = adviseService.removeMentor(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN')")
    @RequestMapping(value = "/{id}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Integer id, @RequestBody Advise advise, HttpServletRequest request) {
        String opId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        advise.setOperatorId(opId);
        ResultMap message = adviseService.updateAdvise(id, advise);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN','STUDENT','ADVISOR')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable(value = "id") Integer id) {
        ResultMap message = adviseService.getMentor(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('TEACH','ADMIN','STUDENT','ADVISOR')")
    @RequestMapping(produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                               @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                               @RequestParam(value = "search", required = false, defaultValue = "") String search,
                               @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                               @RequestParam(value = "orderCol", required = false, defaultValue = "id") String orderCol,
                               @RequestParam(value = "studentId", required = false) String studentId,
                               @RequestParam(value = "facultyId", required = false) String facultyId,
                               @RequestParam(value = "info", required = false, defaultValue = "") String info,
                               @RequestParam(value = "mode", required = false) String mode,
                               HttpServletRequest request) {

        ResultMap message;
        search = search.replace("'", "");

        if (mode != null && mode.equals("faculty")) {
            facultyId = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        }

        if (StringUtils.isNotEmpty(studentId) || StringUtils.isNotEmpty(facultyId) || StringUtils.isNotEmpty(info)) {
            message = adviseService.advisingList(start * length - 1, length, search, order, orderCol, studentId, facultyId, info);
            message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        } else {
            message = new ResultMap();
            message.setData(new ArrayList<>());
            message.put("recordsTotal", 0);
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT','FACULTY')")
    @RequestMapping(value = "/advisor", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                               @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                               @RequestParam(value = "search", required = false, defaultValue = "") String search,
                               @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                               @RequestParam(value = "orderCol", required = false, defaultValue = "user_id") String orderCol,
                               @RequestParam(value = "status", required = false) String status) {
        search = search.replace("'", "");
        ResultMap message = adviseService.advisorList(start * length - 1, length, search, order, orderCol, status);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/download/{info}", method = RequestMethod.GET)
    public void downloadAdviseByInfo(@RequestParam String token, @PathVariable String info, HttpServletResponse response, HttpServletRequest request) {
        if (StringUtils.isNotEmpty(token)) {
            ResultMap resultMap = adviseService.downloadAdviseByInfo(info);
            try {
                FileUtil.downloadFile(info + "导师表.csv", (String) resultMap.getData(), response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACH')")
    @RequestMapping(value = "/advisor/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity removeAdvisor(@PathVariable String userId) {
        ResultMap message = adviseService.removeAdvisor(userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACH')")
    @RequestMapping(value = "/advisor/{userId}", method = RequestMethod.PUT)
    public ResponseEntity addAdvisor(@PathVariable String userId) {
        ResultMap message = adviseService.addAdvisor(userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','TEACH', 'STUDENT')")
    @RequestMapping(value = "/advisor/{userId}", method = RequestMethod.GET)
    public ResponseEntity getAdvisor(@PathVariable String userId) {
        ResultMap message = adviseService.getAdvisor(userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
