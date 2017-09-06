package com.harambase.pioneer.service.Impl;

import com.harambase.common.*;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.dao.mapper.CourseMapper;
import com.harambase.pioneer.dao.mapper.TranscriptMapper;
import com.harambase.pioneer.helper.CheckTime;
import com.harambase.pioneer.pojo.Course;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.pojo.Transcript;
import com.harambase.pioneer.pojo.dto.CourseView;
import com.harambase.pioneer.pojo.dto.Option;
import com.harambase.pioneer.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by linsh on 7/12/2017.
 */
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseMapper courseMapper;
    private final TranscriptMapper transcriptMapper;

    @Autowired
    public CourseServiceImpl(CourseMapper courseMapper,
                             TranscriptMapper transcriptMapper) {
        this.courseMapper = courseMapper;
        this.transcriptMapper = transcriptMapper;
    }

    @Override
    public HaramMessage add(Course course) {
        HaramMessage haramMessage = new HaramMessage();
        try {
            course.setCreatetime(DateUtil.DateToStr(new Date()));
            course.setUpdatetime(DateUtil.DateToStr(new Date()));
            String facultyid = course.getFacultyid();
            //生成CRN
            String info = course.getInfo();
            List<Course> courses = courseMapper.getAllCoursesWithInfo(info);
            String crn = IDUtil.genCRN(info);
            for(int i = 0; i<courses.size(); i++){
                Course c = courses.get(i);
                if(crn.equals(c.getCrn())){
                    crn = IDUtil.genCRN(info);
                    i = 0;
                }
            }
            course.setCrn(crn);
            //检查时间冲突
            if (CheckTime.isTimeConflict(courseMapper, haramMessage, course))
                return haramMessage;

            course.setFacultyid(facultyid);
            //插入课程
            int ret = courseMapper.insert(course);
            if (ret == 1) {
                haramMessage.setCode(FlagDict.SUCCESS.getV());
                haramMessage.setMsg(FlagDict.SUCCESS.getM());
                haramMessage.setData(course);
            } else {
                haramMessage.setCode(FlagDict.FAIL.getV());
                haramMessage.setMsg(FlagDict.FAIL.getM());
            }
            return haramMessage;
        } catch (Exception e) {
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }

    @Override
    @Transactional
    public HaramMessage remove(String crn) {
        HaramMessage haramMessage = new HaramMessage();
        try{
            int ret = transcriptMapper.deleteByCRN(crn);
            if(ret >= 0) {
                ret = courseMapper.deleteByPrimaryKey(crn);
                if (ret == 1) {
                    haramMessage.setCode(FlagDict.SUCCESS.getV());
                    haramMessage.setMsg(FlagDict.SUCCESS.getM());
                    
                }
            }else {
                haramMessage.setCode(FlagDict.FAIL.getV());
                haramMessage.setMsg(FlagDict.FAIL.getM());
            }
            throw new RuntimeException("Error");
            //return haramMessage;

        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }

    @Override
    public HaramMessage updateCourse(Course course) {
        HaramMessage haramMessage = new HaramMessage();
        try {
            course.setUpdatetime(DateUtil.DateToStr(new Date()));
            int ret = courseMapper.updateByPrimaryKeySelective(course);
            if (ret == 1) {
                haramMessage.setCode(FlagDict.SUCCESS.getV());
                haramMessage.setMsg(FlagDict.SUCCESS.getM());
                haramMessage.setData(course);
            } else {
                haramMessage.setCode(FlagDict.FAIL.getV());
                haramMessage.setMsg(FlagDict.FAIL.getM());
            }
            return haramMessage;
        } catch (Exception e) {
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }

    @Override
    public HaramMessage assignFac2Cou(Course course) {
        HaramMessage haramMessage = new HaramMessage();
        try {
            String newFacultyid = course.getFacultyid();
            Course c = courseMapper.selectByPrimaryKey(course.getCrn());
            c.setFacultyid(newFacultyid);
            //检查时间冲突
            if (CheckTime.isTimeConflict(courseMapper, haramMessage, c)) return haramMessage;

            course.setUpdatetime(DateUtil.DateToStr(new Date()));
            int ret = courseMapper.updateByPrimaryKeySelective(course);
            if (ret == 1) {
                haramMessage.setCode(FlagDict.SUCCESS.getV());
                haramMessage.setMsg(FlagDict.SUCCESS.getM());
                haramMessage.setData(course);
            } else {
                haramMessage.setCode(FlagDict.FAIL.getV());
                haramMessage.setMsg(FlagDict.FAIL.getM());
            }
            return haramMessage;
        } catch (Exception e) {
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }

    @Override
    public HaramMessage addStu2Cou(Option option) {
        HaramMessage haramMessage = new HaramMessage();
        try {

            Transcript transcript = new Transcript();
            String crn = option.getCrn();
            String studentid = option.getStudentid();
            Course course = courseMapper.selectByPrimaryKey(crn);
            Date endDate = DateUtil.StrToDate(course.getEnddate() + " 00:00:00");
            //检查课程状态
            if (endDate.compareTo(new Date()) < 0) {
                haramMessage.setMsg(FlagDict.COURSE_DISABLED.getM());
                haramMessage.setCode(FlagDict.COURSE_DISABLED.getV());
                return haramMessage;
            }
            //检查时间冲突
            String time = course.getStarttime() + "-" + course.getEndtime();
            String date = course.getStartdate() + " to " + course.getEnddate();
            String day = course.getDay();
            Map<String, String> param = new HashMap<>();
            param.put("studentid", studentid);
            param.put("time", time);
            param.put("date", date);
            param.put("day", day);
            param.put("crn", crn);
            int count = transcriptMapper.checkTime(param);
            if (count != 0 && !option.isTime()) {
                haramMessage.setMsg(FlagDict.TIMECONFLICT.getM());
                haramMessage.setCode(FlagDict.TIMECONFLICT.getV());
                return haramMessage;
            }
            //检查课程容量
            int remain = courseMapper.getRemain(crn);
            if (remain <= 0 && !option.isCapacity()) {
                haramMessage.setMsg(FlagDict.MAX_CAPACITY.getM());
                haramMessage.setCode(FlagDict.MAX_CAPACITY.getV());
                return haramMessage;
            }
            transcript.setAssigntime(DateUtil.DateToStr(new Date()));
            transcript.setComplete("In Progress");
            transcript.setGrade("*");
            transcript.setCrn(crn);
            transcript.setStudentid(studentid);
            //检查预选
            String precrn = course.getPrecrn();
            if (StringUtils.isNotEmpty(precrn)) {
                Transcript preTranscript = new Transcript();
                preTranscript.setComplete("Complete");
                preTranscript.setStudentid(studentid);
                preTranscript.setCrn(precrn);
                int ret = transcriptMapper.count(preTranscript);
                if (ret != 1 && !option.isPrereq()) {
                    haramMessage.setMsg(FlagDict.UNMET_PREREQ.getM());
                    haramMessage.setCode(FlagDict.UNMET_PREREQ.getV());
                    return haramMessage;
                }
            }
            //检查复选
            if (transcriptMapper.count(transcript) == 0) {
                int ret = transcriptMapper.insert(transcript);
                if (ret == 1) {
                    haramMessage.setMsg(FlagDict.SUCCESS.getM());
                    haramMessage.setCode(FlagDict.SUCCESS.getV());
                    return haramMessage;
                } else {
                    haramMessage.setMsg(FlagDict.FAIL.getM());
                    haramMessage.setCode(FlagDict.FAIL.getV());
                }
            } else {
                haramMessage.setMsg(FlagDict.REPEAT.getM());
                haramMessage.setCode(FlagDict.REPEAT.getV());
            }

            return haramMessage;

        } catch (Exception e) {
            e.printStackTrace();
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            return haramMessage;
        }
    }

    @Override
    public HaramMessage removeStuFromCou(String studentid, String crn) {
        HaramMessage haramMessage = new HaramMessage();
        try{
            Map<String, Object> param = new HashMap<>();
            param.put("studentid", studentid);
            param.put("crn", crn);
            int ret = 1;//transcriptMapper.deleteByPrimaryKey(param);
            if(ret == 1){
                haramMessage.setCode(FlagDict.SUCCESS.getV());
                haramMessage.setMsg(FlagDict.SUCCESS.getM());
                return haramMessage;
            }
            haramMessage.setCode(FlagDict.FAIL.getV());
            haramMessage.setMsg(FlagDict.FAIL.getM());
            return haramMessage;

        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }

    @Override
    public HaramMessage updateGrade(Transcript transcript) {
        HaramMessage message = new HaramMessage();
        try {
            transcript.setAssigntime(DateUtil.DateToStr(new Date()));
            int ret = transcriptMapper.updateByPrimaryKey(transcript);
            if (ret == 1) {
                message.setData(transcript);
                message.setMsg(FlagDict.SUCCESS.getM());
                message.setCode(FlagDict.SUCCESS.getV());
            } else {
                message.setMsg(FlagDict.FAIL.getM());
                message.setCode(FlagDict.FAIL.getV());
            }
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            message.setMsg(FlagDict.SYSTEM_ERROR.getM());
            message.setCode(FlagDict.SYSTEM_ERROR.getV());
            return message;
        }
    }

    @Override
    public HaramMessage listBySearch(String search) {
        HaramMessage message = new HaramMessage();
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("search", search);

            if (search.equals(""))
                param.put("search", null);

            List<CourseView> results = courseMapper.getCourseBySearch(param);
            List<CourseView> courses = new ArrayList<>();
            int index = 0;

            if(results.size() > 1) {
                for(CourseView c: results){
                    if (c.getStatus().equals("1"))
                        courses.add(c);
                    index++;
                    if(index == 5) break;
                }
            }else if(results.size() == 1){
                CourseView cv = results.get(0);
                if(cv.getStatus().equals("0")){
                    message.setCode(FlagDict.COURSE_FINISHED.getV());
                    message.setMsg(FlagDict.COURSE_FINISHED.getM());
                    return message;
                }
                courses.add(cv);
                message.setData(courses);
            }
            message.setMsg(FlagDict.SUCCESS.getM());
            message.setCode(FlagDict.SUCCESS.getV());
            return message;

        } catch (Exception e) {
            e.printStackTrace();
            message.setMsg(FlagDict.SYSTEM_ERROR.getM());
            message.setCode(FlagDict.SYSTEM_ERROR.getV());
            return message;
        }
    }

    @Override
    public HaramMessage courseList(String currentPage, String pageSize, String search, String order, String orderColumn) {
        HaramMessage message = new HaramMessage();
        switch (Integer.parseInt(orderColumn)) {
            case 0:
                orderColumn = "id";
                break;
            case 1:
                orderColumn = "crn";
                break;
            case 2:
                orderColumn = "name";
                break;
            case 3:
                orderColumn = "coulev";
                break;
            case 4:
                orderColumn = "cousec";
                break;
            case 5:
                orderColumn = "capa";
                break;
            case 6:
                orderColumn = "remain";
                break;
            case 7:
                orderColumn = "status";
                break;
            case 8:
                orderColumn = "date";
                break;
            case 9:
                orderColumn = "time";
                break;
            case 10:
                orderColumn = "day";
                break;
            case 11:
                orderColumn = "faculty";
                break;
            default:
                orderColumn = "updatetime";
                break;
        }
        long totalSize = 0;
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("search", search);

            if (search.equals(""))
                param.put("search", null);


            totalSize = courseMapper.getCourseCountByMapPageSearchOrdered(param); //startTime, endTime);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            param.put("currentIndex", page.getCurrentIndex());
            param.put("pageSize", page.getPageSize());
            param.put("order", order);
            param.put("orderColumn", orderColumn);

            List<Course> courses = courseMapper.getCourseByMapPageSearchOrdered(param);

            message.setData(courses);
            message.put("page", page);
            message.setMsg(FlagDict.SUCCESS.getM());
            message.setCode(FlagDict.SUCCESS.getV());
            return message;

        } catch (Exception e) {
            e.printStackTrace();
            message.setMsg(FlagDict.SYSTEM_ERROR.getM());
            message.setCode(FlagDict.SYSTEM_ERROR.getV());
            return message;
        }
    }

    @Override
    public HaramMessage transcriptList(String currentPage, String pageSize, String search, String order, String orderColumn, String studentid, String crn) {
        HaramMessage message = new HaramMessage();
        switch (Integer.parseInt(orderColumn)) {
            case 0:
                orderColumn = "id";
                break;
            case 1:
                orderColumn = "studentid";
                break;
            case 2:
                orderColumn = "slast";
                break;
            case 3:
                orderColumn = "sfirst";
                break;
            case 4:
                orderColumn = "crn";
                break;
            case 5:
                orderColumn = "grade";
                break;
            case 6:
                orderColumn = "complete";
                break;
            case 7:
                orderColumn = "facultyid";
                break;
            case 8:
                orderColumn = "assigntime";
                break;
            default:
                orderColumn = "id";
                break;
        }
        long totalSize = 0;
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("search", search);
            param.put("studentid", studentid);
            param.put("crn", crn);

            if (StringUtils.isEmpty(search))
                param.put("search", null);
            if (StringUtils.isEmpty(studentid))
                param.put("studentid", null);
            if (StringUtils.isEmpty(crn))
                param.put("crn", null);

            totalSize = transcriptMapper.getTranscriptCountByMapPageSearchOrdered(param); //startTime, endTime);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            param.put("currentIndex", page.getCurrentIndex());
            param.put("pageSize", page.getPageSize());
            param.put("order", order);
            param.put("orderColumn", orderColumn);

            //(int currentIndex, int pageSize, String search, String order, String orderColumn);
            List<Person> msgs = transcriptMapper.getTranscriptByMapPageSearchOrdered(param);

            message.setData(msgs);
            message.put("page", page);
            message.setMsg(FlagDict.SUCCESS.getM());
            message.setCode(FlagDict.SUCCESS.getV());
            return message;

        } catch (Exception e) {
            e.printStackTrace();
            message.setMsg(FlagDict.SYSTEM_ERROR.getM());
            message.setCode(FlagDict.SYSTEM_ERROR.getV());
            return message;
        }
    }

    @Override
    public HaramMessage studentList(String currentPage, String pageSize, String search, String order, String orderColumn, String crn) { {
            HaramMessage message = new HaramMessage();
            switch (Integer.parseInt(orderColumn)) {
                case 1:
                    orderColumn = "studentid";
                    break;
                case 2:
                    orderColumn = "sfirst";
                    break;
                case 3:
                    orderColumn = "slast";
                    break;
                default:
                    orderColumn = "id";
                    break;
            }
            long totalSize = 0;
            try {
                Map<String, Object> param = new HashMap<>();
                param.put("search", search);
                param.put("crn", crn);

                if (search.equals(""))
                    param.put("search", null);
                if (crn.equals(""))
                    param.put("crn", null);

                totalSize = transcriptMapper.getStudentInCourseCountByMapPageSearchOrdered(param); //startTime, endTime);

                Page page = new Page();
                page.setCurrentPage(PageUtil.getcPg(currentPage));
                page.setPageSize(PageUtil.getLimit(pageSize));
                page.setTotalRows(totalSize);

                param.put("currentIndex", page.getCurrentIndex());
                param.put("pageSize", page.getPageSize());
                param.put("order", order);
                param.put("orderColumn", orderColumn);

                //(int currentIndex, int pageSize, String search, String order, String orderColumn);
                List<Person> msgs = transcriptMapper.getStudentInCourseByMapPageSearchOrdered(param);

                message.setData(msgs);
                message.put("page", page);
                message.setMsg(FlagDict.SUCCESS.getM());
                message.setCode(FlagDict.SUCCESS.getV());
                return message;

            } catch (Exception e) {
                e.printStackTrace();
                message.setMsg(FlagDict.SYSTEM_ERROR.getM());
                message.setCode(FlagDict.SYSTEM_ERROR.getV());
                return message;
            }
        }
    }

    @Override
    public HaramMessage countActiveCourse() {
        HaramMessage message = new HaramMessage();
        int c = courseMapper.countActiveCourse();
        message.setData(c);
        return message;
    }
    
}
