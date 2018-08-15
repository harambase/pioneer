package com.harambase.pioneer.server;

import com.harambase.pioneer.common.ResultMap;
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

    public ResultMap update(Integer id, Transcript transcript) {
        return transcriptService.updateGrade(id, transcript);
    }

    public ResultMap list(Integer start, Integer length, String search, String order, String orderCol,
                             String studentId, String crn, String info, String complete) {

        return transcriptService.transcriptList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, studentId, crn, info, complete);
    }

    public ResultMap getDistinctColumnByInfo(String column, String info){
        return transcriptService.getDistinctColumnByInfo(column, info);
    }
}
