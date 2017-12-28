package com.harambase.pioneer.service;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.base.TranscriptBase;

public interface TranscriptService {

    HaramMessage updateGrade(int id, TranscriptBase transcript);

    HaramMessage transcriptList(int start, int length, String search, String order, String orderCol, String studentId, String crn);

}
