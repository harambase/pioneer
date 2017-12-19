package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.common.Page;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.pojo.base.TranscriptBase;
import com.harambase.pioneer.server.CourseServer;
import com.harambase.pioneer.server.TranscriptServer;
import com.harambase.pioneer.service.TranscriptService;
import com.harambase.support.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranscriptServiceImpl implements TranscriptService {

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final TranscriptServer transcriptServer;

    @Autowired
    public TranscriptServiceImpl(TranscriptServer transcriptServer) {
        this.transcriptServer = transcriptServer;
    }

    @Override
    public HaramMessage updateGrade(TranscriptBase transcript) {
        return transcriptServer.updateByPrimaryKey(IP, PORT, transcript);
    }

    @Override
    public HaramMessage transcriptList(String currentPage, String pageSize, String search, String order, String orderColumn, String studentId, String crn) {
        Page page = new Page();
        page.setCurrentPage(PageUtil.getcPg(currentPage));
        page.setPageSize(PageUtil.getLimit(pageSize));
        return transcriptServer.transcriptList(IP, PORT, page.getCurrentIndex(), page.getPageSize(), search, order, orderColumn, studentId, crn);
    }
}
