package com.harambase.pioneer.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Course;
import com.harambase.pioneer.server.pojo.dto.Option;
import com.harambase.pioneer.server.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.color.ICC_Profile;

@Component
public class CourseServer {

    private final CourseService courseService;

    @Autowired
    public CourseServer(CourseService courseService) {
        this.courseService = courseService;
    }

    public ResultMap create(Course course) {
        return courseService.addCourse(course);
    }

    public ResultMap delete(String crn) {
        return courseService.delete(crn);
    }

    public ResultMap update(String crn, Course course) {
        return courseService.update(crn, course);
    }

    public ResultMap get(String crn) {
        return courseService.getCourseByCrn(crn);
    }

    public ResultMap list(Integer start, Integer length, String search, String order,
                             String orderCol, String facultyId, String info, String status) {
        return courseService.courseList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, facultyId, info, status);
    }

    public ResultMap studentList(String crn, String search) {
        return courseService.studentList(crn, search);
    }

    public ResultMap listInfo(String search) {
        return courseService.courseListInfo(search);
    }

    public ResultMap zTreeList(String facultyId, String info) {
        return courseService.courseTreeList(facultyId, info);
    }

    public ResultMap search(String search, String status, String info) {
        return courseService.getCourseBySearch(search, status, info);
    }

    public ResultMap preCourseList(String crn) {
        return courseService.preCourseList(crn);
    }

    public ResultMap removeStuFromCourse(String crn, String studentId) {
        return courseService.removeStuFromCou(crn, studentId);
    }

    public ResultMap assignStu2Course(String crn, String studentId, Option option) {
        return courseService.addStu2Cou(crn, studentId, option);
    }

    public ResultMap assignFac2Course(String crn, String facultyId) {
        return courseService.assignFac2Cou(crn, facultyId);
    }

    public ResultMap courseChoice(String studentId, JSONArray choiceList) {
        return courseService.reg2Course(studentId, choiceList);
    }

    public ResultMap getCourseBase(String crn) {
        return courseService.getCourseBase(crn);
    }
}
