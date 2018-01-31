package com.harambase.pioneer.service.impl;

import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.server.AdviseServer;
import com.harambase.pioneer.server.pojo.base.Advise;
import com.harambase.pioneer.service.AdviseService;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdviseServerCaller implements AdviseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AdviseServer adviseServer;

    @Autowired
    public AdviseServerCaller(AdviseServer adviseServer) {
        this.adviseServer = adviseServer;
    }

    @Override
    public HaramMessage advisingList(int start, int length, String search, String order, String orderColumn, String studentId, String facultyId) {
        try {
            return adviseServer.list(start, length, search, order, orderColumn, studentId, facultyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage updateAdvise(Integer id, String studentId, String facultyId) {
        try {
            return adviseServer.update(id, studentId, facultyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage assignMentor(Advise advise) {
        try {
            return adviseServer.create(advise);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage removeMentor(Integer id) {
        try {
            return adviseServer.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage getMentor(Integer id) {
        try {
            return adviseServer.get(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
