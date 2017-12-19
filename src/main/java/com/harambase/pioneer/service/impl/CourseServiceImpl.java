package com.harambase.pioneer.service.impl;

import com.harambase.common.*;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.pojo.Course;
import com.harambase.pioneer.pojo.base.CourseBase;
import com.harambase.pioneer.pojo.base.TranscriptBase;
import com.harambase.support.util.DateUtil;
import com.harambase.support.util.IDUtil;
import com.harambase.support.util.PageUtil;
import com.harambase.pioneer.server.CourseServer;
import com.harambase.pioneer.server.TranscriptServer;
import com.harambase.common.helper.TimeValidate;
import com.harambase.pioneer.pojo.dto.Option;
import com.harambase.pioneer.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by linsh on 7/12/2017.
 */
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseServer courseServer;
    private final TranscriptServer transcriptServer;

    @Autowired
    public CourseServiceImpl(CourseServer courseServer,
                             TranscriptServer transcriptServer) {
        this.courseServer = courseServer;
        this.transcriptServer = transcriptServer;
    }

    @Override
    public HaramMessage create(CourseBase course) {
       return courseServer.insert(course);
    }

    @Override
    public HaramMessage delete(String crn) {
        return courseServer.deleteByPrimaryKey(crn);
    }

    @Override
    public HaramMessage update(CourseBase course) {
        return courseServer.updateByPrimaryKeySelective(course);
    }

    @Override
    public HaramMessage getCourseByCrn(String crn) {
        return courseServer.selectByPrimaryKey(crn);
    }

    @Override
    public HaramMessage courseTreeList(String facultyid, String info) {
        return courseServer.getCourseByMapPageSearchOrdered(param);
    }

    @Override
    public HaramMessage getCourseBySearch(String search, String status) {
        return courseServer.getCourseBySearch(param);
    }

    @Override
    public HaramMessage assignFac2Cou(String crn, String facultyId) {
        return courseServer.updateByPrimaryKeySelective(course);
    }

    @Override
    public HaramMessage addStu2Cou(String crn, String studentId, Option option) {
       return transcriptServer.insert(crn, studentId, option);
    }

    @Override
    public HaramMessage removeStuFromCou(String crn, String studentid) {
        return transcriptServer.deleteByPrimaryKey(param);

    }

    @Override
    public HaramMessage courseList(String currentPage, String pageSize, String search, String order, String orderColumn,
                                   String facultyid, String info) {
       return courseServer.getCourseByMapPageSearchOrdered(param);
    }

    @Override
    public HaramMessage countActiveCourse() {
        return courseServer.countActiveCourse();
    }

    @Override
    public HaramMessage preCourseList(String crn) {
        HaramMessage haramMessage = new HaramMessage();
        try{
            CourseBase course = courseServer.selectByPrimaryKey(crn);
            String[] precrns = course.getPrecrn().split("/");
            List<CourseBase> preCourses = new ArrayList<>();

            for(String precrn: precrns){
                if(StringUtils.isNotEmpty(precrn))
                    preCourses.add(courseServer.selectByPrimaryKey(precrn));
            }
            haramMessage.setData(preCourses);
            haramMessage.setCode(FlagDict.SUCCESS.getV());
            haramMessage.setMsg(FlagDict.SUCCESS.getM());

        }catch (Exception e){
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
        }
        return haramMessage;
    }

    @Override
    public HaramMessage reg2Course(String studentid, String[] choices) {
        HaramMessage haramMessage = new HaramMessage();
        Map<String, Object> result = new HashMap<>();
        List<String> failList = new ArrayList<>();
        try{
            for(String crn: choices){
                TranscriptBase transcript = new TranscriptBase();
                CourseBase course = courseServer.selectByPrimaryKey(crn);
                String status = courseServer.getStatus(crn);
                String courseInfo = "CRN：" + crn + ", 课程名：" + course.getName() + "，失败原因:";
                //检查课程状态
                if (status.equals("-1")) {
                    failList.add(courseInfo + FlagDict.COURSE_DISABLED.getM());
                    continue;
                }
                //检查时间冲突
                if (TimeValidate.isTimeConflict(transcriptServer.studentCourse(studentid), course)) {
                    failList.add(courseInfo + FlagDict.TIMECONFLICT.getM());
                    continue;
                }
                //检查课程容量
                int remain = courseServer.getRemain(crn);
                if (remain <= 0) {
                    failList.add(courseInfo + FlagDict.MAX_CAPACITY.getM());
                    continue;
                }
                transcript.setAssigntime(DateUtil.DateToStr(new Date()));
                transcript.setComplete("0");
                transcript.setGrade("*");
                transcript.setCrn(crn);
                transcript.setStudentid(studentid);
                //检查预选
                String[] precrns = course.getPrecrn().split("/");
                TranscriptBase preTranscript = new TranscriptBase();
                boolean pre = true;
                for(String precrn: precrns){
                    preTranscript.setComplete("1");
                    preTranscript.setStudentid(studentid);
                    preTranscript.setCrn(precrn);
                    int ret = transcriptServer.count(preTranscript);
                    if (ret != 1) {
                        failList.add(courseInfo + FlagDict.UNMET_PREREQ.getM());
                        pre = false;
                        break;
                    }
                }
                if(!pre)
                    continue;

                //检查复选
                if (transcriptServer.count(transcript) == 0) {
                    transcript.setOperator(IDUtil.ROOT);
                    int ret = transcriptServer.insert(transcript);
                    if (ret != 1){
                        haramMessage.setMsg(FlagDict.FAIL.getM());
                        haramMessage.setCode(FlagDict.FAIL.getV());
                        failList.add(courseInfo + FlagDict.FAIL.getM());
                    }
                } else {
                    haramMessage.setMsg(FlagDict.REPEAT.getM());
                    haramMessage.setCode(FlagDict.REPEAT.getV());
                    failList.add(courseInfo + FlagDict.REPEAT.getM());
                }
            }
            haramMessage.setMsg(FlagDict.SUCCESS.getM());
            haramMessage.setCode(FlagDict.SUCCESS.getV());
            result.put("failList", failList);
            haramMessage.setData(result);
            return haramMessage;

        }catch (Exception e){
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }

    
}
