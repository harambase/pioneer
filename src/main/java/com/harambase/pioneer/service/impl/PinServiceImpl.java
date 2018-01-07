package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.server.PinServer;
import com.harambase.pioneer.service.PinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PinServiceImpl implements PinService {

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
        if(roleString.contains(",")) {
            String msg = "返回信息：";
            HaramMessage message_1 = pinServer.generatAll(IP, PORT, startTime, endTime, Integer.valueOf(roleString.split(",")[0]), info, remark);
            HaramMessage message_2 = pinServer.generatAll(IP, PORT, startTime, endTime, Integer.valueOf(roleString.split(",")[1]), info, remark);
            if(message_1.getCode() != 2001 || message_2.getCode() != 2001) {
                msg += message_1.getMsg();
                msg += message_2.getMsg();
                retMessage.setCode(FlagDict.FAIL.getV());
                retMessage.setMsg(msg);
                return retMessage;
            }else{
                retMessage.setCode(FlagDict.SUCCESS.getV());
                retMessage.setMsg(FlagDict.SUCCESS.getM());
            }
            return retMessage;
        }else {
            return pinServer.generatAll(IP, PORT, startTime, endTime, Integer.valueOf(roleString), info, remark);
        }
    }

    @Override
    public HaramMessage generateOne(String startTime, String endTime, int role, String info, String remark, String userId) {
        return pinServer.generateOne(IP, PORT, startTime, endTime, role, info, remark, userId);
    }

    @Override
    public HaramMessage deleteSingleByPin(String pin) {
        return pinServer.deleteSingleByPin(IP, PORT, pin);
    }

    @Override
    public HaramMessage deleteAllByInfo(String info) {
        return pinServer.deleteAllByInfo(IP, PORT, info);
    }

    @Override
    public HaramMessage validate(Integer pinNum) {
        return pinServer.validate(IP, PORT, pinNum);
    }

    @Override
    public HaramMessage listByInfo(int start, int length, String search, String order, String orderColumn, String info) {
        return pinServer.listByInfo(IP, PORT, start, length, search, order, orderColumn, info);
    }

    @Override
    public HaramMessage sendFacultyPin(String info, String senderId) {
        return pinServer.sendFacultyPin(IP, PORT, info, senderId);
    }

    @Override
    public HaramMessage getAllInfo() {
        return pinServer.getAllInfo(IP, PORT);
    }

    @Override
    public HaramMessage sendAdvisorPin(String info, String senderId) {
        return pinServer.sendAdvisorPin(IP, PORT, info, senderId);
    }
}
