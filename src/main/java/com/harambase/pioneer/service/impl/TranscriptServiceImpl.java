package com.harambase.pioneer.service.impl;

import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.common.constant.FlagDict;
import com.harambase.pioneer.pojo.Transcript;
import com.harambase.pioneer.server.PersonServer;
import com.harambase.pioneer.server.StudentServer;
import com.harambase.pioneer.server.TranscriptServer;
import com.harambase.pioneer.service.TranscriptService;
import com.harambase.pioneer.common.support.document.jlr.JLRConverter;
import com.harambase.pioneer.common.support.document.jlr.JLRGenerator;
import com.harambase.pioneer.common.support.util.ReportUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class TranscriptServiceImpl implements TranscriptService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final TranscriptServer transcriptServer;
    private final PersonServer personServer;
    private final StudentServer studentServer;

    @Autowired
    public TranscriptServiceImpl(TranscriptServer transcriptServer, PersonServer personServer, StudentServer studentServer) {
        this.transcriptServer = transcriptServer;
        this.studentServer = studentServer;
        this.personServer = personServer;
    }

    @Override
    public HaramMessage updateGrade(int id, Transcript transcript) {
        try {
            return transcriptServer.updateByPrimaryKey(IP, PORT, id, transcript);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage transcriptList(int start, int length, String search, String order, String orderColumn, String studentId, String crn,
                                       String info, String complete) {
        try {
            return transcriptServer.transcriptList(IP, PORT, start, length, search, order, orderColumn, studentId, crn, info, complete);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public HaramMessage studentTranscriptReport(String studentId) {

        /* VTL Syntax information:
         * http://velocity.apache.org/engine/1.7/user-guide.html
         */
        File workingDirectory = new File(Config.serverPath + "/tex");

        File template = new File(workingDirectory.getAbsolutePath() + "/transcriptTemplate.tex");

        String dirPath = "/document/studentReport/" + studentId + "/";
        String inputTexPath = Config.TEMP_FILE_PATH + dirPath + studentId + ".tex";
        File reportTex = new File(inputTexPath);

        try {
            JLRConverter converter = new JLRConverter(workingDirectory);

            //学生信息TITLE
            LinkedHashMap studentInfoMap = (LinkedHashMap) personServer.get(IP, PORT, studentId).getData();
            converter.replace("sname", studentInfoMap.get("lastName") + ", " + studentInfoMap.get("firstName"));
            converter.replace("studentId", studentInfoMap.get("userId"));
            converter.replace("info", ReportUtil.infoConverter((String) studentInfoMap.get("info")));
            converter.replace("address", studentInfoMap.get("address"));

            //成绩详情
            List<LinkedHashMap> transcriptList = (List<LinkedHashMap>) transcriptServer.transcriptList(IP, PORT, 1, Integer.MAX_VALUE, "", "", "", studentId, "", "", "").getData();
            Map<String, List<List<Object>>> transcripts = new HashMap<>();
            Set<String> infoSet = new HashSet<>();
            Map<String, String> infoNameSet = new HashMap<>();

            for (LinkedHashMap transcriptView : transcriptList) {
                infoSet.add((String) transcriptView.get("info"));
            }

            int qualityPoints = 0;

            for (String info : infoSet) {
                List<List<Object>> transcriptInfoList = new ArrayList<>();
                for (LinkedHashMap transcriptMap : transcriptList) {
                    if (transcriptMap.get("info").equals(info)) {
                        List<Object> transcriptDetail = new ArrayList<>();

                        Transcript transcript = new Transcript();
                        BeanUtils.populate(transcript, transcriptMap);

                        transcriptDetail.add(transcript.getCrn());
                        transcriptDetail.add(transcript.getCname());
                        transcriptDetail.add(transcript.getCredits());
                        if (transcript.getComplete().equals("1"))
                            transcriptDetail.add(transcript.getCredits());
                        else
                            transcriptDetail.add("0.0");
                        String grade = transcript.getGrade();
                        int points = ReportUtil.qualityPointsCalculator(transcript.getCredits(), grade);
                        qualityPoints += points;

                        transcriptDetail.add(grade);
                        transcriptDetail.add(points);

                        transcriptInfoList.add(transcriptDetail);

                    }
                }
                transcripts.put(info, transcriptInfoList);
                infoNameSet.put(info, ReportUtil.infoConverter(info));
            }

            converter.replace("infoSet", infoSet);
            converter.replace("infoNameSet", infoNameSet);
            converter.replace("transcriptList", transcripts);

            //学分TOTAL:
            LinkedHashMap studentViewMap = (LinkedHashMap) studentServer.transcriptDetail(IP, PORT, studentId).getData();
            int complete = (Integer) studentViewMap.get("complete");
            int progress = (Integer) studentViewMap.get("progress");
            int incomplete = (Integer) studentViewMap.get("incomplete");
            int total = complete + progress + incomplete;

            double gpa = (double) qualityPoints / (double) (complete + incomplete);
            DecimalFormat df = new DecimalFormat("######0.00");

            converter.replace("total", total);
            converter.replace("complete", complete);
            converter.replace("points", qualityPoints);
            converter.replace("gpa", df.format(gpa));

            //输出
            converter.parse(template, reportTex);
            File projectDir = new File(Config.TEMP_FILE_PATH + dirPath);

            //PDF生成
            JLRGenerator pdfGen = new JLRGenerator();
            pdfGen.generate(reportTex, projectDir, projectDir);

            logger.info("Reporting task for " + studentId + " has completed.");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        HaramMessage restMessage = new HaramMessage();
        restMessage.setCode(FlagDict.SUCCESS.getV());
        restMessage.setData(dirPath + studentId + ".pdf");
        return restMessage;
    }
}
