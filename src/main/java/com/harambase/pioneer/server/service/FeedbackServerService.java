package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Advise;
import com.harambase.pioneer.server.pojo.base.Feedback;

public interface FeedbackServerService {
    
    ResultMap create(Feedback feedback);

    ResultMap delete(Integer id);

    ResultMap update(Integer id, Feedback feedback);

    ResultMap retrieve(Integer id);

    ResultMap list(String s, String s1, String search, String order, String orderCol, String facultyId, String info);
}
