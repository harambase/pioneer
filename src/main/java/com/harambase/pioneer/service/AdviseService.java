package com.harambase.pioneer.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.AdviseServer;
import com.harambase.pioneer.server.pojo.base.Advise;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdviseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AdviseServer adviseServer;

    @Autowired
    public AdviseService(AdviseServer adviseServer) {
        this.adviseServer = adviseServer;
    }

    
    public ResultMap advisingList(int start, int length, String search, String order, String orderColumn, String studentId, String facultyId) {
        try {
            return adviseServer.list(start, length, search, order, orderColumn, studentId, facultyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap updateAdvise(Integer id, String studentId, String facultyId) {
        try {
            return adviseServer.update(id, studentId, facultyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap assignMentor(Advise advise) {
        try {
            return adviseServer.create(advise);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap removeMentor(Integer id) {
        try {
            return adviseServer.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap getMentor(Integer id) {
        try {
            return adviseServer.get(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
