package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Transcript;

public interface TranscriptServerService {

    ResultMap update(Integer id, Transcript transcript);

    ResultMap list(String currentPage, String pageSize, String search, String order, String orderCol, String studentId, String crn, String info, String complete);

    ResultMap getDistinctColumnByInfo(String column, String info);
}
