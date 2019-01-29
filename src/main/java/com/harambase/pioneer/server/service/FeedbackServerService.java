package com.harambase.pioneer.server.service;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Feedback;

public interface FeedbackServerService {

    ResultMap delete(Integer id);

    ResultMap update(Integer id, Feedback feedback);

    ResultMap retrieve(Integer id);

    ResultMap list(String s, String s1, String search, String order, String orderCol, String facultyId, String info);

    ResultMap generateAll(String info, String password, String opId);

    ResultMap generateOne(String info, String userId, String password, String opId);

    ResultMap find(String facultyId);

    ResultMap updateFeedbackOther(Integer id, Feedback feedback);

    ResultMap encrypt(String info, String password, String oldPassword, String opId);

    ResultMap validate(String info, String password);

    ResultMap decryptList(String s, String s1, String search, String order, String orderCol, String facultyId, String password, String info);
}
