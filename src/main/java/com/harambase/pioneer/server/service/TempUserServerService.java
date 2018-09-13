package com.harambase.pioneer.server.service;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.TempUser;

public interface TempUserServerService {

    ResultMap list(String currentPage, String pageSize, String search, String order, String orderCol, String status);

    ResultMap create(JSONObject jsonObject);

    ResultMap delete(Integer id);

    ResultMap update(Integer id, TempUser tempUser);

    ResultMap retrieve(Integer id);

}
