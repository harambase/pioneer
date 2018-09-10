package com.harambase.pioneer.server.service;

import com.alibaba.fastjson.JSONArray;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Course;
import com.harambase.pioneer.server.pojo.dto.Option;

/**
 * Created by linsh on 7/12/2017.
 */
public interface CourseServerService {

    ResultMap addCourse(Course course);

    ResultMap delete(String crn);

    ResultMap update(String crn, Course course);

    ResultMap assignFac2Cou(String crn, String facultyId);

    ResultMap addStu2Cou(String crn, String studentId, Option option);

    ResultMap removeStuFromCou(String crn, String studentId);

    ResultMap getCourseBySearch(String search, String status, String info);

    ResultMap courseList(String s, String s1, String search, String order, String orderCol, String facultyId, String info, String status);

    ResultMap preCourseList(String crn);

    ResultMap reg2Course(String studentId, JSONArray choiceList);

    ResultMap getCourseByCrn(String crn);

    ResultMap courseTreeList(String facultyId, String info);

    ResultMap courseListInfo(String search);

    ResultMap studentList(String crn, String search);

    ResultMap getCourseBase(String crn);
}
