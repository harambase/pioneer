package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;

public interface RoleServerService {

    ResultMap get(Integer roleId);

    ResultMap list(String search, String order, String orderCol);

}
