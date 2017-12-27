package com.harambase.pioneer.server;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.base.AdviseBase;
import com.harambase.support.util.RestTemplateUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AdviseServer {

    private StringBuilder requestUrl = new StringBuilder();
    Map params = new HashMap();
    public HaramMessage advisingList(String ip, int port, int currentIndex, int pageSize, String search, String order, String orderColumn, String studentid, String facultyid) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage updateAdvise(String ip, int port, String studentId, String facultyId) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage assignMentor(String ip, int port, AdviseBase advise) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage removeMentor(String ip, int port, Integer id) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage getMentor(String ip, int port, Integer id) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

}