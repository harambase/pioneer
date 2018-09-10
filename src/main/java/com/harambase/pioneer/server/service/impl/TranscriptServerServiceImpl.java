package com.harambase.pioneer.server.service.impl;

import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.DateUtil;
import com.harambase.pioneer.common.support.util.PageUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.dao.base.TranscriptDao;
import com.harambase.pioneer.server.dao.repository.TranscriptRepository;
import com.harambase.pioneer.server.pojo.base.Transcript;
import com.harambase.pioneer.server.pojo.view.TranscriptView;
import com.harambase.pioneer.server.service.TranscriptServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TranscriptServerServiceImpl implements TranscriptServerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TranscriptRepository transcriptRepository;
    private final TranscriptDao transcriptDao;

    @Autowired
    public TranscriptServerServiceImpl(TranscriptRepository transcriptRepository, TranscriptDao transcriptDao) {
        this.transcriptRepository = transcriptRepository;
        this.transcriptDao = transcriptDao;
    }

    @Override
    public ResultMap updateGrade(Integer id, Transcript transcript) {
        try {
            transcript.setId(id);
            transcript.setAssignTime(DateUtil.DateToStr(new Date()));
            Transcript newTranscript = transcriptRepository.save(transcript);
            return newTranscript != null ? ReturnMsgUtil.success(newTranscript) : ReturnMsgUtil.fail();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap transcriptList(String currentPage, String pageSize, String search, String order,
                                    String orderColumn, String studentId, String crn, String info, String complete) {

        ResultMap message = new ResultMap();
        try {

            long totalSize = transcriptDao.getCountByMapPageSearchOrdered(search, studentId, crn, info, complete);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            List<TranscriptView> transcriptViews = transcriptDao.getByMapPageSearchOrdered(page.getCurrentIndex(),
                    page.getPageSize(), search, order, orderColumn, studentId, crn, info, complete);

            message.setData(transcriptViews);
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
    public ResultMap getDistinctColumnByInfo(String column, String info) {
        try {
            List<String> columnValues = transcriptDao.getDistinctColumnByInfo(column, info);
            return ReturnMsgUtil.success(columnValues);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }
}
