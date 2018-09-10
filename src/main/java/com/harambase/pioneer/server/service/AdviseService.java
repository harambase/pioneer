package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Advise;

public interface AdviseService {

    ResultMap advisingList(String s, String s1, String search, String order, String orderCol, String studentId, String facultyId, String info);

    ResultMap updateAdvise(Integer id, Advise advise);

    ResultMap assignMentor(Advise advise);

    ResultMap removeMentor(Integer id);

    ResultMap getMentor(Integer id);

    ResultMap getAdviseByStudentId(String studentId);

    ResultMap advisorList(String s, String s1, String search, String order, String orderCol, String status);

    ResultMap removeAdvisor(String userId);

    ResultMap addAdvisor(String userId);

    ResultMap getAdvisor(String userId);

    ResultMap getAdviseViewByStudentId(String studentId);
}
