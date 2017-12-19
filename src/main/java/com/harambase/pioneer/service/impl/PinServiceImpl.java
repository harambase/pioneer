package com.harambase.pioneer.service.impl;

import com.harambase.pioneer.pojo.base.MessageWithBLOBs;
import com.harambase.support.util.DateUtil;
import com.harambase.common.HaramMessage;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.server.AdviseServer;
import com.harambase.pioneer.server.MessageServer;
import com.harambase.pioneer.server.PersonServer;
import com.harambase.pioneer.server.PinServer;
import com.harambase.common.helper.TimeValidate;
import com.harambase.pioneer.pojo.*;
import com.harambase.pioneer.service.PinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PinServiceImpl implements PinService{
    private final PinServer pinServer;
    private final PersonServer personServer;
    private final MessageServer messageServer;
    private final AdviseServer adviseServer;
    
    @Autowired
    public PinServiceImpl(PersonServer personServer, PinServer pinServer,
                          MessageServer messageServer, AdviseServer adviseServer){
        this.personServer = personServer;
        this.pinServer = pinServer;
        this.messageServer = messageServer;
        this.adviseServer = adviseServer;
    }
    @Override
    public HaramMessage validate(Integer pinNum) {
        HaramMessage haramMessage = new HaramMessage();
        try{
            Pin pin = pinServer.selectByPin(pinNum);
            if(pin != null && TimeValidate.isPinValidate(pin)){
                haramMessage.setCode(FlagDict.SUCCESS.getV());
                haramMessage.setMsg(FlagDict.SUCCESS.getM());
                haramMessage.setData(pin);
                return haramMessage;
            }

        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
        haramMessage.setCode(FlagDict.FAIL.getV());
        haramMessage.setMsg(FlagDict.FAIL.getM());
        return haramMessage;
    }
    
    @Override
    public HaramMessage generate(String startTime, String endTime, int role, String info, String remark) {
        HaramMessage haramMessage = new HaramMessage();
        try{
            Map<String, Object> param = new HashMap<>();
            Pin pin = new Pin();
            int pinNum, count;
            
            switch (role){
                case 1:
                    param.put("type", "s");
                    param.put("status", "1");
                    break;
                case 2:
                    param.put("type", "f");
                    param.put("status", "1");
                    break;
            }
            param.put("role", role);

            Object intObject = pinServer.countByInfo(param);
            count = 0;
            if(intObject != null)
                count = (Integer) intObject;

            if(count > 0){
                haramMessage.setCode(FlagDict.PIN_EXISTS.getV());
                haramMessage.setMsg(FlagDict.PIN_EXISTS.getM());
                return haramMessage;
            }
            
            List<Person> personList = personServer.getUsersBySearch(param);


            for(Person person : personList){
                do{
                    pinNum = (int)(Math.random() * (999999 - 100000 + 1) + 100000);
                    param.put("pin", pin);
                    count = pinServer.countByPin(pinNum);
                }while(count != 0);

                pin.setPin(pinNum);
                pin.setStarttime(startTime);
                pin.setEndtime(endTime);
                pin.setCreatetime(DateUtil.DateToStr(new Date()));
                pin.setRole(role);
                pin.setInfo(info);
                pin.setRemark(remark);

                switch (role){
                    case 1:
                        pin.setStudentid(person.getUserid());
                        break;
                    case 2:
                        pin.setFacultyid(person.getUserid());
                        break;
                }

                int ret = pinServer.insert(pin);
                if(ret != 1)
                    throw new RuntimeException("插入失败");
            }
            
        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
        haramMessage.setCode(FlagDict.SUCCESS.getV());
        haramMessage.setMsg(FlagDict.SUCCESS.getM());
        return haramMessage;
    }
    
    @Override
    public HaramMessage clearAll(String info) {
        return null;
    }
    
    @Override
    public HaramMessage listByInfo(String info) {
        HaramMessage haramMessage = new HaramMessage();
        try{
            Map<String, Object> param = new HashMap<>();
            param.put("info", info);
            List<Pin> pinInfoList = pinServer.listByInfo(param);
            haramMessage.setData(pinInfoList);
        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
        haramMessage.setCode(FlagDict.SUCCESS.getV());
        haramMessage.setMsg(FlagDict.SUCCESS.getM());
        return haramMessage;
    }

    @Override
    public HaramMessage sendFacultyPin(String info, String senderId){
        HaramMessage haramMessage = new HaramMessage();
        try{
            Map<String, Object> param = new HashMap<>();
            param.put("info", info);
            List<Pin> pinInfoList = pinServer.listByInfo(param);

            String date = DateUtil.DateToStr(new Date());
            MessageWithBLOBs message = new MessageWithBLOBs();
            message.setDate(date);
            message.setStatus("UNREAD");
            message.setTitle("PIN的信息");
            message.setSenderid(senderId);
            message.setAttachment(null);
            message.setLabels("inbox/important/");
            message.setTag("work");
            String body;
            String facultyId;
            for(Pin pin : pinInfoList){
                if(pin.getRole() == 2){
                    facultyId = pin.getFacultyid();
                    body = "您的账号用于管理学生的成绩的PIN号码是：" + pin.getPin() + "，有效期为："
                            + pin.getStarttime() + "至" + pin.getEndtime();
                    message.setReceiverid(facultyId);
                    message.setBody(body);
                    int ret = messageServer.insert(message);
                    if(ret != 1)
                        throw new RuntimeException("插入失败!");
                }
            }


        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
        haramMessage.setCode(FlagDict.SUCCESS.getV());
        haramMessage.setMsg(FlagDict.SUCCESS.getM());
        return haramMessage;
    }

    @Override
    public HaramMessage delete(String pin) {
        return null;
    }

    @Override
    public HaramMessage sendAdvisorPin(String info, String senderId){
        HaramMessage haramMessage = new HaramMessage();
        try{
            Map<String, Object> param = new HashMap<>();
            param.put("info", info);
            List<Pin> pinInfoList = pinServer.listByInfo(param);

            String date = DateUtil.DateToStr(new Date());
            MessageWithBLOBs message = new MessageWithBLOBs();
            message.setDate(date);
            message.setStatus("UNREAD");
            message.setTitle("您的辅导学生的PIN的信息");
            message.setSenderid(senderId);
            message.setAttachment(null);
            message.setLabels("inbox/important/");
            message.setTag("work");

            String body;
            String facultyId;
            String studentId;
            String studentName;
            Person student;

            for(Pin pin : pinInfoList){
                if(pin.getRole() == 1){
                    studentId = pin.getStudentid();
                    facultyId = adviseServer.selectFacultyByStudent(studentId);
                    student = personServer.selectByPrimaryKey(studentId);
                    studentName = student.getLastname() + ", " + student.getFirstname();

                    body = "您的辅导学生"+ studentName +"用于选课的PIN是：" + pin.getPin() + "，有效期为："
                            + pin.getStarttime() + "至" + pin.getEndtime() + "请及时告知，谢谢！";
                    message.setReceiverid(facultyId);
                    message.setBody(body);
                    int ret = messageServer.insert(message);
                    if(ret != 1)
                        throw new RuntimeException("插入失败!");
                }
            }


        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
        haramMessage.setCode(FlagDict.SUCCESS.getV());
        haramMessage.setMsg(FlagDict.SUCCESS.getM());
        return haramMessage;
    }
}
