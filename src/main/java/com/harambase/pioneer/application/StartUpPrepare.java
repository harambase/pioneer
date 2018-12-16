package com.harambase.pioneer.application;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.helper.ConfigUtil;
import com.harambase.pioneer.server.dao.base.PersonDao;
import com.harambase.pioneer.server.dao.connection.DataServiceConnection;
import com.harambase.pioneer.server.dao.connection.ResultSetHelper;
import com.harambase.pioneer.server.pojo.base.Person;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class StartUpPrepare {

    private static final Logger LOGGER = LoggerFactory.getLogger("StartUpPrepare");

    private static Map<String, Object> dataMap = new HashMap<>();
    private static InputStream staffListInputStream, schoolInputStream, articleInputStream;

    @Autowired
    private final PersonDao personDao;

    public StartUpPrepare(PersonDao personDao) {
        this.personDao = personDao;
    }

    static {
        try {
            String path = Config.serverPath;
            LOGGER.info(path);
            ConfigUtil.setConfFolder(path);

            initHome();
            initEAS();

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            System.exit(1);
        }
    }

    public static void initEAS() {
        try {
            List<Person> people = getPersonBySearch("1000");

            for (Person person : people) {
                if (StringUtils.isNotEmpty(person.getProfile())) {
                    JSONObject jsonObject = JSON.parseObject(person.getProfile());
                    String filePath = jsonObject.getString("path");
                    if (StringUtils.isNotEmpty(filePath)) {
                        String fileName = FileUtil.getFileLogicalName(filePath);
                        String localPath = Config.serverPath + "/static/" + FileUtil.getFileDirPath(filePath);

                        File file = new File(localPath + fileName);
                        if (!file.exists())
                            FileUtil.downloadFileFromFTPToLocal(fileName, filePath, localPath, Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static void initHome() {
        try {
            String staffList = "/static/files/personnel.json";
            String schoolList = "/static/files/school.json";
            String articleList = "/static/files/articles.json";

            staffListInputStream = ConfigUtil.getConfFile(staffList);
            schoolInputStream = ConfigUtil.getConfFile(schoolList);
            articleInputStream = ConfigUtil.getConfFile(articleList);

            JSONObject weChatList = //initWeChatList();
                    new JSONObject();

            String staffJson = initData(staffListInputStream);
            JSONArray staffJsonArray = JSON.parseArray(staffJson);

            String schoolJson = initData(schoolInputStream);
            JSONArray schoolJsonArray = JSON.parseArray(schoolJson);

            String articleJsonString = initData(articleInputStream);
            JSON articleJson = JSON.parseObject(articleJsonString);

            LOGGER.info("STAFF: " + staffJson);
            LOGGER.info("SCHOOL: " + schoolJson);
            LOGGER.info("ARTICLE: " + articleJson);

            dataMap.put("staffList", staffJsonArray);
            dataMap.put("schoolList", schoolJsonArray);
            dataMap.put("articleList", articleJson);
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

    private static List<Person> getPersonBySearch(String maxLength) throws Exception {
        ResultSet rs = null;
        Connection connection = null;
        Statement stmt = null;
        List<Person> personList = new ArrayList<>();

        try {
            connection = DataServiceConnection.openDBConnection();
            if (connection == null) {
                return personList;
            }
            stmt = connection.createStatement();

            String queryString = "select * from person where 1=1 limit 0," + Integer.parseInt(maxLength);

            LOGGER.info(queryString);

            rs = stmt.executeQuery(queryString);

            personList = ResultSetHelper.getObjectFor(rs, Person.class);

        } finally {
            if (stmt != null) {
                stmt.close();
            }

            if (rs != null) {
                rs.close();
            }

            if (connection != null) {
                connection.close();
            }

        }

        return personList;
    }
}
