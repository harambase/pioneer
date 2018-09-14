package com.harambase.pioneer.service;

import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.helper.Name;
import com.harambase.pioneer.server.pojo.base.Advise;
import com.harambase.pioneer.server.pojo.dto.AdviseReportOnly;
import com.harambase.pioneer.server.pojo.view.AdviseView;
import com.harambase.pioneer.server.service.AdviseServerService;
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

    private final AdviseServerService adviseServerService;

    @Autowired
    public AdviseService(AdviseServerService adviseServerService) {
        this.adviseServerService = adviseServerService;
    }


    public ResultMap advisingList(int start, int length, String search, String order, String orderColumn, String studentId, String facultyId, String info) {
        try {
            return adviseServerService.list(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderColumn, studentId, facultyId, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap updateAdvise(Integer id, Advise advise) {
        try {
            return adviseServerService.update(id, advise);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap assignMentor(Advise advise) {
        try {
            return adviseServerService.create(advise);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap removeMentor(Integer id) {
        try {
            return adviseServerService.remove(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap getMentor(Integer id) {
        try {
            return adviseServerService.retrieve(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap downloadAdviseByInfo(String info) {
        FileOutputStream fos = null;
        String csvPath = Config.serverPath + info + "导师表.csv";

        try {
            File outputFile = new File(csvPath);
            if (outputFile.exists()) {
                outputFile.delete();
                outputFile = new File(csvPath);
            }
            fos = new FileOutputStream(outputFile, true);
            //Solve for Chinese Character errors while using excel:
            fos.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});

            Field[] titleList = AdviseReportOnly.class.getDeclaredFields();
            List<AdviseView> adviseViewList = (List<AdviseView>) adviseServerService.list("1", String.valueOf(Integer.MAX_VALUE), "", "asc",
                    "fname", "", "", info).getData();

            StringBuilder exportInfoSb = new StringBuilder();
            for (int i = 0; i < titleList.length; i++) {
                if (i != 0) exportInfoSb.append(",");
                Name name = titleList[i].getAnnotation(Name.class);
                exportInfoSb.append("\"" + name.value() + "\"");
            }
            exportInfoSb.append("\n");
            for (int i = 0; i < adviseViewList.size(); i++) {
                Map<String, String> tvMap = BeanUtils.describe(adviseViewList.get(i));
                exportInfoSb.append((i + 1) + ",");
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
        return adviseServerService.advisorList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderColumn, status);
    }

    public ResultMap removeAdvisor(String userId) {
        return adviseServerService.removeAdvisor(userId);
    }

    public ResultMap addAdvisor(String userId) {
        return adviseServerService.addAdvisor(userId);
    }

    public ResultMap getAdvisor(String userId) {
        return adviseServerService.getAdvisor(userId);
    }

    public ResultMap getAdvisorByStudentId(String studentId) {
        return adviseServerService.getAdviseViewByStudentId(studentId);
    }
}
