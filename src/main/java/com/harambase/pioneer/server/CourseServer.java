package com.harambase.pioneer.server;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.server.pojo.base.Course;
import com.harambase.pioneer.server.pojo.dto.Option;
import com.harambase.pioneer.server.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourseServer {

    private final CourseService courseService;

    @Autowired
    public CourseServer(CourseService courseService) {
        this.courseService = courseService;
    }

    public HaramMessage create(Course course) {
        return courseService.addCourse(course);
    }

    public HaramMessage delete(String crn) {
        return courseService.delete(crn);
    }

    public HaramMessage update(String crn, Course course) {
        return courseService.update(crn, course);
    }

    public HaramMessage get(String crn) {
        return courseService.getCourseByCrn(crn);
    }

    public HaramMessage list(Integer start, Integer length, String search, String order,
                             String orderCol, String facultyId, String info) {
        return courseService.courseList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, facultyId, info);
    }

    public HaramMessage studentList(String crn, String search) {
        return courseService.studentList(crn, search);
    }

    public HaramMessage listInfo(String search) {
        return courseService.courseListInfo(search);
    }

    public HaramMessage zTreeList(String facultyId, String info) {
        return courseService.courseTreeList(facultyId, info);
    }

    public HaramMessage search(String search, String status) {
        return courseService.getCourseBySearch(search, status);
    }

    public HaramMessage preCourseList(String crn) {
        return courseService.preCourseList(crn);
    }

    public HaramMessage removeStuFromCourse(String crn, String studentId) {
        return courseService.removeStuFromCou(crn, studentId);
    }

    public HaramMessage assignStu2Course(String crn, String studentId,
                                         Option option) {
        return courseService.addStu2Cou(crn, studentId, option);
    }

    public HaramMessage assignFac2Course(String crn, String facultyId) {
        return courseService.assignFac2Cou(crn, facultyId);
    }

    public HaramMessage courseChoice(String studentId, JSONObject choiceList) {
        return courseService.reg2Course(studentId, choiceList);
    }

}
