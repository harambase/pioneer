package com.harambase.pioneer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.harambase.common.*;
import com.harambase.common.constant.FlagDict;
import com.harambase.support.util.DateUtil;
import com.harambase.support.util.IDUtil;
import com.harambase.support.util.PageUtil;
import com.harambase.pioneer.server.MessageServer;
import com.harambase.pioneer.server.TempCourseServer;
import com.harambase.pioneer.server.TempUserServer;
import com.harambase.pioneer.pojo.base.MessageWithBLOBs;
import com.harambase.pioneer.pojo.TempUser;
import com.harambase.pioneer.pojo.Advise;
import com.harambase.pioneer.service.RequestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RequestServiceImpl implements RequestService {

    private final TempUserServer tempUserServer;
    private final TempCourseServer tempCourseServer;
    private final MessageServer messageServer;

    @Autowired
    public RequestServiceImpl(TempUserServer tempUserServer,
                              TempCourseServer tempCourseServer,
                              MessageServer messageServer){
        this.tempCourseServer = tempCourseServer;
        this.tempUserServer = tempUserServer;
        this.messageServer = messageServer;
    }

    @Override
    public HaramMessage deleteTempUserById(Integer id) {
        HaramMessage haramMessage = new HaramMessage();
        try{
            int ret = tempUserServer.deleteByPrimaryKey(id);
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

            int ret = tempUserServer.insert(tempUser);
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

            ret = messageServer.insertSelective(message);
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
            int ret = tempUserServer.updateByPrimaryKeySelective(tempUser);

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

    @Override
    public HaramMessage tempUserList(String currentPage, String pageSize, String search, String order, String orderColumn, String viewStatus) {
        HaramMessage message = new HaramMessage();
        switch (Integer.parseInt(orderColumn)) {
            case 1:
                orderColumn = "userid";
                break;
            case 2:
                orderColumn = "createtime";
                break;
            default:
                orderColumn = "id";
                break;
        }
        long totalSize = 0;
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("search", search);
            param.put("status", viewStatus);

            if(StringUtils.isEmpty(search))
                param.put("search", null);
            if(StringUtils.isEmpty(viewStatus))
                param.put("status", null);

            totalSize = tempUserServer.getTempUserCountByMapPageSearchOrdered(param); //startTime, endTime);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            param.put("currentIndex", page.getCurrentIndex());
            param.put("pageSize",  page.getPageSize());
            param.put("order",  order);
            param.put("orderColumn",  orderColumn);

            //(int currentIndex, int pageSize, String search, String order, String orderColumn);
            List<Advise> msgs = tempUserServer.getTempUserByMapPageSearchOrdered(param);

            message.setData(msgs);
            message.put("page", page);
            message.setMsg(FlagDict.SUCCESS.getM());
            message.setCode(FlagDict.SUCCESS.getV());
            return message;

        }catch (Exception e) {
            e.printStackTrace();
            message.setMsg(FlagDict.SYSTEM_ERROR.getM());
            message.setCode(FlagDict.SYSTEM_ERROR.getV());
            return message;
        }
    }

}
