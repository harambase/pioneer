package com.harambase.pioneer.service.impl;

import com.harambase.common.HaramMessage;
import com.harambase.common.Page;
import com.harambase.common.constant.FlagDict;
import com.harambase.support.util.DateUtil;
import com.harambase.support.util.PageUtil;
import com.harambase.support.util.SessionUtil;
import com.harambase.pioneer.server.AdviseServer;
import com.harambase.pioneer.pojo.base.AdviseBase;
import com.harambase.pioneer.pojo.Advise;
import com.harambase.pioneer.service.AdviseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdviseServiceImpl implements AdviseService{

    private final AdviseServer AdviseServer;

    @Autowired
    public AdviseServiceImpl(AdviseServer AdviseServer){
        this.AdviseServer = AdviseServer;
    }


    @Override
    public HaramMessage advisingList(String currentPage, String pageSize, String search, String order, String orderColumn,
                                     String studentid, String facultyid) {
        HaramMessage message = new HaramMessage();
        switch (Integer.parseInt(orderColumn)) {
            case 0:
                orderColumn = "sname";
                break;
            case 1:
                orderColumn = "fname";
                break;
            case 2:
                orderColumn = "status";
                break;
            case 3:
                orderColumn = "updateTime";
                break;
            case 4:
                orderColumn = "operator";
                break;
            default:
                orderColumn = "updateTime";
                break;
        }
        long totalSize = 0;
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("search", search);
            param.put("studentid", studentid);
            param.put("facultyid", facultyid);
            if(StringUtils.isEmpty(studentid))
                param.put("studentid", null);
            if(StringUtils.isEmpty(facultyid))
                param.put("facultyid", null);
            if(StringUtils.isEmpty(search))
                param.put("search", null);

            totalSize = AdviseServer.getAdvisingCountByMapPageSearchOrdered(param); //startTime, endTime);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            param.put("currentIndex", page.getCurrentIndex());
            param.put("pageSize",  page.getPageSize());
            param.put("order",  order);
            param.put("orderColumn",  orderColumn);

            //(int currentIndex, int pageSize, String search, String order, String orderColumn);
            List<Advise> msgs = AdviseServer.getAdvisingByMapPageSearchOrdered(param);

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
    public HaramMessage updateAdvise(Integer id, String studentId, String facultyId) {

        HaramMessage haramMessage = new HaramMessage();
        try {
            AdviseBase advise = new AdviseBase();
            advise.setId(id);
            advise.setOperator(SessionUtil.getUserId());
            advise.setStudentid(studentId);
            advise.setFacultyid(facultyId);
            advise.setUpdateTime(DateUtil.DateToStr(new Date()));
            int ret = AdviseServer.updateByPrimaryKeySelective(advise);
            if(ret == 1) {
                haramMessage.setData(advise);
                haramMessage.setCode(FlagDict.SUCCESS.getV());
                haramMessage.setMsg(FlagDict.SUCCESS.getM());
            }
            else{
                haramMessage.setCode(FlagDict.FAIL.getV());
                haramMessage.setMsg(FlagDict.FAIL.getM());
            }
            return haramMessage;
        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }

    @Override
    public HaramMessage assignMentor(AdviseBase advise) {
        HaramMessage haramMessage = new HaramMessage();
        try {
            advise.setOperator(SessionUtil.getUserId());
            advise.setUpdateTime(DateUtil.DateToStr(new Date()));
            AdviseBase a = AdviseServer.selectByAdvise(advise);
            if(a != null){
                haramMessage.setCode(FlagDict.ADVISE_DUPLICATE.getV());
                haramMessage.setMsg(FlagDict.ADVISE_DUPLICATE.getM());
                return haramMessage;
            }
            int ret = AdviseServer.insertSelective(advise);
            if(ret == 1) {
                haramMessage.setData(advise);
                haramMessage.setCode(FlagDict.SUCCESS.getV());
                haramMessage.setMsg(FlagDict.SUCCESS.getM());
            }
            else{
                haramMessage.setCode(FlagDict.FAIL.getV());
                haramMessage.setMsg(FlagDict.FAIL.getM());
            }
            return haramMessage;
        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }

    @Override
    public HaramMessage removeMentor(Integer id) {
        HaramMessage haramMessage = new HaramMessage();
        try {
            int ret = AdviseServer.deleteByPrimaryKey(id);
            if(ret == 1) {
                haramMessage.setCode(FlagDict.SUCCESS.getV());
                haramMessage.setMsg(FlagDict.SUCCESS.getM());
            }
            else{
                haramMessage.setCode(FlagDict.FAIL.getV());
                haramMessage.setMsg(FlagDict.FAIL.getM());
            }
            return haramMessage;
        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }

    @Override
    public HaramMessage getMentor(Integer id) {
        HaramMessage haramMessage = new HaramMessage();
        try {
            AdviseBase advise = AdviseServer.selectByPrimaryKey(id);
            haramMessage.setData(advise);
            haramMessage.setCode(FlagDict.SUCCESS.getV());
            haramMessage.setMsg(FlagDict.SUCCESS.getM());
            return haramMessage;
        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }
}
