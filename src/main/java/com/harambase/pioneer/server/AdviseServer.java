package com.harambase.pioneer.server;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.base.AdviseBase;
import com.harambase.support.util.BuildUrlUtil;
import com.harambase.support.util.RestTemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AdviseServer {

    public HaramMessage advisingList(String ip, int port, int start, int length, String search, String order, String orderColumn,
                                     String studentId, String facultyId) {
        String remotePath = "/advise";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        requestUrl.append("?start=")
                .append(start)
                .append("&length=")
                .append(length)
                .append("&search=")
                .append(search)
                .append("&order=").append(order).append("orderCol=").append(orderColumn);
        if (StringUtils.isNotEmpty(studentId))
            requestUrl.append("&studentId=").append(studentId);
        if (StringUtils.isNotEmpty(facultyId))
            requestUrl.append("&facultyId=").append(facultyId);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage updateAdvise(String ip, int port, Integer id, String studentId, String facultyId) {
        String remotePath = "/advise/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        requestUrl.append("?studentId=").append(studentId).append("&facultyId=").append(facultyId);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.PUT, params);
    }

    public HaramMessage assignMentor(String ip, int port, AdviseBase advise) {
        String remotePath = "/advise";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.POST, advise);
    }

    public HaramMessage removeMentor(String ip, int port, Integer id) {
        String remotePath = "/advise/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.DELETE, params);
    }

    public HaramMessage getMentor(String ip, int port, Integer id) {
        String remotePath = "/advise/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

}