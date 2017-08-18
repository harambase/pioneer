package com.harambase.pioneer.service;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Course;
import com.harambase.pioneer.pojo.Transcript;
import com.harambase.pioneer.pojo.dto.Option;

/**
 * Created by linsh on 7/12/2017.
 */
public interface CourseService {
    HaramMessage add(Course course);

    HaramMessage remove(String crn);

    HaramMessage get(String courseid);

    HaramMessage updateCourse(Course course);

    HaramMessage assignFac2Cou(Course course);

    HaramMessage addStu2Cou(Option option);

    HaramMessage removeStuFromCou(String studentid, String courseid);

    HaramMessage countStudent(String courseid);

    HaramMessage updateGrade(Transcript transcript);

    HaramMessage listBySearch(String search);

    HaramMessage courseList(String s, String s1, String search, String order, String orderCol);

    HaramMessage transcriptList(String s, String s1, String search, String order, String orderCol, String studentid, String crn);

    HaramMessage studentList(String s, String s1, String search, String order, String orderCol, String crn);
}
