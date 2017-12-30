package com.harambase.pioneer.server;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Course;
import com.harambase.pioneer.pojo.dto.Option;
import com.harambase.support.util.BuildUrlUtil;
import com.harambase.support.util.RestTemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CourseServer {

    public HaramMessage create(String ip, int port, Course course) {
        String remotePath = "/course";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.POST, course);
    }

    public HaramMessage delete(String ip, int port, String crn) {
        String remotePath = "/course/" + crn;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.DELETE, params);
    }

    public HaramMessage update(String ip, int port, String crn, Course course) {
        String remotePath = "/course/" + crn;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.PUT, course);
    }

    public HaramMessage getCourseByCrn(String ip, int port, String crn) {
        String remotePath = "/course/" + crn;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage courseList(String ip, int port, int start, int length, String search,
                                   String order, String orderColumn, String facultyId, String info) {
        String remotePath = "/course";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        requestUrl.append("?start=").append(start)
                .append("&length=").append(length)
                .append("&search=").append(search)
                .append("&order=").append(order)
                .append("&orderCol=").append(orderColumn);
        if (StringUtils.isNotEmpty(facultyId))
            requestUrl.append("&facultyId=").append(facultyId);
        if (StringUtils.isNotEmpty(info))
            requestUrl.append("&info=").append(info);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage getCourseBySearch(String ip, int port, String search, String status) {
        String remotePath = "/course?search=";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        if (StringUtils.isNotEmpty(search))
            requestUrl.append(search);
        if (StringUtils.isNotEmpty(status))
            requestUrl.append("&status=").append(status);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage assignFac2Cou(String ip, int port, String crn, String facultyId) {
        String remotePath = "/course/" + crn + "/faculty/" + facultyId;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage preCourseList(String ip, int port, String crn) {
        String remotePath = "/course/" + crn;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage countActiveCourse(String ip, int port) {
        String remotePath = "/course";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage courseTreeList(String ip, int port, String facultyId, String info) {
        String remotePath = "/course?facultyId=";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        if (StringUtils.isNotEmpty(facultyId))
            requestUrl.append(facultyId);
        if (StringUtils.isNotEmpty(info))
            requestUrl.append("&info=").append(info);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }

    public HaramMessage addStu2Cou(String ip, int port, String crn, String studentId, Option option) {
        String remotePath = "/course" + crn + "/student" + studentId;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.PUT, option);
    }

    public HaramMessage removeStuFromCou(String ip, int port, String crn, String studentId) {
        String remotePath = "/course" + crn + "/student" + studentId;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.DELETE, params);
    }

    public HaramMessage reg2Course(String ip, int port, String studentId, String[] choices) {
        String remotePath = "/course/" + studentId + "/choose";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, choices);
    }
}