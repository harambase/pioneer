package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Advise;
import com.harambase.pioneer.server.AdviseServer;
import com.harambase.pioneer.service.AdviseService;
import com.harambase.support.util.ReturnMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdviseServiceImpl implements AdviseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final AdviseServer adviseServer;

    @Autowired
    public AdviseServiceImpl(AdviseServer adviseServer) {
        this.adviseServer = adviseServer;
    }

    @Override
    public HaramMessage advisingList(int start, int length, String search, String order, String orderColumn, String studentId, String facultyId) {
        try {
            return adviseServer.advisingList(IP, PORT, start, length, search, order, orderColumn, studentId, facultyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage updateAdvise(Integer id, String studentId, String facultyId) {
        try {
            return adviseServer.updateAdvise(IP, PORT, id, studentId, facultyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage assignMentor(Advise advise) {
        try {
            return adviseServer.assignMentor(IP, PORT, advise);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage removeMentor(Integer id) {
        try {
            return adviseServer.removeMentor(IP, PORT, id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage getMentor(Integer id) {
        try {
            return adviseServer.getMentor(IP, PORT, id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
