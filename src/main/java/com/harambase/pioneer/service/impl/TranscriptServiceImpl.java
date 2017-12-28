package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.base.TranscriptBase;
import com.harambase.pioneer.server.TranscriptServer;
import com.harambase.pioneer.service.TranscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public HaramMessage updateGrade(int id, TranscriptBase transcript) {
        return transcriptServer.updateByPrimaryKey(IP, PORT, id, transcript);
    }

    @Override
    public HaramMessage transcriptList(int start, int length, String search, String order, String orderColumn, String studentId, String crn) {
        return transcriptServer.transcriptList(IP, PORT, start, length, search, order, orderColumn, studentId, crn);
    }
}
