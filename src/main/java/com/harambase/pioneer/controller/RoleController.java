package com.harambase.pioneer.controller;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.server.RoleServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
@Controller
@RequestMapping("/role")
public class RoleController {

    private final RoleServer roleServer;

    public RoleController(RoleServer roleServer) {
        this.roleServer = roleServer;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('SYSTEM','ADMIN')")
    public ResponseEntity list(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                               @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                               @RequestParam(value = "search", required = false, defaultValue = "") String search,
                               @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                               @RequestParam(value = "orderCol", required = false, defaultValue = "role_id") String orderCol) {
        search = search.replace("'", "");
        ResultMap message = roleServer.list(search, order, orderCol);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
