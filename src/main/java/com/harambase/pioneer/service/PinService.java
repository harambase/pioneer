package com.harambase.pioneer.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.pojo.base.Pin;
import com.harambase.pioneer.server.service.PinServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PinService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PinServerService pinServerService;

    @Autowired
    public PinService(PinServerService pinServerService) {
        this.pinServerService = pinServerService;
    }


    public ResultMap generateAll(String startTime, String endTime, String roleString, String info, String remark) {
        try {
            return pinServerService.generateAll(startTime, endTime, Integer.valueOf(roleString), info, remark);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap generateOne(String startTime, String endTime, String roleString, String info, String remark, String userId) {
        try {
            return pinServerService.generateOne(startTime, endTime, Integer.valueOf(roleString), info, remark, userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap deleteSingleByPin(Integer pin) {
        try {
            return pinServerService.deleteByPinNum(pin);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap deleteAllByInfo(String info) {
        try {
            return pinServerService.deleteByInfo(info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap validate(Integer pinNum, String userId) {
        try {
            return pinServerService.validate(pinNum, userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap listByInfo(int start, int length, String search, String order, String orderColumn, String info, String ownerId, String role) {
        try {
            return pinServerService.list(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderColumn, info, ownerId, role);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap sendFacultyPin(String info, String senderId) {
        try {
            return pinServerService.sendFacultyPin(info, senderId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap getAllInfo() {
        try {
            return pinServerService.getAllInfo();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap sendStudentPin(String info, String senderId) {
        try {
            return pinServerService.sendStudentPin(info, senderId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap sendAdvisorPin(String info, String senderId) {
        try {
            return pinServerService.sendAdvisorPin(info, senderId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap resend(Pin pin, String userId) {
        try {
            return pinServerService.resendPin(pin, userId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap updateOne(Integer pinNum, Pin pin) {
        try {
            return pinServerService.updateByPinNum(pinNum, pin);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap sendSelfFeedbackPin(String info, String senderId) {
        try {
            return pinServerService.sendSelfFeedbackPin(info, senderId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap sendOtherFeedbackPin(String info, String senderId) {
        try {
            return pinServerService.sendOtherFeedbackPin(info, senderId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }
}
