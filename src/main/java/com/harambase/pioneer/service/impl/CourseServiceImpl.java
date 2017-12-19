package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.common.Page;
import com.harambase.pioneer.pojo.Course;
import com.harambase.pioneer.pojo.dto.Option;
import com.harambase.pioneer.server.CourseServer;
import com.harambase.pioneer.server.TranscriptServer;
import com.harambase.pioneer.service.CourseService;
import com.harambase.support.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by linsh on 7/12/2017.
 */
@Service
public class CourseServiceImpl implements CourseService {

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final CourseServer courseServer;
    private final TranscriptServer transcriptServer;

    @Autowired
    public CourseServiceImpl(CourseServer courseServer,
                             TranscriptServer transcriptServer) {
        this.courseServer = courseServer;
        this.transcriptServer = transcriptServer;
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
    public HaramMessage update(Course course) {
        return courseServer.update(IP, PORT, course);
    }

    @Override
    public HaramMessage getCourseByCrn(String crn) {
        return courseServer.getCourseByCrn(IP, PORT, crn);
    }

    @Override
    public HaramMessage courseList(String currentPage, String pageSize, String search, String order, String orderColumn, String facultyid, String info) {
        Page page = new Page();
        page.setCurrentPage(PageUtil.getcPg(currentPage));
        page.setPageSize(PageUtil.getLimit(pageSize));
        return courseServer.courseList(IP, PORT, page.getCurrentIndex(), page.getPageSize(), search, order, orderColumn, facultyid, info);
    }

    @Override
    public HaramMessage courseTreeList(String facultyid, String info) {
        return courseServer.courseList(IP, PORT, 0, Integer.MAX_VALUE, "", "desc", "id", facultyid, info);
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
        return transcriptServer.addStu2Cou(IP, PORT, crn, studentId, option);
    }

    @Override
    public HaramMessage removeStuFromCou(String crn, String studentId) {
        return transcriptServer.removeStuFromCou(IP, PORT, crn, studentId);
    }

    @Override
    public HaramMessage reg2Course(String studentId, String[] choices) {
        return transcriptServer.reg2Course(IP, PORT, studentId, choices);
    }
}
