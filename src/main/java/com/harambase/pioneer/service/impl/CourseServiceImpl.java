package com.harambase.pioneer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.pojo.Course;
import com.harambase.pioneer.pojo.dto.Option;
import com.harambase.pioneer.server.CourseServer;
import com.harambase.pioneer.service.CourseService;
import com.harambase.support.util.FileUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class CourseServiceImpl implements CourseService {

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CourseServer courseServer;

    @Autowired
    public CourseServiceImpl(CourseServer courseServer) {
        this.courseServer = courseServer;
    }

    @Override
    public HaramMessage create(Course course) {
        return courseServer.create(IP, PORT, course);
    }

    @Override
    public HaramMessage delete(String crn) {
        return courseServer.delete(IP, PORT, crn);
    }

    @Override
    public HaramMessage update(String crn, Course course) {
        return courseServer.update(IP, PORT, crn, course);
    }

    @Override
    public HaramMessage getCourseByCrn(String crn) {
        return courseServer.getCourseByCrn(IP, PORT, crn);
    }

    @Override
    public HaramMessage courseList(int start, int length, String search, String order, String orderColumn, String facultyid, String info) {
        return courseServer.courseList(IP, PORT, start, length, search, order, orderColumn, facultyid, info);
    }

    @Override
    public HaramMessage courseTreeList(String facultyid, String info) {
        return courseServer.courseTreeList(IP, PORT, facultyid, info);
    }

    @Override
    public HaramMessage uploadInfo(String crn, MultipartFile file) {
        HaramMessage message = new HaramMessage();
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = new JSONObject();

        try {
            LinkedHashMap courseMap = (LinkedHashMap) courseServer.getCourseByCrn(IP, PORT, crn).getData();
            Course course = new Course();
            BeanUtils.populate(course, courseMap);

            //处理老的文件
            String oldInfo = course.getCourseInfo();

            if (StringUtils.isNotEmpty(oldInfo)) {
                File oldfile = new File(Config.serverPath + oldInfo);
                oldfile.delete();
            }

            String fileUri = FileUtil.uploadFileToPath(file, "/static/upload/document/courseInfo");
            String name = file.getOriginalFilename();

            jsonObject.put("name", name);
            jsonObject.put("size", file.getSize());
            jsonObject.put("type", name.substring(name.lastIndexOf(".") + 1));
            jsonObject.put("path", fileUri);

            course.setCourseInfo(jsonObject.toJSONString());

            message = courseServer.update(IP, PORT, crn, course);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message.setMsg("上传失败");
            message.setCode(FlagDict.FAIL.getV());
            return message;
        }

        message.setMsg("上传成功");
        message.setData(map);
        return message;
    }

    @Override
    public HaramMessage courseInfoList(String search) {
        return courseServer.courseInfoList(IP, PORT, search);
    }

    @Override
    public HaramMessage getCourseBySearch(String search, String status) {
        return courseServer.getCourseBySearch(IP, PORT, search, status);
    }

    @Override
    public HaramMessage assignFac2Cou(String crn, String facultyId) {
        return courseServer.assignFac2Cou(IP, PORT, crn, facultyId);
    }

    @Override
    public HaramMessage preCourseList(String crn) {
        return courseServer.preCourseList(IP, PORT, crn);
    }

    @Override
    public HaramMessage countActiveCourse() {
        return courseServer.countActiveCourse(IP, PORT);
    }

    @Override
    public HaramMessage addStu2Cou(String crn, String studentId, Option option) {
        return courseServer.addStu2Cou(IP, PORT, crn, studentId, option);
    }

    @Override
    public HaramMessage removeStuFromCou(String crn, String studentId) {
        return courseServer.removeStuFromCou(IP, PORT, crn, studentId);
    }

    @Override
    public HaramMessage reg2Course(String studentId, String[] choices) {
        return courseServer.reg2Course(IP, PORT, studentId, choices);
    }
}
