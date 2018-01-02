package com.harambase.pioneer.server;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Transcript;
import com.harambase.support.util.BuildUrlUtil;
import com.harambase.support.util.RestTemplateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TranscriptServer {


    public HaramMessage updateByPrimaryKey(String ip, int port, int id, Transcript transcript) {
        String remotePath = "/transcript/" + id;
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.PUT, transcript);
    }

    public HaramMessage transcriptList(String ip, int port, int start, int length,
                                       String search, String order, String orderColumn, String studentId, String crn) {
        String remotePath = "/transcript";
        StringBuilder requestUrl = BuildUrlUtil.buildUrl(remotePath, ip, port);
        requestUrl.append("?start=").append(start)
                .append("&length=").append(length)
                .append("&search=").append(search)
                .append("&order=").append(order)
                .append("&orderCol=").append(orderColumn);
        if (StringUtils.isNotEmpty(studentId))
            requestUrl.append("&studentId=").append(studentId);
        if (StringUtils.isNotEmpty(crn))
            requestUrl.append("&crn=").append(crn);
        Map params = new HashMap();
        return RestTemplateUtil.sendRestRequest(requestUrl.toString(), HttpMethod.GET, params);
    }
}