package com.harambase.pioneer.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Config;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class DataSourceConf {

    private static final Logger LOGGER = LoggerFactory.getLogger("DataSourceConf");

    private static Map<String, Object> dataMap = new HashMap<>();
    private static InputStream staffListInputStream, schoolInputStream;

    static {
        try {

            String path = Config.serverPath;
            LOGGER.info(path);
            ConfigUtil.setConfFolder(path);

            init();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            System.exit(1);
        }
    }

    public static void init() {

        try {
            String staffList = "/static/files/personnel.json";
            String schoolList = "/static/files/school.json";

            staffListInputStream = ConfigUtil.getConfFile(staffList);
            schoolInputStream = ConfigUtil.getConfFile(schoolList);

            JSONObject weChatList = initWeChatList();
                    //new JSONObject();

            String staffJson = initData(staffListInputStream);
            JSONArray staffJsonArray = JSON.parseArray(staffJson);

            String schoolJson = initData(schoolInputStream);
            JSONArray schoolJsonArray = JSON.parseArray(schoolJson);

            LOGGER.info("STAFF: " + staffJson);
            LOGGER.info("SCHOOL: " + schoolJson);

            dataMap.put("staffList", staffJsonArray);
            dataMap.put("schoolList", schoolJsonArray);
            dataMap.put("weChatList", weChatList);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private static JSONObject initWeChatList() throws Exception {
        String cmd = "python " + (Config.serverPath + "/static/files/weChat.py").substring(1);
        Process proc = Runtime.getRuntime().exec(cmd);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        String s;
        JSONObject weChatList = new JSONObject();
        while ((s = stdInput.readLine()) != null) {

            Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                char ch = (char) Integer.parseInt(matcher.group(2), 16);
                s = s.replace(matcher.group(1), ch + "");
            }

            weChatList = JSONObject.parseObject(s);
            LOGGER.info("WECHAT OUTPUT:" + s);
        }

        while ((s = stdError.readLine()) != null) {
            LOGGER.error("WECHAT ERROR：" + s);
        }

        stdError.close();
        stdInput.close();

        return weChatList;
    }

    private static String initData(InputStream inputStream) {
        StringBuilder json = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return json.toString();
    }

    public static Map<String, Object> getDataMap() {
        return dataMap;
    }

    public static Object getDatasource(String name) {
        return dataMap.get(name);
    }

    @Deprecated
    private static JSONObject updateWeChatList() throws Exception {
        //搜索获取最近的URI
        CloseableHttpClient httpClient = HttpClients.createDefault(); // 创建httpClient实例
        HttpGet httpGet = new HttpGet("http://weixin.sogou.com/weixin?type=1&s_from=input&query=pioneer%E5%85%88%E9%94%8B%E6%95%99%E8%82%B2&ie=utf8&_sug_=n&_sug_type_=");
//        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"); // 设置请求头消息User-Agent
        CloseableHttpResponse response = httpClient.execute(httpGet); // 执行http get请求
        HttpEntity entity = response.getEntity(); // 获取返回实体

        String html = EntityUtils.toString(entity, "utf-8");
        int start = html.indexOf("http://mp.weixin.qq.com/profile?");
        String newHTML = html.substring(start, html.length());
        int end = newHTML.indexOf(">");
//        String srcURI = "http://mp.weixin.qq.com/profile?src=3&amp;timestamp=1531234196&amp;ver=1&amp;signature=8IcAyAw4zbQdvFS86AO7OM8yLHQa-A*tcevN2DFJwr7dKHjijeRUSV22FhKClFqDAa-3MpqAcU0y6LxhgapxJg==";
        String srcURI = newHTML.substring(0, end - 1).replaceAll("amp;", "");
        srcURI = srcURI.replaceAll("amp;", "");
//
//        //通过URL获取列表
        httpGet = new HttpGet(srcURI);
        response = httpClient.execute(httpGet); // 执行http get请求
        entity = response.getEntity(); // 获取返回实体

        html = EntityUtils.toString(entity, "utf-8");
        start = html.indexOf("msgList") + 10;
        end = html.lastIndexOf("}}]};") + 4;
        String list = html.substring(start, end);

        JSONObject weChatList = JSONObject.parseObject(list);

        response.close(); // response关闭
        httpClient.close(); // httpClient关闭

        return weChatList;
    }
}
