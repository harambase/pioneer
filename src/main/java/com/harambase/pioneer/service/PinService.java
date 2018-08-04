package com.harambase.pioneer.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.PinServer;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.pojo.base.Pin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PinService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PinServer pinServer;

    @Autowired
    public PinService(PinServer pinServer) {
        this.pinServer = pinServer;
    }


    public ResultMap generateAll(String startTime, String endTime, String roleString, String info, String remark) {
        try {
            return pinServer.createAll(startTime, endTime, Integer.valueOf(roleString), info, remark);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap generateOne(String startTime, String endTime, String roleString, String info, String remark, String userId) {
        try {
            return pinServer.createOne(startTime, endTime, Integer.valueOf(roleString), info, remark, userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap deleteSingleByPin(Integer pin) {
        try {
            return pinServer.delete(pin);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap deleteAllByInfo(String info) {
        try {
            return pinServer.deleteAll(info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap validate(Integer pinNum, String userId) {
        try {
            return pinServer.validate(pinNum, userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap listByInfo(int start, int length, String search, String order, String orderColumn, String info, String ownerId) {
        try {
            return pinServer.list(start, length, search, order, orderColumn, info, ownerId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap sendFacultyPin(String info, String senderId) {
        try {
            return pinServer.sendFacultyPin(info, senderId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap getAllInfo() {
        try {
            return pinServer.getAllInfo();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap sendAdvisorPin(String info, String senderId) {
        try {
            return pinServer.sendAdvisorPin(info, senderId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap resend(Pin pin, String userId) {
        try {
            return pinServer.resend(pin, userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap updateOne(Integer pinNum, Pin pin) {
        try {
            return pinServer.updateOne(pinNum, pin);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }
}
