package com.harambase.pioneer.server.service.impl;


import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.DateUtil;
import com.harambase.pioneer.common.support.util.PageUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.dao.base.PersonDao;
import com.harambase.pioneer.server.dao.base.PinDao;
import com.harambase.pioneer.server.dao.repository.PinRepository;
import com.harambase.pioneer.server.helper.TimeValidate;
import com.harambase.pioneer.server.pojo.base.Person;
import com.harambase.pioneer.server.pojo.base.Pin;
import com.harambase.pioneer.server.pojo.view.PinView;
import com.harambase.pioneer.server.service.PinService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PinServiceImpl implements PinService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PinRepository pinRepository;

    private final PersonDao personDao;
    private final PinDao pinDao;

    @Autowired
    public PinServiceImpl(PinRepository pinRepository, PinDao pinDao, PersonDao personDao) {
        this.pinRepository = pinRepository;
        this.personDao = personDao;
        this.pinDao = pinDao;
    }

    @Override
    public ResultMap validate(Integer pinNum, String userId) {
        try {
            Pin pin = pinRepository.findByPin(pinNum);

            if (pin != null) {
                String ownerId = pin.getOwnerId();
                return (ownerId.equals(userId) && TimeValidate.isPinValidate(pin)) ? ReturnMsgUtil.success(pin) : ReturnMsgUtil.fail();
            }
            return ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap generateAll(String startTime, String endTime, int role, String info, String remark) {

        try {

            int count = pinRepository.countByInfoAndRole(info, role);
            if (count != 0) {
                return ReturnMsgUtil.custom(SystemConst.PIN_EXISTS);
            }

            List<Person> personList = new ArrayList<>();

            switch (role) {
                case 1:
                    personList = personDao.getPersonBySearch("", "s", "", "1", "");
                    break;
                case 2:
                    personList = personDao.getPersonBySearch("", "f", "", "1", "");
                    break;
                case 3:
                    personList = personDao.getPersonBySearch("", "s", "", "1", "");
                    break;
            }

            for (Person person : personList) {
                Pin pin = new Pin();
                int pinNum;
                do {
                    pinNum = (int) (Math.random() * (999999 - 100000 + 1) + 100000);
                    count = pinRepository.countByPin(pinNum);
                } while (count != 0);

                pin.setPin(pinNum);
                pin.setStartTime(startTime);
                pin.setEndTime(endTime);
                pin.setCreateTime(DateUtil.DateToStr(new Date()));
                pin.setRole(role);
                pin.setInfo(info);
                pin.setRemark(remark);
                pin.setOwnerId(person.getUserId());

                Pin newPin = pinRepository.save(pin);
                if (newPin == null)
                    throw new RuntimeException("PIN生成失败!");
            }
            return ReturnMsgUtil.success(null);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }

    }

    @Override
    public ResultMap deleteAllByInfo(String info) {
        try {
            pinRepository.deleteByInfo(info);
            int count = pinRepository.countByInfo(info);
            return count == 0 ? ReturnMsgUtil.success(null) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap deleteSingleByPin(Integer pin) {
        try {
            pinRepository.deleteByPin(pin);
            int count = pinRepository.countByPin(pin);
            return count == 0 ? ReturnMsgUtil.success(null) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap generateOne(String startTime, String endTime, int role, String info, String remark, String userId) {
        try {

            //检查是否存在该类型的pin
            int count = pinRepository.countByInfoAndOwnerIdAndRole(info, userId, role);

            if (count != 0) {
                return ReturnMsgUtil.custom(SystemConst.PIN_EXISTS);
            }

            //生成pin
            Pin pin = new Pin();
            int pinNum;

            do {
                pinNum = (int) (Math.random() * (999999 - 100000 + 1) + 100000);
                count = pinRepository.countByPin(pinNum);
            } while (count != 0);

            pin.setPin(pinNum);
            pin.setStartTime(startTime);
            pin.setEndTime(endTime);
            pin.setCreateTime(DateUtil.DateToStr(new Date()));
            pin.setInfo(info);
            pin.setRemark(remark);
            pin.setRole(role);
            pin.setOwnerId(userId);

            Pin newPin = pinRepository.save(pin);
            return newPin != null ? ReturnMsgUtil.success(newPin) : ReturnMsgUtil.fail();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap getAllInfo() {
        try {
            List<String> infoList = pinRepository.findInfo();
            return ReturnMsgUtil.success(infoList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap updateOne(Integer pinNum, Pin pin) {
        try {
            pin.setPin(pinNum);
            Pin newPin = pinRepository.save(pin);
            return newPin != null ? ReturnMsgUtil.success(newPin) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap listByInfo(String currentPage, String pageSize, String search, String order, String orderColumn,
                                String info, String ownerId) {
        try {
            ResultMap message = new ResultMap();

            long totalSize = pinDao.getCountByMapPageSearchOrdered(search, info, ownerId);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            List<PinView> pinViewList = pinDao.getByMapPageSearchOrdered(search, info, ownerId,
                    page.getCurrentIndex(), page.getPageSize(), order, orderColumn);

            message.setData(pinViewList);
            message.put("page", page);
            message.setMsg(SystemConst.SUCCESS.getMsg());
            message.setCode(SystemConst.SUCCESS.getCode());
            return message;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
