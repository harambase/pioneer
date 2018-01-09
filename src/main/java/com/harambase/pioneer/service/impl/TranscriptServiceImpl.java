package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.pojo.Transcript;
import com.harambase.pioneer.server.PersonServer;
import com.harambase.pioneer.server.StudentServer;
import com.harambase.pioneer.server.TranscriptServer;
import com.harambase.pioneer.service.TranscriptService;
import com.harambase.support.document.jlr.JLRConverter;
import com.harambase.support.document.jlr.JLRGenerator;
import com.harambase.support.util.ReportUtil;
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
        return transcriptServer.updateByPrimaryKey(IP, PORT, id, transcript);
    }

    @Override
    public HaramMessage transcriptList(int start, int length, String search, String order, String orderColumn, String studentId, String crn) {
        return transcriptServer.transcriptList(IP, PORT, start, length, search, order, orderColumn, studentId, crn);
    }

    @Override
    public HaramMessage studentTranscriptReport(String studentid) {

        /* VTL Syntax information:
         * http://velocity.apache.org/engine/1.7/user-guide.html
         */

        File projectDirectory = new File("");
        File workingDirectory = new File(projectDirectory.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "tex");

        File template = new File(workingDirectory.getAbsolutePath() + File.separator + "transcriptTemplate.tex");

        String dirPath = "/static/upload/document/studentReport/" + studentid + "/";
        String inputTexPath = Config.serverPath + dirPath + studentid + ".tex";
        File reportTex = new File(inputTexPath);

        try {
            JLRConverter converter = new JLRConverter(workingDirectory);

            //学生信息TITLE
            LinkedHashMap studentInfoMap = (LinkedHashMap) personServer.get(IP, PORT, studentid).getData();
            converter.replace("sname", studentInfoMap.get("lastName") + ", " + studentInfoMap.get("firstName"));
            converter.replace("studentId", studentInfoMap.get("userId"));
            converter.replace("info", ReportUtil.infoConverter((String) studentInfoMap.get("info")));
            converter.replace("address", studentInfoMap.get("address"));

            //成绩详情
            List<LinkedHashMap> transcriptList = (List<LinkedHashMap>) transcriptServer.transcriptList(IP, PORT, 1, Integer.MAX_VALUE, "", "", "", studentid, "").getData();
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
            LinkedHashMap studentViewMap = (LinkedHashMap) studentServer.transcriptDetail(IP, PORT, studentid).getData();
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
            File projectDir = new File(Config.serverPath + dirPath);

            //PDF生成
            JLRGenerator pdfGen = new JLRGenerator();
            pdfGen.generate(reportTex, projectDir, projectDir);

            logger.info("Reporting task for " + studentid + " has completed.");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        HaramMessage restMessage = new HaramMessage();
        restMessage.setCode(FlagDict.SUCCESS.getV());
        restMessage.setData(dirPath + studentid + ".pdf");
        return restMessage;
    }
}
