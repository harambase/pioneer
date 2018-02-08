package com.harambase.pioneer.server;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.service.PinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PinServer {

    private final PinService pinService;

    @Autowired
    public PinServer(PinService pinService) {
        this.pinService = pinService;
    }

    public ResultMap createAll(String startTime, String endTime, int role, String info, String remark) {
        return pinService.generateAll(startTime, endTime, role, info, remark);
    }

    public ResultMap createOne(String startTime, String endTime, int role, String info, String remark, String userId) {
        return pinService.generateOne(startTime, endTime, role, info, remark, userId);
    }

    public ResultMap validate(Integer pin, String userId) {
        return pinService.validate(pin, userId);
    }

    public ResultMap list(Integer start, Integer length, String search, String order, String orderCol, String info) {
        return pinService.listByInfo(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, info);
    }

    public ResultMap delete(Integer pin) {
        return pinService.deleteSingleByPin(pin);
    }

    public ResultMap deleteAll(String info) {
        return pinService.deleteAllByInfo(info);
    }

    public ResultMap sendFacultyPin(String info, String senderId) {
        return pinService.sendFacultyPin(info, senderId);
    }

    public ResultMap sendAdvisorPin(String info, String senderId) {
        return pinService.sendAdvisorPin(info, senderId);
    }

    public ResultMap getAllInfo() {
        return pinService.getAllInfo();
    }
}
