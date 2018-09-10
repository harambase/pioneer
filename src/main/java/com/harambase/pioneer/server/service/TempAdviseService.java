package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.TempAdvise;

public interface TempAdviseService {

    ResultMap register(TempAdvise tempAdvise);

    ResultMap deleteTempAdviseById(Integer id);

    ResultMap get(String studentId);

    ResultMap tempAdviseList(String s, String s1, String search, String order, String orderCol, String viewStatus, String info, String studentId, String facultyId);

    ResultMap updateTempAdvise(Integer id, TempAdvise tempAdvise);
}
