package com.harambase.pioneer.server;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Advise;
import com.harambase.pioneer.server.service.AdviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdviseServer {

    private final AdviseService adviseService;

    @Autowired
    public AdviseServer(AdviseService adviseService) {
        this.adviseService = adviseService;
    }

    public ResultMap create(Advise advise) {
        return adviseService.assignMentor(advise);
    }

    public ResultMap delete(Integer id) {
        return adviseService.removeMentor(id);
    }

    public ResultMap update(Integer id, Advise advise) {
        return adviseService.updateAdvise(id, advise);
    }

    public ResultMap get(Integer id) {
        return adviseService.getMentor(id);
    }

    public ResultMap list(Integer start, Integer length, String search, String order, String orderCol, String studentId, String facultyId, String info) {
        return adviseService.advisingList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, studentId, facultyId, info);
    }

    public ResultMap advisorList(int start, int length, String search, String order, String orderColumn, String status) {
        return adviseService.advisorList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderColumn, status);
    }

    public ResultMap removeAdvisor(String userId) {
        return adviseService.removeAdvisor(userId);
    }

    public ResultMap addAdvisor(String userId) {
        return adviseService.addAdvisor(userId);
    }
}
