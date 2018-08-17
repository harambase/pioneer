package com.harambase.pioneer.service;

import com.alibaba.fastjson.JSONArray;
import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.server.StudentServer;
import com.harambase.pioneer.server.pojo.base.Student;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.pojo.view.AdviseView;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StudentServer studentServer;

    @Autowired
    public StudentService(StudentServer studentServer) {
        this.studentServer = studentServer;
    }


    public ResultMap transcriptDetail(String studentid) {
        try {
            return studentServer.getTranscriptDetail(studentid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap update(String studentId, Student student) {
        try {
            return studentServer.update(studentId, student);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap studentList(int start, int length, String search, String order, String orderColumn, String status) {
        try {
            return studentServer.list(start, length, search, order, orderColumn, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap getAvailableCredit(String studentid, String info) {
        try {
            return studentServer.getAvailableCredit(studentid, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap courseList(String status, String studentId) {
        try {
            return studentServer.courseList(status, studentId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
