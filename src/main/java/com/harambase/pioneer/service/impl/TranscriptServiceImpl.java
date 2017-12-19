package com.harambase.pioneer.service.impl;

import com.harambase.common.HaramMessage;
import com.harambase.common.Page;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.pojo.base.TranscriptBase;
import com.harambase.support.util.DateUtil;
import com.harambase.support.util.PageUtil;
import com.harambase.pioneer.server.CourseServer;
import com.harambase.pioneer.server.TranscriptServer;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.service.TranscriptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TranscriptServiceImpl implements TranscriptService{

    private final CourseServer courseServer;
    private final TranscriptServer transcriptServer;

    @Autowired
    public TranscriptServiceImpl(CourseServer courseServer,
                                 TranscriptServer transcriptServer) {
        this.courseServer = courseServer;
        this.transcriptServer = transcriptServer;
    }

    @Override
    public HaramMessage updateGrade(TranscriptBase transcript) {
        HaramMessage message = new HaramMessage();
        try {
            transcript.setAssigntime(DateUtil.DateToStr(new Date()));
            int ret = transcriptServer.updateByPrimaryKey(transcript);
            if (ret == 1) {
                message.setData(transcript);
                message.setMsg(FlagDict.SUCCESS.getM());
                message.setCode(FlagDict.SUCCESS.getV());
            } else {
                message.setMsg(FlagDict.FAIL.getM());
                message.setCode(FlagDict.FAIL.getV());
            }
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            message.setMsg(FlagDict.SYSTEM_ERROR.getM());
            message.setCode(FlagDict.SYSTEM_ERROR.getV());
            return message;
        }
    }


    @Override
    public HaramMessage transcriptList(String currentPage, String pageSize, String search, String order, String orderColumn, String studentid, String crn) {
        HaramMessage message = new HaramMessage();
        switch (Integer.parseInt(orderColumn)) {
            case 1:
                orderColumn = "studentid";
                break;
            case 2:
                orderColumn = "slast";
                break;
            case 3:
                orderColumn = "sfirst";
                break;
            case 4:
                orderColumn = "crn";
                break;
            case 5:
                orderColumn = "grade";
                break;
            case 6:
                orderColumn = "complete";
                break;
            case 7:
                orderColumn = "facultyid";
                break;
            case 8:
                orderColumn = "assigntime";
                break;
            default:
                orderColumn = "id";
                break;
        }
        long totalSize = 0;
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("search", search);
            param.put("crn", crn);
            param.put("studentid", studentid);


            if (StringUtils.isEmpty(search))
                param.put("search", null);
            if (StringUtils.isEmpty(studentid))
                param.put("studentid", null);
            if (StringUtils.isEmpty(crn))
                param.put("crn", null);

            totalSize = transcriptServer.getTranscriptCountByMapPageSearchOrdered(param); //startTime, endTime);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            param.put("currentIndex", page.getCurrentIndex());
            param.put("pageSize", page.getPageSize());
            param.put("order", order);
            param.put("orderColumn", orderColumn);

            //(int currentIndex, int pageSize, String search, String order, String orderColumn);
            List<Person> msgs = transcriptServer.getTranscriptByMapPageSearchOrdered(param);

            message.setData(msgs);
            message.put("page", page);
            message.setMsg(FlagDict.SUCCESS.getM());
            message.setCode(FlagDict.SUCCESS.getV());
            return message;

        } catch (Exception e) {
            e.printStackTrace();
            message.setMsg(FlagDict.SYSTEM_ERROR.getM());
            message.setCode(FlagDict.SYSTEM_ERROR.getV());
            return message;
        }
    }
}
