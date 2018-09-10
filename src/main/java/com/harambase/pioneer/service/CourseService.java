package com.harambase.pioneer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.server.pojo.base.Course;
import com.harambase.pioneer.server.pojo.dto.Option;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.service.CourseServerService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;

@Service
public class CourseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CourseServerService courseServerService;

    @Autowired
    public CourseService(CourseServerService courseServerService) {
        this.courseServerService = courseServerService;
    }


    public ResultMap create(Course course) {
        try {
            return courseServerService.addCourse(course);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap delete(String crn) {
        try {
            return courseServerService.delete(crn);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap update(String crn, Course course) {
        try {
            return courseServerService.update(crn, course);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap getCourseByCrn(String crn) {
        try {
            return courseServerService.getCourseByCrn(crn);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap courseList(int start, int length, String search, String order, String orderColumn, String facultyId, String info, String status) {
        try {
            return courseServerService.courseList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderColumn, facultyId, info, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap uploadInfo(String crn, MultipartFile file) {

        ResultMap message = new ResultMap();
        JSONObject jsonObject = new JSONObject();

        try {
            Course course = (Course) courseServerService.getCourseBase(crn).getData();

            //处理老的文件
            if (StringUtils.isNotEmpty(course.getCourseInfo())) {
                String oldInfoPath = (JSON.parseObject(course.getCourseInfo())).getString("path");
                FileUtil.deleteFileFromFTP(oldInfoPath, Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);
            }

            String fileUri = FileUtil.uploadFileToFtpServer(file, Config.FTP_PATH + "/document/courseInfo", Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);

            String name = file.getOriginalFilename();

            jsonObject.put("name", name);
            jsonObject.put("size", file.getSize());
            jsonObject.put("type", name.substring(name.lastIndexOf(".") + 1));
            jsonObject.put("path", fileUri);

            course.setCourseInfo(jsonObject.toJSONString());

            message = courseServerService.update(crn, course);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message.setMsg("上传失败");
            message.setCode(SystemConst.SYSTEM_ERROR.getCode());
            return message;
        }

        message.setMsg("上传成功");
        message.setData(jsonObject);
        message.setCode(SystemConst.SUCCESS.getCode());
        return message;
    }


    public ResultMap courseInfoList(String search) {
        try {
            return courseServerService.courseListInfo(search);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap studentList(String crn, String search) {
        try {
            return courseServerService.studentList(crn, search);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap getCourseBySearch(String search, String status, String info) {
        try {
            return courseServerService.getCourseBySearch(search, status, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap assignFac2Cou(String crn, String facultyId) {
        try {
            return courseServerService.assignFac2Cou(crn, facultyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap preCourseList(String crn) {
        try {
            return courseServerService.preCourseList(crn);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap addStu2Cou(String crn, String studentId, Option option) {
        try {
            return courseServerService.addStu2Cou(crn, studentId, option);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap removeStuFromCou(String crn, String studentId) {
        try {
            return courseServerService.removeStuFromCou(crn, studentId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap reg2Course(String studentId, JSONArray choiceList) {
        try {
            return courseServerService.reg2Course(studentId, choiceList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

//    public ResultMap uploadSchedule(String info, MultipartFile file) {
//        ResultMap message = new ResultMap();
//        JSONObject jsonObject = new JSONObject();
//
//        try {
//            Course course = (Course) courseServerService.getCourseBase(crn).getData();
//
//            //处理老的文件
//            if (StringUtils.isNotEmpty(course.getCourseInfo())) {
//                String oldInfoPath = (JSON.parseObject(course.getCourseInfo())).getString("path");
//                FileUtil.deleteFileFromFTP(oldInfoPath, Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);
//            }
//
//            String fileUri = FileUtil.uploadFileToFtpServer(file, Config.FTP_PATH + "/document/courseInfo", Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);
//
//            String name = file.getOriginalFilename();
//
//            jsonObject.put("name", name);
//            jsonObject.put("size", file.getSize());
//            jsonObject.put("type", name.substring(name.lastIndexOf(".") + 1));
//            jsonObject.put("path", fileUri);
//
//            course.setCourseInfo(jsonObject.toJSONString());
//
//            message = courseServerService.update(crn, course);
//
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            message.setMsg("上传失败");
//            message.setCode(SystemConst.SYSTEM_ERROR.getCode());
//            return message;
//        }
//
//        message.setMsg("上传成功");
//        message.setData(jsonObject);
//        message.setCode(SystemConst.SUCCESS.getCode());
//        return message;
//    }
}
