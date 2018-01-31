package com.harambase.pioneer.service;

import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.server.pojo.base.Transcript;

public interface TranscriptService {

    HaramMessage updateGrade(int id, Transcript transcript);

    HaramMessage transcriptList(int start, int length, String search, String order, String orderCol, String studentId, String crn, String info, String complete);

    HaramMessage studentTranscriptReport(String studentId);
}
