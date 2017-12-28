package com.harambase.pioneer.service;

import com.alibaba.fastjson.JSONObject;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.TempUser;

public interface RequestService {

    HaramMessage deleteTempUserById(Integer id);

    HaramMessage register(JSONObject jsonObject);

    HaramMessage updateTempUser(Integer id, TempUser tempUser);

    HaramMessage tempUserList(int start, int length, String search, String order, String orderCol, String viewStatus);
}
