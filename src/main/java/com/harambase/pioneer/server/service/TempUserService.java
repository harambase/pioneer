package com.harambase.pioneer.server.service;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.TempUser;

public interface TempUserService {

    ResultMap deleteTempUserById(Integer id);

    ResultMap register(JSONObject jsonObject);

    ResultMap updateTempUser(Integer id, TempUser tempUser);

    ResultMap tempUserList(String s, String s1, String search, String order, String orderCol, String status);

    ResultMap get(Integer id);
}
