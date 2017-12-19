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


    public HaramMessage addStu2Cou(String ip, int port, String crn, String studentId, Option option) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage removeStuFromCou(String ip, int port, String crn, String studentId) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage reg2Course(String ip, int port, String studentId, String[] choices) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }
}