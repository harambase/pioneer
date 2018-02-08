package com.harambase.pioneer.server;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Advise;
import com.harambase.pioneer.server.service.AdviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdviseServer {

    private AdviseService adviseService;

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

    public ResultMap update(Integer id, String studentId, String facultyId) {
        return adviseService.updateAdvise(id, studentId, facultyId);
    }

    public ResultMap get(Integer id) {
        return adviseService.getMentor(id);
    }

    public ResultMap list(Integer start, Integer length, String search, String order, String orderCol, String studentId, String facultyId) {
        return adviseService.advisingList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, studentId, facultyId);
    }

}
