package com.harambase.pioneer.server;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.pojo.Transcript;
import com.harambase.pioneer.pojo.base.CourseBase;
import com.harambase.pioneer.pojo.base.TranscriptBase;
import com.harambase.pioneer.pojo.dto.Option;
import com.harambase.support.util.RestTemplateUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TranscriptServer {

    private StringBuilder requestUrl = new StringBuilder();
    Map params = new HashMap();

    public HaramMessage updateByPrimaryKey(String ip, int port, TranscriptBase transcript) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage transcriptList(String ip, int port, int currentIndex, int pageSize, String search, String order, String orderColumn, String studentId, String crn) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }
}