package com.harambase.pioneer.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.harambase.common.DateUtil;
import com.harambase.common.HaramMessage;
import com.harambase.common.IDUtil;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.dao.mapper.MessageMapper;
import com.harambase.pioneer.dao.mapper.TempCourseMapper;
import com.harambase.pioneer.dao.mapper.TempUserMapper;
import com.harambase.pioneer.pojo.MessageWithBLOBs;
import com.harambase.pioneer.pojo.TempUser;
import com.harambase.pioneer.service.RequestSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RequestServiceImpl implements RequestSerivce{

    private final TempUserMapper tempUserMapper;
    private final TempCourseMapper tempCourseMapper;
    private final MessageMapper messageMapper;

    @Autowired
    public RequestServiceImpl(TempUserMapper tempUserMapper,
                              TempCourseMapper tempCourseMapper,
                              MessageMapper messageMapper){
        this.tempCourseMapper = tempCourseMapper;
        this.tempUserMapper = tempUserMapper;
        this.messageMapper = messageMapper;
    }

    @Override
    public HaramMessage deleteTempUserById(Integer id) {
        HaramMessage haramMessage = new HaramMessage();
        try{
            int ret = tempUserMapper.deleteByPrimaryKey(id);
            if(ret < 0)
                throw new RuntimeException("删除失败");

            haramMessage.setCode(FlagDict.SUCCESS.getV());
            haramMessage.setMsg(FlagDict.SUCCESS.getM());

        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
        }
        return haramMessage;
    }

    @Override
    @Transactional
    public HaramMessage register(JSONObject jsonObject) {
        HaramMessage haramMessage = new HaramMessage();
        try{

            String userid = IDUtil.genUserID(jsonObject.getString("info"));

            TempUser tempUser = new TempUser();
            tempUser.setUserid(userid);
            tempUser.setUserJson(jsonObject.toJSONString());
            tempUser.setCreatetime(DateUtil.DateToStr(new Date()));
            tempUser.setUpdatetime(DateUtil.DateToStr(new Date()));
            tempUser.setStatus("0");

            int ret = tempUserMapper.insert(tempUser);
            if(ret <= 0)
                throw new RuntimeException("TempUser 插入失败!");

            MessageWithBLOBs message = new MessageWithBLOBs();
            message.setDate(DateUtil.DateToStr(new Date()));
            message.setReceiverid("9000000000");
            message.setSenderid("9000000000");
            message.setBody("注意!接收到来自" + userid + "的请求注册信息");
            message.setTitle("注册信息");
            message.setStatus("UNREAD");
            message.setSubject("用户注册");
            message.setTag("work");
            message.setLabels("inbox/important/");

            ret = messageMapper.insertSelective(message);
            if(ret <= 0)
                throw new RuntimeException("MessageWithBLOBs 插入失败!");
            haramMessage.setCode(FlagDict.SUCCESS.getV());
            haramMessage.setMsg(FlagDict.SUCCESS.getM());

        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
        }
        return haramMessage;
    }

    @Override
    public HaramMessage updateTempUser(TempUser tempUser) {
        HaramMessage haramMessage = new HaramMessage();
        try {
            tempUser.setUpdatetime(DateUtil.DateToStr(new Date()));
            int ret = tempUserMapper.updateByPrimaryKeySelective(tempUser);

            if(ret <= 0)
                throw new RuntimeException("TempUser 更新失败!");

            haramMessage.setCode(FlagDict.SUCCESS.getV());
            haramMessage.setMsg(FlagDict.SUCCESS.getM());

        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
        }
        return haramMessage;
    }
}
