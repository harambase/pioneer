package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Transcript;

public interface TranscriptService {

    ResultMap updateGrade(Integer id, Transcript transcript);

    ResultMap transcriptList(String s, String s1, String search, String order, String orderCol,
                             String studentId, String crn, String info, String complete);

    ResultMap getDistinctColumnByInfo(String column, String info);
}
