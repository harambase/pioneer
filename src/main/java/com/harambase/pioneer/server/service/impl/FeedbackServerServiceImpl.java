package com.harambase.pioneer.server.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.DateUtil;
import com.harambase.pioneer.common.support.util.EncryptionUtil;
import com.harambase.pioneer.common.support.util.PageUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.dao.base.FeedbackDao;
import com.harambase.pioneer.server.dao.base.PersonDao;
import com.harambase.pioneer.server.dao.repository.FeedbackRepository;
import com.harambase.pioneer.server.pojo.base.Feedback;
import com.harambase.pioneer.server.pojo.base.Person;
import com.harambase.pioneer.server.pojo.view.FeedbackView;
import com.harambase.pioneer.server.service.FeedbackServerService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FeedbackServerServiceImpl implements FeedbackServerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FeedbackRepository feedbackRepository;

    private final FeedbackDao feedbackDao;
    private final PersonDao personDao;


    @Autowired
    public FeedbackServerServiceImpl(FeedbackRepository feedbackRepository, FeedbackDao feedbackDao,
                                     PersonDao personDao) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackDao = feedbackDao;
        this.personDao = personDao;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public ResultMap delete(Integer id) {
        try {
            feedbackRepository.deleteById(id);
            return !feedbackRepository.existsById(id) ? ReturnMsgUtil.success(null) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap update(Integer id, Feedback feedback) {
        try {
            feedback.setId(id);
            feedback.setUpdateTime(DateUtil.DateToStr(new Date()));
            Feedback newFeedback = feedbackRepository.save(feedback);
            return ReturnMsgUtil.success(newFeedback);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap retrieve(Integer id) {
        try {
            Feedback feedback = feedbackRepository.getOne(id);
            return ReturnMsgUtil.success(feedback);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap list(String currentPage, String pageSize, String search, String order, String orderColumn, String facultyId, String info) {
        try {
            ResultMap message = new ResultMap();

            long totalSize = feedbackDao.getFeedbackCountByMapPageSearchOrdered(facultyId, info, search);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            List<FeedbackView> feedbackViewList = feedbackDao.getFeedbackByMapPageSearchOrdered(facultyId, info, search,
                    page.getCurrentIndex(), page.getPageSize(), order, orderColumn);

            message.setData(feedbackViewList);
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
    public ResultMap generateAll(String info, String password, String opId) {

        try {

            int count = feedbackRepository.countByInfo(info);
            if (count != 0) {
                return ReturnMsgUtil.custom(SystemConst.FEEDBACK_DUPLICATE);
            }

            List<Person> facultyList = personDao.getPersonBySearch("", "f", "1", "0", String.valueOf(Integer.MAX_VALUE));

            for (Person f : facultyList) {
                Feedback feedback = new Feedback();

                feedback.setPassword(password);
                feedback.setFacultyId(f.getUserId());
                feedback.setOperatorId(opId);
                feedback.setUpdateTime(DateUtil.DateToStr(new Date()));
                feedback.setInfo(info);

                Feedback newFeedback = feedbackRepository.save(feedback);
                if (newFeedback == null)
                    throw new RuntimeException("评价生成失败!");
            }
            return ReturnMsgUtil.success(null);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }

    }

    @Override
    public ResultMap generateOne(String info, String userId, String password, String opId) {
        try {

            //检查是否存在该类型的feedback
            int count = feedbackRepository.countByFacultyIdAndInfo(userId, info);

            if (count != 0) {
                return ReturnMsgUtil.custom(SystemConst.FEEDBACK_DUPLICATE);
            }

            //生成feedback
            Feedback feedback = new Feedback();

            feedback.setFacultyId(userId);
            feedback.setPassword(password);
            feedback.setOperatorId(opId);
            feedback.setUpdateTime(DateUtil.DateToStr(new Date()));
            feedback.setInfo(info);

            Feedback newFeedback = feedbackRepository.save(feedback);

            return newFeedback != null ? ReturnMsgUtil.success(newFeedback) : ReturnMsgUtil.fail();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap find(String facultyId) {
        try {
            Feedback feedback = feedbackRepository.getByFacultyId(facultyId);
            return ReturnMsgUtil.success(feedback);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap updateFeedbackOther(Integer id, Feedback feedback) {
        try {
            Optional<Feedback> f = feedbackRepository.findById(id);
            f.ifPresent(fb -> {
                try {
                    String key = fb.getPassword();
                    String rate = fb.getRate();

                    EncryptionUtil ed = new EncryptionUtil(key);

                    //把原始的先解密
                    String deRate = ed.decrypt(rate);

                    //添加进入解密后的评价中
                    JSONArray rateJA = JSONArray.parseArray(deRate);
                    rateJA.add(feedback.getRate());

                    //重新加密
                    String enRate = ed.encrypt(JSONArray.toJSONString(rateJA));
                    fb.setRate(enRate);
                    fb.setOperatorId(feedback.getOperatorId());
                    fb.setUpdateTime(DateUtil.DateToStr(new Date()));

                    feedbackRepository.save(fb);

                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException();
                }
            });
            return ReturnMsgUtil.success(f);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap encrypt(String info, String password, String oldPassword, String opId) {
        try {

            //通过info获取所有的当年评价
            List<Feedback> feedbackList = feedbackRepository.findAllByInfo(info);

            //列表读取，解密（如果之前有加密码的话）并加密
            for (Feedback f : feedbackList) {
                String deRate = f.getRate();

                if (StringUtils.isNotEmpty(f.getPassword())) {
                    //matches(String raw, String encrypted)
                    if (passwordEncoder().matches(oldPassword, f.getPassword())) {
                        //把原始的先解密
                        EncryptionUtil edOld = new EncryptionUtil(f.getPassword());
                        deRate = edOld.decrypt(f.getRate());
                    } else {
                        return ReturnMsgUtil.custom(SystemConst.WRONG_OLD_PASSWORD);
                    }
                }

                //加密
                EncryptionUtil edNew = new EncryptionUtil(password);
                String enRate = edNew.encrypt(deRate);

                //设置
                f.setRate(enRate);
                f.setPassword(password);
                f.setUpdateTime(DateUtil.DateToStr(new Date()));
                f.setOperatorId(opId);
            }

            feedbackRepository.saveAll(feedbackList);

            return ReturnMsgUtil.success(null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap validate(String info, String password) {
        try {

            //通过info获取所有的当年评价
            List<Feedback> feedbackList = feedbackRepository.findAllByInfo(info);

            Feedback f = feedbackList.get(0);

            //列表读取，解密
            if (StringUtils.isNotEmpty(f.getPassword())) {
                //matches(String raw, String encrypted)
                if (passwordEncoder().matches(password, f.getPassword())) {
                    return ReturnMsgUtil.success(null);
                } else {
                    return ReturnMsgUtil.custom(SystemConst.WRONG_OLD_PASSWORD);
                }
            }

            return ReturnMsgUtil.success(null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap decryptList(String currentPage, String pageSize, String search, String order, String orderColumn, String facultyId, String password, String info) {
        try {
            ResultMap message = new ResultMap();

            long totalSize = feedbackDao.getFeedbackCountByMapPageSearchOrdered(facultyId, info, search);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            List<FeedbackView> feedbackViewList = feedbackDao.getFeedbackByMapPageSearchOrdered(facultyId, info, search,
                    page.getCurrentIndex(), page.getPageSize(), order, orderColumn);

            if (feedbackViewList.size() > 0) {
                Optional<Feedback> feedback = feedbackRepository.findById(feedbackViewList.get(0).getId());
                feedback.ifPresent(f -> {
                    try {
                        if (StringUtils.isNotEmpty(f.getPassword())) {
                            //matches(String raw, String encrypted)
                            if (passwordEncoder().matches(password, f.getPassword())) {
                                for (FeedbackView fv : feedbackViewList) {
                                    //把原始的解密
                                    EncryptionUtil ed = new EncryptionUtil(f.getPassword());
                                    String enRate = fv.getRate();
                                    String deRate = ed.decrypt(enRate);

                                    //设置
                                    fv.setRate(deRate);
                                }
                            } else {
                                throw new RuntimeException("密码错误");
                            }
                        }

                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        throw new RuntimeException();
                    }
                });
            }

            message.setData(feedbackViewList);
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
