package com.harambase.pioneer.service.impl;

import com.harambase.support.util.DateUtil;
import com.harambase.common.HaramMessage;
import com.harambase.common.Page;
import com.harambase.support.util.PageUtil;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.server.MessageServer;
import com.harambase.pioneer.pojo.base.MessageWithBLOBs;
import com.harambase.pioneer.pojo.Message;
import com.harambase.pioneer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageServer messageServer;

    @Autowired
    public MessageServiceImpl(MessageServer messageServer){
        this.messageServer = messageServer;
    }

    @Override
    public HaramMessage list(String currentPage, String pageSize, String search, String order, String orderColumn,
                             String receiverid, String senderid, String box) {
        HaramMessage message = new HaramMessage();
        switch (Integer.parseInt(orderColumn)) {
            case 1:
                orderColumn = "sender";
                break;
            case 2:
                orderColumn = "title";
                break;
            case 3:
                orderColumn = "body";
                break;
            case 4:
                orderColumn = "status";
                break;
            default:
                orderColumn = "date";
                break;
        }
        long totalSize = 0;
        try {
            totalSize = messageDao.getMessageCountByMapPageSearchOrdered(receiverid, senderid, box, search); //startTime, endTime);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            List<Message> msgs = messageDao.getMessageByMapPageSearchOrdered(receiverid, senderid, box, search,
                    page.getCurrentIndex(),page.getPageSize(),order,orderColumn);

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

    @Override
    public HaramMessage getMessageView(Integer id) {
        HaramMessage haramMessage = new HaramMessage();
        try{
            Message messageView = messageServer.selectViewByPrimaryKey(id);
            haramMessage.setData(messageView);
            haramMessage.setMsg(FlagDict.SUCCESS.getM());
            haramMessage.setCode(FlagDict.SUCCESS.getV());
            return haramMessage;
        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            return haramMessage;
        }
    }
    
    @Override
    public HaramMessage countMessageByStatus(String receiverid, String senderid, String box, String status) {
        HaramMessage haramMessage = new HaramMessage();
        try{
            int count = messageDao.countMessageByStatus(receiverid, senderid, box, status);
            haramMessage.setData(count);
            haramMessage.setMsg(FlagDict.SUCCESS.getM());
            haramMessage.setCode(FlagDict.SUCCESS.getV());
            return haramMessage;
        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            return haramMessage;
        }
    }
    
    @Override
    public HaramMessage update(Integer id, MessageWithBLOBs message) {
        HaramMessage haramMessage = new HaramMessage();
        try{
            message.setId(id);
            int ret = messageServer.updateByPrimaryKeySelective(message);
            if(ret != 1)
                throw new RuntimeException("更新失败");
            
            haramMessage.setMsg(FlagDict.SUCCESS.getM());
            haramMessage.setCode(FlagDict.SUCCESS.getV());
            return haramMessage;
        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            return haramMessage;
        }
    }
    
    @Override
    public HaramMessage createMessage(MessageWithBLOBs message) {
        HaramMessage haramMessage = new HaramMessage();
        try{
            message.setDate(DateUtil.DateToStr(new Date()));
            int ret = messageServer.insert(message);
            if(ret != 1)
                throw new RuntimeException("插入失败");
        
            haramMessage.setMsg(FlagDict.SUCCESS.getM());
            haramMessage.setCode(FlagDict.SUCCESS.getV());
            return haramMessage;
            
        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            return haramMessage;
        }
    }

    @Override
    public HaramMessage delete(Integer id) {
        HaramMessage haramMessage = new HaramMessage();
        try{
            int ret =  messageServer.deleteByPrimaryKey(id);
            if(ret != 1)
                throw new RuntimeException("插入失败");

            haramMessage.setMsg(FlagDict.SUCCESS.getM());
            haramMessage.setCode(FlagDict.SUCCESS.getV());
            return haramMessage;

        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            return haramMessage;
        }
    }

}
