package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Advise;

public interface AdviseServerService {

    ResultMap list(String s, String s1, String search, String order, String orderCol, String studentId, String facultyId, String info);

    ResultMap update(Integer id, Advise advise);

    ResultMap create(Advise advise);

    ResultMap remove(Integer id);

    ResultMap retrieve(Integer id);

    ResultMap getAdviseViewByStudentId(String studentId);

    //Advisor相关接口
    ResultMap advisorList(String s, String s1, String search, String order, String orderCol, String status);

    ResultMap removeAdvisor(String userId);

    ResultMap addAdvisor(String userId);

    ResultMap getAdvisor(String userId);

}
