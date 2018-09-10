package com.harambase.pioneer.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.service.RoleServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleService {

    private final RoleServerService roleServerService;

    @Autowired
    public RoleService(RoleServerService roleServerService) {
        this.roleServerService = roleServerService;
    }

    public ResultMap get(Integer roleId) {
        return roleServerService.get(roleId);
    }

    public ResultMap list(String search, String order, String orderCol) {
        return roleServerService.list(search, order, orderCol);
    }
}
