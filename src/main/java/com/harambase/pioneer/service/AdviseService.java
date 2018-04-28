package com.harambase.pioneer.service;

import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.server.AdviseServer;
import com.harambase.pioneer.server.pojo.base.Advise;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.pojo.view.AdviseView;
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
public class AdviseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AdviseServer adviseServer;

    @Autowired
    public AdviseService(AdviseServer adviseServer) {
        this.adviseServer = adviseServer;
    }

    
    public ResultMap advisingList(int start, int length, String search, String order, String orderColumn, String studentId, String facultyId, String info) {
        try {
            return adviseServer.list(start, length, search, order, orderColumn, studentId, facultyId, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap updateAdvise(Integer id, Advise advise) {
        try {
            return adviseServer.update(id, advise);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap assignMentor(Advise advise) {
        try {
            return adviseServer.create(advise);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap removeMentor(Integer id) {
        try {
            return adviseServer.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    
    public ResultMap getMentor(Integer id) {
        try {
            return adviseServer.get(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap downloadAdviseByInfo(String info) {
        FileOutputStream fos = null;
        String csvPath = Config.TEMP_FILE_PATH + info + "导师表.csv";
        ResultMap message = null;

        try {
            File outputFile = new File(csvPath);
            if(outputFile.exists()) {
                outputFile.delete();
                outputFile = new File(csvPath);
            }
            fos = new FileOutputStream(outputFile, true);
            //Solve for Chinese Character errors while using excel:
            fos.write(new byte[]{(byte)0xEF,(byte)0xBB,(byte)0xBF});

            Field[] titleList = AdviseView.class.getDeclaredFields();
            List<AdviseView> adviseViewList = (List<AdviseView>) adviseServer.list(1, Integer.MAX_VALUE, "", "asc",
                    "student_id", "", "", info).getData();

            StringBuilder exportInfoSb = new StringBuilder();
            for (int i = 0; i < titleList.length; i++) {
                if (i != 0) exportInfoSb.append(",");
                exportInfoSb.append("\"" + titleList[i].getName() + "\"");
            }
            exportInfoSb.append("\n");
            for (int i = 0; i < adviseViewList.size(); i++) {
                Map<String, String> tvMap = BeanUtils.describe(adviseViewList.get(i));
                for (int j = 0; j < titleList.length; j++) {
                    if (j != 0) exportInfoSb.append(",");
                    exportInfoSb.append("\"" + tvMap.get(titleList[j].getName()) + "\"");
                }
                exportInfoSb.append("\n");
            }
            exportInfoSb.append("总计" + adviseViewList.size() + "条数据。");
            fos.write(exportInfoSb.toString().getBytes("UTF-8"));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        logger.info("Filing task for advise has completed.");
        ResultMap restMessage = new ResultMap();
        restMessage.setCode(SystemConst.SUCCESS.getCode());
        restMessage.setData(info + "导师表.csv");
        return restMessage;
    }

    public ResultMap advisorList(int start, int length, String search, String order, String orderColumn, String status) {
        return adviseServer.advisorList(start, length, search, order, orderColumn, status);
    }

    public ResultMap removeAdvisor(String userId) {
        return adviseServer.removeAdvisor(userId);
    }

    public ResultMap addAdvisor(String userId) {
        return adviseServer.addAdvisor(userId);
    }
}
