package com.harambase.pioneer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.server.CourseServer;
import com.harambase.pioneer.server.pojo.base.Course;
import com.harambase.pioneer.server.pojo.dto.Option;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
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
public class CourseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CourseServer courseServer;

    @Autowired
    public CourseService(CourseServer courseServer) {
        this.courseServer = courseServer;
    }

    
    public ResultMap create(Course course) {
        try {
            return courseServer.create(course);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap delete(String crn) {
        try {
            return courseServer.delete(crn);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap update(String crn, Course course) {
        try {
            return courseServer.update(crn, course);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap getCourseByCrn(String crn) {
        try {
            return courseServer.get(crn);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap courseList(int start, int length, String search, String order, String orderColumn, String facultyid, String info) {
        try {
            return courseServer.list(start, length, search, order, orderColumn, facultyid, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap courseTreeList(String facultyid, String info) {
        try {
            return courseServer.zTreeList(facultyid, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }
    
    public ResultMap uploadInfo(String crn, MultipartFile file) {
        ResultMap message = new ResultMap();
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = new JSONObject();

        try {
            LinkedHashMap courseMap = (LinkedHashMap) courseServer.get(crn).getData();
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

            message = courseServer.update(crn, course);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message.setMsg("上传失败");
            message.setCode(SystemConst.SYSTEM_ERROR.getCode());
            return message;
        }

        message.setMsg("上传成功");
        message.setData(map);
        return message;
    }

    
    public ResultMap courseInfoList(String search) {
        try {
            return courseServer.listInfo(search);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap getAssignmentList(String crn) {
        ResultMap message = courseServer.get(crn);

        LinkedHashMap courseMap = (LinkedHashMap) message.getData();
        String rawAssignment = (String) courseMap.get("assignment");

        JSONArray assignmentList = new JSONArray();
        if (StringUtils.isNotEmpty(rawAssignment)) {
            assignmentList = JSONArray.parseArray(rawAssignment);
        }

        message.setData(assignmentList);
        message.setCode(SystemConst.SUCCESS.getCode());
        message.setMsg(SystemConst.SUCCESS.getMsg());
        return message;
    }

    
    public ResultMap updateAssignment(String crn, JSONArray assignment) {
        ResultMap message = courseServer.get(crn);
        LinkedHashMap courseMap = (LinkedHashMap) message.getData();
        Course course = new Course();
        try {
            BeanUtils.populate(course, courseMap);
            course.setAssignment(JSONArray.toJSONString(assignment));
            message = courseServer.update(crn, course);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return message;
    }

    
    public ResultMap uploadAssignmentAttachment(String crn, MultipartFile multipartFile) {
        return null;
    }

    
    public ResultMap submitAssignment(String crn, String assignmentName, String createTime, String studentId, MultipartFile multipartFile) {
        return null;
    }

    
    public ResultMap studentList(String crn, String search) {
        try {
            return courseServer.studentList(crn, search);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap getCourseBySearch(String search, String status) {
        try {
            return courseServer.search(search, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap assignFac2Cou(String crn, String facultyId) {
        try {
            return courseServer.assignFac2Course(crn, facultyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap preCourseList(String crn) {
        try {
            return courseServer.preCourseList(crn);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap addStu2Cou(String crn, String studentId, Option option) {
        try {
            return courseServer.assignStu2Course(crn, studentId, option);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap removeStuFromCou(String crn, String studentId) {
        try {
            return courseServer.removeStuFromCourse(crn, studentId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap reg2Course(String studentId, JSONObject choiceList) {
        try {
            return courseServer.courseChoice(studentId, choiceList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }
}
