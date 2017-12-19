package com.harambase.pioneer.server;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.pojo.Student;
import com.harambase.pioneer.pojo.base.StudentBase;
import com.harambase.support.util.RestTemplateUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class StudentServer {
    private StringBuilder requestUrl = new StringBuilder();
    Map params = new HashMap();

    public HaramMessage transcriptDetail(String ip, int port, String studentid) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage updateByPrimaryKey(String ip, int port, Student student) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage studentList(String ip, int port, int currentIndex, int pageSize, String search, String order, String orderColumn, String type, String status) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage getAvailableCredit(String ip, int port, String studentid, String info) {
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }
}