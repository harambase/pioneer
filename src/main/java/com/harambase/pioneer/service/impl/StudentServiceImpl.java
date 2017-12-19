package com.harambase.pioneer.service.impl;

import com.harambase.common.HaramMessage;
import com.harambase.common.Page;
import com.harambase.pioneer.pojo.base.CourseBase;
import com.harambase.pioneer.pojo.base.StudentBase;
import com.harambase.support.util.PageUtil;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.server.StudentServer;
import com.harambase.pioneer.server.TranscriptServer;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.pojo.Student;
import com.harambase.pioneer.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentServer studentServer;
    private final TranscriptServer transcriptServer;

    @Autowired
    public StudentServiceImpl(StudentServer studentServer, TranscriptServer transcriptServer){
        this.transcriptServer = transcriptServer;
        this.studentServer = studentServer;
    }

    @Override
    public HaramMessage transcriptDetail(String studentid) {
       HaramMessage haramMessage = new HaramMessage();
       try{
           Student sv = studentServer.creditsDetail(studentid);
           haramMessage.setData(sv);
           haramMessage.setCode(FlagDict.SUCCESS.getV());
           haramMessage.setMsg(FlagDict.SUCCESS.getM());
           return haramMessage;
       }catch(Exception e){
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
       }
    }

    @Override
    public HaramMessage update(StudentBase student) {
        HaramMessage haramMessage = new HaramMessage();
        try{
            int ret = studentServer.updateByPrimaryKey(student);
            if(ret == 1) {
                haramMessage.setData(student);
                haramMessage.setCode(FlagDict.SUCCESS.getV());
                haramMessage.setMsg(FlagDict.SUCCESS.getM());
                return haramMessage;
            }else{
                haramMessage.setCode(FlagDict.FAIL.getV());
                haramMessage.setMsg(FlagDict.FAIL.getM());
                return haramMessage;
            }
        }catch(Exception e){
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }

    @Override
    public HaramMessage studentList(String currentPage, String pageSize, String search, String order, String orderColumn,
                                    String type, String status) {
        HaramMessage message = new HaramMessage();

        switch (Integer.parseInt(orderColumn)) {
            case 0:
                orderColumn = "studentid";
                break;
            case 1:
                orderColumn = "firstname";
                break;
            case 2:
                orderColumn = "lastname";
                break;
            case 3:
                orderColumn = "max_credits";
                break;
            case 4:
                orderColumn = "complete";
                break;
            case 5:
                orderColumn = "progress";
                break;
            case 6:
                orderColumn = "incomplete";
                break;
        }
        long totalSize = 0;
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("search", search);
            param.put("type", type);
            param.put("status", status);

            if(StringUtils.isEmpty(search))
                param.put("search", null);

            totalSize = studentServer.getStudentCountByMapPageSearchOrdered(param); //startTime, endTime);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            param.put("currentIndex", page.getCurrentIndex());
            param.put("pageSize",  page.getPageSize());
            param.put("order",  order);
            param.put("orderColumn",  orderColumn);

            //(int currentIndex, int pageSize, String search, String order, String orderColumn);
            List<Person> msgs = studentServer.getStudentByMapPageSearchOrdered(param);

            message.setData(msgs);
            message.put("page", page);
            message.setMsg(FlagDict.SUCCESS.getM());
            message.setCode(FlagDict.SUCCESS.getV());
            return message;

        }catch (Exception e) {
            e.printStackTrace();
            message.setMsg(FlagDict.SYSTEM_ERROR.getM());
            message.setCode(FlagDict.SYSTEM_ERROR.getV());
            return message;
        }
    }

    @Override
    public HaramMessage getAvailableCredit(String studentid, String info) {
        HaramMessage haramMessage = new HaramMessage();
        try{
            Map<String, Integer> creditInfo = new HashMap<>();
            List<CourseBase> courseList = transcriptServer.studentCourse(studentid);
            Student sv = studentServer.creditsDetail(studentid);
            int use_credits = 0;
            int ava_credits = 0;
            int tol_credits = sv.getMax_credits();
            for(CourseBase course:courseList){
                if(course.getInfo().equals(info))
                    use_credits += course.getCredits();
            }
            ava_credits = tol_credits - use_credits;
            creditInfo.put("tol_credits", tol_credits);
            creditInfo.put("ava_credits", ava_credits);
            creditInfo.put("use_credits", use_credits);

            haramMessage.setData(creditInfo);
            haramMessage.setMsg(FlagDict.SUCCESS.getM());
            haramMessage.setCode(FlagDict.SUCCESS.getV());
            return haramMessage;
        }catch(Exception e){
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }
}
