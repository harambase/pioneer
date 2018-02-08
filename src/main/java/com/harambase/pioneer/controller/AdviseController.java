package com.harambase.pioneer.controller;

import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.helper.TokenHelper;
import com.harambase.pioneer.server.pojo.base.Advise;
import com.harambase.pioneer.service.AdviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

@RestController
@CrossOrigin
@RequestMapping(value = "/advise")
public class AdviseController {

    private AdviseService adviseService;

    @Autowired
    public AdviseController(AdviseService adviseService) {
        this.adviseService = adviseService;
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Advise advise, @RequestHeader String token) {
        ResultMap message = adviseService.assignMentor(advise);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable(value = "id") Integer id) {
        ResultMap message = adviseService.removeMentor(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach"}, logical = Logical.OR)
    @RequestMapping(value = "/{id}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Integer id,
                                 @RequestParam(value = "studentId") String studentId,
                                 @RequestParam(value = "facultyId") String facultyId) {
        ResultMap message = adviseService.updateAdvise(id, studentId, facultyId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "student", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@RequestParam(value = "id") Integer id) {
        ResultMap message = adviseService.getMentor(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //    @RequiresPermissions(value = {"admin", "teach", "student", "faculty"}, logical = Logical.OR)
    @RequestMapping(value = "/list", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start") Integer start,
                               @RequestParam(value = "length") Integer length,
                               @RequestParam(value = "search[value]") String search,
                               @RequestParam(value = "order[0][dir]") String order,
                               @RequestParam(value = "order[0][column]") String orderCol,
                               @RequestParam(value = "studentid", required = false) String studentid,
                               @RequestParam(value = "facultyid", required = false) String facultyid,
                               @RequestParam(value = "mode", required = false) String mode,
                               HttpServletRequest request) {

        if (mode != null && mode.equals("faculty"))
            facultyid = TokenHelper.getUserIdFromToken(TokenHelper.getToken(request));
        ResultMap message = adviseService.advisingList(start, length, search, order, orderCol, studentid, facultyid);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
