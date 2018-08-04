package com.harambase.pioneer.server;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.IDUtil;
import com.harambase.pioneer.helper.MessageSender;
import com.harambase.pioneer.server.pojo.base.Pin;
import com.harambase.pioneer.server.pojo.view.AdviseView;
import com.harambase.pioneer.server.service.PinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PinServer {

    private final PinService pinService;
    private final MessageSender messageSender;
    private final AdviseServer adviseServer;

    @Autowired
    public PinServer(PinService pinService, MessageSender messageSender, AdviseServer adviseServer) {
        this.pinService = pinService;
        this.adviseServer = adviseServer;
        this.messageSender = messageSender;
    }

    public ResultMap createAll(String startTime, String endTime, int role, String info, String remark) {
        return pinService.generateAll(startTime, endTime, role, info, remark);
    }

    public ResultMap createOne(String startTime, String endTime, int role, String info, String remark, String userId) {
        ResultMap resultMap = pinService.generateOne(startTime, endTime, role, info, remark, userId);

        if (resultMap.getCode() == SystemConst.SUCCESS.getCode()) {
            Pin pin = (Pin) resultMap.getData();
            int pinNum = pin.getPin();
            if (role == 2) {//2是 成绩录入
                messageSender.sendFacultyPin(pin, IDUtil.ROOT);
                messageSender.sendImportantSystemMsg(IDUtil.ROOT, IDUtil.ROOT,
                        "您接收到来自系统的一条消息:来自用户 " + userId + " 的成绩录入识别码" + pinNum + " 已生成！", "成绩录入识别码", "识别码");
            } else {
                messageSender.sendAdvisorPin(pin, IDUtil.ROOT);
                messageSender.sendImportantSystemMsg(IDUtil.ROOT, IDUtil.ROOT,
                        "您接收到来自系统的一条消息:来自用户 " + userId + " 的选课的识别码" + pinNum + " 已生成！", "选课的识别码", "识别码");
            }
        }

        return resultMap;
    }

    public ResultMap validate(Integer pin, String userId) {
        return pinService.validate(pin, userId);
    }

    public ResultMap list(Integer start, Integer length, String search, String order, String orderCol, String info, String ownerId) {
        return pinService.listByInfo(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, info, ownerId);
    }

    public ResultMap delete(Integer pin) {
        return pinService.deleteSingleByPin(pin);
    }

    public ResultMap deleteAll(String info) {
        return pinService.deleteAllByInfo(info);
    }

    public ResultMap sendFacultyPin(String info, String senderId) {
        return messageSender.sendFacultyPinByInfo(info, senderId);
    }

    public ResultMap sendAdvisorPin(String info, String senderId) {
        return messageSender.sendAdvisorPinByInfo(info, senderId);
    }

    public ResultMap getAllInfo() {
        return pinService.getAllInfo();
    }

    public ResultMap resend(Pin pin, String userId) {
        return messageSender.resendPin(pin, userId);
    }

    public ResultMap updateOne(Integer pinNum, Pin pin) {
        return pinService.updateOne(pinNum, pin);
    }
}
