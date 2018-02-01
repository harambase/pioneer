package com.harambase.pioneer.server;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.pojo.TempAdvise;
import com.harambase.pioneer.pojo.TempCourse;
import com.harambase.pioneer.pojo.TempUser;
import com.harambase.pioneer.common.support.util.BuildUrlUtil;
import com.harambase.pioneer.common.support.util.RestTemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Component
public class RequestServer {

    public HaramMessage deleteTempUser(String ip, int port, Integer id) {
        String remotePath = "/request/user/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.DELETE, params);
    }

    public HaramMessage registerNewUser(String ip, int port, JSONObject jsonObject) {
        String remotePath = "/request/user/register";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.POST, jsonObject);
    }

    public HaramMessage updateTempUser(String ip, int port, int id, TempUser tempUser) {
        String remotePath = "/request/user/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.PUT, tempUser);
    }

    public HaramMessage tempUserList(String ip, int port, int start, int length, String search,
                                     String order, String orderColumn, String status) {
        String remotePath = "/request/user";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        requestUrl.append("?start=")
                .append(start)
                .append("&length=")
                .append(length)
                .append("&search=")
                .append(search)
                .append("&order=").append(order).append("&orderCol=").append(orderColumn);
        if (StringUtils.isNotEmpty(status))
            requestUrl.append("&status=").append(status);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage updateTempCourse(String ip, int port, int id, TempCourse tempCourse) {
        String remotePath = "/request/course/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.PUT, tempCourse);
    }

    public HaramMessage registerNewCourse(String ip, int port, String facultyId, JSONObject jsonObject) {
        String remotePath = "/request/course/register/" + facultyId;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.POST, jsonObject);
    }

    public HaramMessage deleteTempCourse(String ip, int port, Integer id) {
        String remotePath = "/request/course/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.DELETE, params);
    }

    public HaramMessage tempCourseList(String ip, int port, Integer start, Integer length, String search, String order, String orderCol, String status, String facultyId) {
        String remotePath = "/request/course";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        requestUrl.append("?start=")
                .append(start)
                .append("&length=")
                .append(length)
                .append("&search=")
                .append(search)
                .append("&order=").append(order).append("&orderCol=").append(orderCol);
        if (StringUtils.isNotEmpty(status))
            requestUrl.append("&status=").append(status);
        if (StringUtils.isNotEmpty(facultyId))
            requestUrl.append("&facultyId=").append(facultyId);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage getTempUser(String ip, int port, Integer id) {
        String remotePath = "/request/user/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage getTempCourse(String ip, int port, Integer id) {
        String remotePath = "/request/course/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage tempAdviseList(String ip, int port, Integer start, Integer length, String search, String order, String orderCol) {
        String remotePath = "/request/advise";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        requestUrl.append("?start=")
                .append(start)
                .append("&length=")
                .append(length)
                .append("&search=")
                .append(search)
                .append("&order=").append(order).append("&orderCol=").append(orderCol);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage updateTempAdvise(String ip, int port, Integer id, TempAdvise tempAdvise) {
        String remotePath = "/request/advise/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.PUT, tempAdvise);
    }

    public HaramMessage getTempAdvise(String ip, int port, Integer id) {
        String remotePath = "/request/advise/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage deleteTempAdviseById(String ip, int port, Integer id) {
        String remotePath = "/request/advise/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.DELETE, params);
    }

    public HaramMessage registerTempAdvise(String ip, int port, String studentId, JSONObject jsonObject) {
        String remotePath = "/request/advise/" + studentId;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.POST, jsonObject);
    }
}