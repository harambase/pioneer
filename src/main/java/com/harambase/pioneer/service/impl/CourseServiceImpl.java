package com.harambase.pioneer.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.pojo.Course;
import com.harambase.pioneer.pojo.dto.Option;
import com.harambase.pioneer.server.CourseServer;
import com.harambase.pioneer.service.CourseService;
import com.harambase.support.util.FileUtil;
import com.harambase.support.util.ReturnMsgUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class CourseServiceImpl implements CourseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final CourseServer courseServer;

    @Autowired
    public CourseServiceImpl(CourseServer courseServer) {
        this.courseServer = courseServer;
    }

    @Override
    public HaramMessage create(Course course) {
        try {
            return courseServer.create(IP, PORT, course);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage delete(String crn) {
        try {
            return courseServer.delete(IP, PORT, crn);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage update(String crn, Course course) {
        try {
            return courseServer.update(IP, PORT, crn, course);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage getCourseByCrn(String crn) {
        try {
            return courseServer.getCourseByCrn(IP, PORT, crn);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage courseList(int start, int length, String search, String order, String orderColumn, String facultyid, String info) {
        try {
            return courseServer.courseList(IP, PORT, start, length, search, order, orderColumn, facultyid, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage courseTreeList(String facultyid, String info) {
        try {
            return courseServer.courseTreeList(IP, PORT, facultyid, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
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
            if (StringUtils.isNotEmpty(course.getCourseInfo())) {
                String oldInfoPath = (JSON.parseObject(course.getCourseInfo())).getString("path");
                File oldFile = new File(Config.TEMP_FILE_PATH + oldInfoPath);
                oldFile.delete();
            }

            String fileUri = FileUtil.uploadFileToPath(file, "/document/courseInfo");
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
            message.setCode(FlagDict.SYSTEM_ERROR.getV());
            return message;
        }

        message.setMsg("上传成功");
        message.setData(map);
        return message;
    }

    @Override
    public HaramMessage courseInfoList(String search) {
        try {
            return courseServer.courseInfoList(IP, PORT, search);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage getAssignmentList(String crn) {
        HaramMessage message = courseServer.getCourseByCrn(IP, PORT, crn);

        LinkedHashMap courseMap = (LinkedHashMap) message.getData();
        String rawAssignment = (String) courseMap.get("assignment");

        JSONArray assignmentList = new JSONArray();
        if (StringUtils.isNotEmpty(rawAssignment)) {
            assignmentList = JSONArray.parseArray(rawAssignment);
        }

        message.setData(assignmentList);
        message.setCode(FlagDict.SUCCESS.getV());
        message.setMsg(FlagDict.SUCCESS.getM());
        return message;
    }

    @Override
    public HaramMessage updateAssignment(String crn, JSONArray assignment) {
        HaramMessage message = courseServer.getCourseByCrn(IP, PORT, crn);
        LinkedHashMap courseMap = (LinkedHashMap) message.getData();
        Course course = new Course();
        try {
            BeanUtils.populate(course, courseMap);
            course.setAssignment(JSONArray.toJSONString(assignment));
            message = courseServer.update(IP, PORT, crn, course);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return message;
    }

    @Override
    public HaramMessage uploadAssignmentAttachment(String crn, MultipartFile multipartFile) {
        return null;
    }

    @Override
    public HaramMessage submitAssignment(String crn, String assignmentName, String createTime, String studentId, MultipartFile multipartFile) {
        return null;
    }

    @Override
    public HaramMessage studentList(String crn, String search) {
        try {
            return courseServer.studentList(IP, PORT, crn, search);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage getCourseBySearch(String search, String status) {
        try {
            return courseServer.getCourseBySearch(IP, PORT, search, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage assignFac2Cou(String crn, String facultyId) {
        try {
            return courseServer.assignFac2Cou(IP, PORT, crn, facultyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage preCourseList(String crn) {
        try {
            return courseServer.preCourseList(IP, PORT, crn);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage addStu2Cou(String crn, String studentId, Option option) {
        try {
            return courseServer.addStu2Cou(IP, PORT, crn, studentId, option);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage removeStuFromCou(String crn, String studentId) {
        try {
            return courseServer.removeStuFromCou(IP, PORT, crn, studentId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage reg2Course(String studentId, JSONObject choiceList) {
        try {
            return courseServer.reg2Course(IP, PORT, studentId, choiceList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }
}
