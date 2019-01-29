package com.harambase.pioneer.service;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.helper.Name;
import com.harambase.pioneer.server.pojo.base.Feedback;
import com.harambase.pioneer.server.pojo.dto.FeedbackReportOnly;
import com.harambase.pioneer.server.pojo.view.FeedbackView;
import com.harambase.pioneer.server.service.FeedbackServerService;
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
public class FeedbackService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FeedbackServerService feedbackServerService;

    @Autowired
    public FeedbackService(FeedbackServerService feedbackServerService) {
        this.feedbackServerService = feedbackServerService;
    }

    public ResultMap removeFeedback(Integer id) {
        try {
            return feedbackServerService.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap updateFeedback(Integer id, Feedback feedback) {
        try {
            return feedbackServerService.update(id, feedback);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap get(Integer id) {
        try {
            return feedbackServerService.retrieve(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap feedbackList(Integer start, Integer length, String search, String order, String orderCol, String facultyId, String info) {
        try {
            return feedbackServerService.list(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, facultyId, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap decryptList(Integer start, Integer length, String search, String order, String orderCol, String facultyId, String password, String info) {
        try {
            return feedbackServerService.decryptList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, facultyId, password, info);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap downloadFeedbackByInfo(String info, String password) {
        FileOutputStream fos = null;
        String csvPath = Config.serverPath + info + "年度评价表.csv";

        try {
            File outputFile = new File(csvPath);
            if (outputFile.exists()) {
                outputFile.delete();
                outputFile = new File(csvPath);
            }
            fos = new FileOutputStream(outputFile, true);
            //Solve for Chinese Character errors while using excel:
            fos.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});

            Field[] titleList = FeedbackReportOnly.class.getDeclaredFields();


            List<FeedbackView> feedbackViewList = (List<FeedbackView>) feedbackServerService.decryptList("1", String.valueOf(Integer.MAX_VALUE), "", "asc",
                    "fname", "", password, info).getData();

            StringBuilder exportInfoSb = new StringBuilder();
            for (int i = 0; i < titleList.length; i++) {
                if (i != 0) exportInfoSb.append(",");
                Name name = titleList[i].getAnnotation(Name.class);
                exportInfoSb.append("\"" + name.value() + "\"");
            }
            exportInfoSb.append("\n");
            for (int i = 0; i < feedbackViewList.size(); i++) {
                Map<String, String> tvMap = BeanUtils.describe(feedbackViewList.get(i));
                exportInfoSb.append((i + 1) + ",");
                for (int j = 0; j < titleList.length; j++) {
                    if (j != 0) exportInfoSb.append(",");
                    exportInfoSb.append("\"" + tvMap.get(titleList[j].getName()) + "\"");
                }
                exportInfoSb.append("\n");
            }

            exportInfoSb.append("总计" + feedbackViewList.size() + "条数据。");
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

        logger.info("Filing task for feedback has completed.");
        ResultMap restMessage = new ResultMap();
        restMessage.setCode(SystemConst.SUCCESS.getCode());
        restMessage.setData(info + "年度评价表.csv");
        return restMessage;
    }

    public ResultMap generateAll(String info, String password, String opId) {
        try {
            return feedbackServerService.generateAll(info, password, opId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap generateOne(String info, String userId, String password, String opId) {
        try {
            return feedbackServerService.generateOne(info, userId, password, opId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap find(String facultyId) {
        try {
            return feedbackServerService.find(facultyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap updateFeedbackOther(Integer id, Feedback feedback) {
        try {
            return feedbackServerService.updateFeedbackOther(id, feedback);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap encrypt(String info, String password, String oldPassword, String opId) {
        try {
            return feedbackServerService.encrypt(info, password, oldPassword, opId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    public ResultMap validate(String info, String password) {
        try {
            return feedbackServerService.validate(info, password);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
