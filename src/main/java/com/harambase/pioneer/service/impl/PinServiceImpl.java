package com.harambase.pioneer.service.impl;

import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.common.constant.FlagDict;
import com.harambase.pioneer.server.PinServer;
import com.harambase.pioneer.service.PinService;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PinServiceImpl implements PinService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private final PinServer pinServer;

    @Autowired
    public PinServiceImpl(PinServer pinServer) {
        this.pinServer = pinServer;
    }

    @Override
    public HaramMessage generateAll(String startTime, String endTime, String roleString, String info, String remark) {
        HaramMessage retMessage = new HaramMessage();
        if (roleString.contains(",")) {
            String msg = "返回信息：";
            HaramMessage message_1 = pinServer.createAll(startTime, endTime, Integer.valueOf(roleString.split(",")[0]), info, remark);
            HaramMessage message_2 = pinServer.createAll(startTime, endTime, Integer.valueOf(roleString.split(",")[1]), info, remark);
            if (message_1.getCode() != 2001 || message_2.getCode() != 2001) {
                msg += message_1.getMsg();
                msg += message_2.getMsg();
                retMessage.setCode(FlagDict.FAIL.getV());
                retMessage.setMsg(msg);
                return retMessage;
            } else {
                retMessage.setCode(FlagDict.SUCCESS.getV());
                retMessage.setMsg(FlagDict.SUCCESS.getM());
            }
            return retMessage;
        } else {
            return pinServer.createAll(startTime, endTime, Integer.valueOf(roleString), info, remark);
        }
    }

    @Override
    public HaramMessage generateOne(String startTime, String endTime, int role, String info, String remark, String userId) {
        try {
            return pinServer.createOne(startTime, endTime, role, info, remark, userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage deleteSingleByPin(Integer pin) {
        try {
            return pinServer.delete(pin);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage deleteAllByInfo(String info) {
        try {
            return pinServer.deleteAll(info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage validate(Integer pinNum, String userId) {
        try {
            return pinServer.validate(pinNum, userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage listByInfo(int start, int length, String search, String order, String orderColumn, String info) {
        try {
            return pinServer.list(start, length, search, order, orderColumn, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage sendFacultyPin(String info, String senderId) {
        try {
            return pinServer.sendFacultyPin(info, senderId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage getAllInfo() {
        try {
            return pinServer.getAllInfo();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage sendAdvisorPin(String info, String senderId) {
        try {
            return pinServer.sendAdvisorPin(info, senderId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }
}
