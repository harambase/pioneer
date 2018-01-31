package com.harambase.pioneer.server;

import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.pojo.Student;
import com.harambase.pioneer.common.support.util.BuildUrlUtil;
import com.harambase.pioneer.common.support.util.RestTemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class StudentServer {

    public HaramMessage transcriptDetail(String ip, int port, String studentId) {
        String remotePath = "/student/" + studentId + "/transcript";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage updateByPrimaryKey(String ip, int port, String studentId, Student student) {
        String remotePath = "/student/" + studentId;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.PUT, student);
    }

    public HaramMessage studentList(String ip, int port, int start, int length, String search,
                                    String order, String orderColumn, String type, String status) {
        String remotePath = "/student";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        requestUrl.append("?start=").append(start)
                .append("&length=").append(length)
                .append("&search=").append(search)
                .append("&order=").append(order)
                .append("&orderCol=").append(orderColumn);
        if (StringUtils.isNotEmpty(type))
            requestUrl.append("&type=").append(type);
        if (StringUtils.isNotEmpty(status))
            requestUrl.append("&status=").append(status);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage getAvailableCredit(String ip, int port, String studentId, String info) {
        String remotePath = "/student/" + studentId + "/available/credit?info=" + info;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage courseList(String ip, int port, String status, String studentId) {
        String remotePath = "/student/" + studentId + "/course?status=" + status;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }
}