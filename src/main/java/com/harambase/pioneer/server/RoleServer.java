package com.harambase.pioneer.server;

import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.server.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleServer {

    private final RoleService roleService;

    @Autowired
    public RoleServer(RoleService roleService) {
        this.roleService = roleService;
    }
    
    public HaramMessage get(Integer roleId) {
        return roleService.get(roleId);
    }

    public HaramMessage list(String search, String order, String orderCol) {
        return roleService.list(search, order, orderCol);
    }
}
