package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.TempAdvise;

public interface TempAdviseServerService {

    ResultMap list(String currentPage, String pageSize, String search, String order, String orderCol, String viewStatus, String info, String studentId, String facultyId);

    ResultMap create(TempAdvise tempAdvise);

    ResultMap delete(Integer id);

    ResultMap update(Integer id, TempAdvise tempAdvise);

    ResultMap retrieve(String studentId);
}
