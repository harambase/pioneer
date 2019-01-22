package com.harambase.pioneer.server.service.impl;

import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.DateUtil;
import com.harambase.pioneer.common.support.util.PageUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.dao.base.FeedbackDao;
import com.harambase.pioneer.server.dao.base.PersonDao;
import com.harambase.pioneer.server.dao.repository.FeedbackRepository;
import com.harambase.pioneer.server.pojo.base.Feedback;
import com.harambase.pioneer.server.pojo.base.Person;
import com.harambase.pioneer.server.pojo.view.FeedbackView;
import com.harambase.pioneer.server.service.FeedbackServerService;
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
    public ResultMap generateAll(String info, String opId) {

        try {

            int count = feedbackRepository.countByInfo(info);
            if (count != 0) {
                return ReturnMsgUtil.custom(SystemConst.FEEDBACK_DUPLICATE);
            }

            List<Person> facultyList = personDao.getPersonBySearch("", "f", "1", "0", String.valueOf(Integer.MAX_VALUE));

            for (Person f : facultyList) {
                Feedback feedback = new Feedback();

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
    public ResultMap generateOne(String info, String userId, String opId) {
        try {

            //检查是否存在该类型的feedback
            int count = feedbackRepository.countByFacultyIdAndInfo(userId, info);

            if (count != 0) {
                return ReturnMsgUtil.custom(SystemConst.FEEDBACK_DUPLICATE);
            }

            //生成feedback
            Feedback feedback = new Feedback();

            feedback.setFacultyId(userId);
            feedback.setOperatorId(opId);
            feedback.setUpdateTime(DateUtil.DateToStr(new Date()));
            feedback.setInfo(info);

            Feedback newFeedback = feedbackRepository.save(feedback);

            return newFeedback != null ? ReturnMsgUtil.success(newFeedback): ReturnMsgUtil.fail();

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
}
