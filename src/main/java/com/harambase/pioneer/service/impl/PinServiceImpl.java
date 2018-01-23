package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.server.PinServer;
import com.harambase.pioneer.service.PinService;
import com.harambase.support.util.ReturnMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PinServiceImpl implements PinService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

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
            HaramMessage message_1 = pinServer.generatAll(IP, PORT, startTime, endTime, Integer.valueOf(roleString.split(",")[0]), info, remark);
            HaramMessage message_2 = pinServer.generatAll(IP, PORT, startTime, endTime, Integer.valueOf(roleString.split(",")[1]), info, remark);
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
            return pinServer.generatAll(IP, PORT, startTime, endTime, Integer.valueOf(roleString), info, remark);
        }
    }

    @Override
    public HaramMessage generateOne(String startTime, String endTime, int role, String info, String remark, String userId) {
        try {
            return pinServer.generateOne(IP, PORT, startTime, endTime, role, info, remark, userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage deleteSingleByPin(String pin) {
        try {
            return pinServer.deleteSingleByPin(IP, PORT, pin);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage deleteAllByInfo(String info) {
        try {
            return pinServer.deleteAllByInfo(IP, PORT, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage validate(Integer pinNum) {
        try {
            return pinServer.validate(IP, PORT, pinNum);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage listByInfo(int start, int length, String search, String order, String orderColumn, String info) {
        try {
            return pinServer.listByInfo(IP, PORT, start, length, search, order, orderColumn, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage sendFacultyPin(String info, String senderId) {
        try {
            return pinServer.sendFacultyPin(IP, PORT, info, senderId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage getAllInfo() {
        try {
            return pinServer.getAllInfo(IP, PORT);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage sendAdvisorPin(String info, String senderId) {
        try {
            return pinServer.sendAdvisorPin(IP, PORT, info, senderId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }
}
