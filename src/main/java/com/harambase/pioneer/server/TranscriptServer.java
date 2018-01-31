package com.harambase.pioneer.server;

import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.server.pojo.base.Transcript;
import com.harambase.pioneer.server.service.TranscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TranscriptServer {

    private final TranscriptService transcriptService;

    @Autowired
    public TranscriptServer(TranscriptService transcriptService) {
        this.transcriptService = transcriptService;
    }

    public HaramMessage update(Integer id, Transcript transcript) {
        return transcriptService.updateGrade(id, transcript);
    }

    public HaramMessage list(Integer start, Integer length, String search, String order, String orderCol,
                             String crn, String studentId, String info, String complete) {

        return transcriptService.transcriptList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, studentId, crn, info, complete);
    }

}
