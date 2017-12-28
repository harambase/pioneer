package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.base.AdviseBase;
import com.harambase.pioneer.server.AdviseServer;
import com.harambase.pioneer.service.AdviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdviseServiceImpl implements AdviseService {

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final AdviseServer adviseServer;

    @Autowired
    public AdviseServiceImpl(AdviseServer adviseServer) {
        this.adviseServer = adviseServer;
    }

    @Override
    public HaramMessage advisingList(int start, int length, String search, String order, String orderColumn, String studentId, String facultyId) {
        return adviseServer.advisingList(IP, PORT, start, length, search, order, orderColumn, studentId, facultyId);
    }

    @Override
    public HaramMessage updateAdvise(Integer id, String studentId, String facultyId) {
        return adviseServer.updateAdvise(IP, PORT, id, studentId, facultyId);
    }

    @Override
    public HaramMessage assignMentor(AdviseBase advise) {
        return adviseServer.assignMentor(IP, PORT, advise);
    }

    @Override
    public HaramMessage removeMentor(Integer id) {
        return adviseServer.removeMentor(IP, PORT, id);
    }

    @Override
    public HaramMessage getMentor(Integer id) {
        return adviseServer.getMentor(IP, PORT, id);
    }

}
