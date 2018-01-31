package com.harambase.pioneer.server;

import com.harambase.pioneer.common.HaramMessage;
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

    public HaramMessage create(Advise advise) {
        return adviseService.assignMentor(advise);
    }

    public HaramMessage delete(Integer id) {
        return adviseService.removeMentor(id);
    }

    public HaramMessage update(Integer id, String studentId, String facultyId) {
        return adviseService.updateAdvise(id, studentId, facultyId);
    }

    public HaramMessage get(Integer id) {
        return adviseService.getMentor(id);
    }

    public HaramMessage list(Integer start, Integer length, String search, String order, String orderCol, String studentId, String facultyId) {
        return adviseService.advisingList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, studentId, facultyId);
    }

}
