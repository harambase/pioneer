package com.harambase.pioneer.server.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.DateUtil;
import com.harambase.pioneer.common.support.util.IDUtil;
import com.harambase.pioneer.common.support.util.PageUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.dao.base.TempUserDao;
import com.harambase.pioneer.server.dao.repository.MessageRepository;
import com.harambase.pioneer.server.dao.repository.TempUserRepository;
import com.harambase.pioneer.server.pojo.base.TempUser;
import com.harambase.pioneer.server.service.TempUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TempUserImpl implements TempUserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TempUserRepository tempUserRepository;
    private final MessageRepository messageRepository;

    private final TempUserDao tempUserDao;

    @Autowired
    public TempUserImpl(TempUserRepository tempUserRepository,
                        MessageRepository messageRepository,
                        TempUserDao tempUserDao) {
        this.tempUserRepository = tempUserRepository;
        this.messageRepository = messageRepository;
        this.tempUserDao = tempUserDao;
    }

    @Override
    public ResultMap deleteTempUserById(Integer id) {

        try {
            tempUserRepository.delete(id);
            int count = tempUserRepository.countById(id);
            return count == 0 ? ReturnMsgUtil.success(null) : ReturnMsgUtil.fail();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap register(JSONObject jsonObject) {

        try {
            String userId = IDUtil.genTempUserID(jsonObject.getString("info"));

            TempUser tempUser = new TempUser();
            tempUser.setUserId(userId);
            tempUser.setUserJson(jsonObject.toJSONString());
            tempUser.setCreateTime(DateUtil.DateToStr(new Date()));
            tempUser.setUpdateTime(DateUtil.DateToStr(new Date()));
            tempUser.setStatus("0");

            TempUser newTempUser = tempUserRepository.save(tempUser);

            return newTempUser != null ? ReturnMsgUtil.success(newTempUser) : ReturnMsgUtil.fail();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }

    }

    @Override
    public ResultMap updateTempUser(Integer id, TempUser tempUser) {
        try {
            tempUser.setId(id);
            tempUser.setUpdateTime(DateUtil.DateToStr(new Date()));
            TempUser newTempUser = tempUserRepository.save(tempUser);
            return newTempUser != null ? ReturnMsgUtil.success(newTempUser) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap tempUserList(String currentPage, String pageSize, String search, String order, String orderColumn, String status) {
        ResultMap message = new ResultMap();
        try {

            long totalSize = tempUserDao.getCountByMapPageSearchOrdered(search, status);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            List<TempUser> tempUsers = tempUserDao.getByMapPageSearchOrdered(page.getCurrentIndex(), page.getPageSize(), search, order, orderColumn, status);

            message.setData(tempUsers);
            message.put("page", page);
            message.setMsg(SystemConst.SUCCESS.getMsg());
            message.setCode(SystemConst.SUCCESS.getCode());
            return message;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap get(Integer id) {
        try {
            TempUser tempUser = tempUserRepository.findOne(id);
            return ReturnMsgUtil.success(tempUser);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
