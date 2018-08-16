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

//    public ResultMap getContract(String studentId) {
//        try {
//            return studentServer.getContract(studentId);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            return ReturnMsgUtil.systemError();
//        }
//    }
//
//    public ResultMap updateContract(String studentId, String contractString) {
//        try {
//            return studentServer.updateContract(studentId, contractString);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            return ReturnMsgUtil.systemError();
//        }
//    }
//
//    public ResultMap downloadContractById(String studentId) {
//        FileOutputStream fos = null;
//        String csvPath = Config.TEMP_FILE_PATH + studentId + "合同表.csv";
//        ResultMap message = null;
//
//        try {
//            File outputFile = new File(csvPath);
//            if(outputFile.exists()) {
//                outputFile.delete();
//                outputFile = new File(csvPath);
//            }
//            fos = new FileOutputStream(outputFile, true);
//            //Solve for Chinese Character errors while using excel:
//            fos.write(new byte[]{(byte)0xEF,(byte)0xBB,(byte)0xBF});
//
//            JSONArray contractList = JSONArray.parseArray((String) studentServer.getContract(studentId).getData());
//
//            StringBuilder exportInfoSb = new StringBuilder();
//            for (int i = 0; i < contractList.size(); i++) {
//                if (i != 0) exportInfoSb.append(",");
//                exportInfoSb.append("\"" + contractList.get(0) + "\"");
//            }
//            exportInfoSb.append("\n");
//            for (int i = 0; i < adviseViewList.size(); i++) {
//                Map<String, String> tvMap = BeanUtils.describe(adviseViewList.get(i));
//                for (int j = 0; j < titleList.length; j++) {
//                    if (j != 0) exportInfoSb.append(",");
//                    exportInfoSb.append("\"" + tvMap.get(titleList[j].getName()) + "\"");
//                }
//                exportInfoSb.append("\n");
//            }
//            exportInfoSb.append("总计" + adviseViewList.size() + "条数据。");
//            fos.write(exportInfoSb.toString().getBytes("UTF-8"));
//
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        } finally {
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    logger.error(e.getMessage(), e);
//                }
//            }
//        }
//
//        logger.info("Filing task for advise has completed.");
//        ResultMap restMessage = new ResultMap();
//        restMessage.setCode(SystemConst.SUCCESS.getCode());
//        restMessage.setData(studentId + "合同表.csv");
//        return restMessage;
//    }

}
