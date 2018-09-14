package com.harambase.pioneer.server.service;

import com.alibaba.fastjson.JSONArray;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Course;
import com.harambase.pioneer.server.pojo.dto.Option;

public interface CourseServerService {

    ResultMap list(String currentPage, String pageSize, String search, String order, String orderCol, String facultyId, String info, String status);

    ResultMap create(Course course);

    ResultMap delete(String crn);

    ResultMap update(String crn, Course course);

    ResultMap retrieveCourseView(String crn);

    ResultMap retrieveCourseBase(String crn);

    ResultMap search(String search, String status, String info);

    ResultMap assignFaculty(String crn, String facultyId);

    ResultMap addStudent(String crn, String studentId, Option option);

    ResultMap studentRegistration(String studentId, JSONArray choiceList);

    ResultMap removeStudent(String crn, String studentId);

    ResultMap getInfoList(String search);

    ResultMap getStudentList(String crn, String search);

    ResultMap getPrerequisiteList(String crn);

    @Deprecated
    ResultMap courseTreeList(String facultyId, String info);
}
