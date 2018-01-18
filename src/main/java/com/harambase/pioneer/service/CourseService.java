package com.harambase.pioneer.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Course;
import com.harambase.pioneer.pojo.dto.Option;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;

public interface CourseService {

    HaramMessage create(Course course);

    HaramMessage delete(String crn);

    HaramMessage update(String crn, Course course);

    HaramMessage assignFac2Cou(String crn, String facultyId);

    HaramMessage addStu2Cou(String crn, String studentId, Option option);

    HaramMessage removeStuFromCou(String crn, String studentId);

    HaramMessage getCourseBySearch(String search, String status);

    HaramMessage courseList(int start, int length, String search, String order, String orderCol, String facultyid, String info);

    HaramMessage preCourseList(String crn);

    HaramMessage reg2Course(String studentId, JSONObject choiceList);

    HaramMessage getCourseByCrn(String crn);

    HaramMessage courseTreeList(String facultyId, String info);

    HaramMessage uploadInfo(String crn, MultipartFile file);

    HaramMessage courseInfoList(String search);

    HaramMessage getAssignmentList(String crn);

    HaramMessage updateAssignment(String crn, JSONArray assignment);

    HaramMessage uploadAssignmentAttachment(String crn, MultipartFile multipartFile);

    HaramMessage submitAssignment(String crn, String assignmentName, String createTime, String studentId, MultipartFile multipartFile);

    HaramMessage studentList(String crn, String search);
}
