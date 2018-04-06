package com.harambase.pioneer.service;

import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.document.jlr.JLRConverter;
import com.harambase.pioneer.common.support.document.jlr.JLRGenerator;
import com.harambase.pioneer.common.support.util.ReportUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.PersonServer;
import com.harambase.pioneer.server.StudentServer;
import com.harambase.pioneer.server.TranscriptServer;
import com.harambase.pioneer.server.pojo.base.Person;
import com.harambase.pioneer.server.pojo.base.Transcript;
import com.harambase.pioneer.server.pojo.view.TranscriptView;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class TranscriptService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TranscriptServer transcriptServer;
    private final PersonServer personServer;
    private final StudentServer studentServer;

    @Autowired
    public TranscriptService(TranscriptServer transcriptServer, PersonServer personServer, StudentServer studentServer) {
        this.transcriptServer = transcriptServer;
        this.studentServer = studentServer;
        this.personServer = personServer;
    }


    public ResultMap updateGrade(int id, Transcript transcript) {
        try {
            return transcriptServer.update(id, transcript);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap transcriptList(int start, int length, String search, String order, String orderColumn, String studentId, String crn,
                                    String info, String complete) {
        try {
            return transcriptServer.list(start, length, search, order, orderColumn, studentId, crn, info, complete);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap studentTranscriptReport(String studentId) {

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
            Person student = (Person) personServer.get(studentId).getData();
            converter.replace("sname", student.getLastName() + ", " + student.getFirstName());
            converter.replace("studentId", student.getUserId());
            converter.replace("info", ReportUtil.infoConverter(student.getInfo()));
            converter.replace("address", student.getAddress());

            //成绩详情
            List<TranscriptView> transcriptList = (List<TranscriptView>) transcriptServer.list(1, Integer.MAX_VALUE, "", "", "crn",
                    studentId, "", "", "").getData();
            Map<String, List<List<Object>>> transcripts = new HashMap<>();
            Set<String> infoSet = new HashSet<>();
            Map<String, String> infoNameSet = new HashMap<>();

            for (TranscriptView transcriptView : transcriptList) {
                infoSet.add(transcriptView.getInfo());
            }

            int qualityPoints = 0;

            for (String info : infoSet) {
                List<List<Object>> transcriptInfoList = new ArrayList<>();
                for (TranscriptView transcript : transcriptList) {
                    if (transcript.getInfo().equals(info)) {
                        List<Object> transcriptDetail = new ArrayList<>();

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
            LinkedHashMap studentViewMap = (LinkedHashMap) studentServer.getTranscriptDetail(studentId).getData();
            int complete = (int) studentViewMap.get("complete");
            int progress = (int) studentViewMap.get("progress");
            int incomplete = (int) studentViewMap.get("incomplete");
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

        ResultMap restMessage = new ResultMap();
        restMessage.setCode(SystemConst.SUCCESS.getCode());
        restMessage.setData(dirPath + studentId + ".pdf");
        return restMessage;
    }

    public ResultMap allTranscripts(String info) {
        FileOutputStream fos = null;
        String csvPath = Config.TEMP_FILE_PATH + "all_transcript_report.csv";
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

            Field[] titleList = TranscriptView.class.getDeclaredFields();
            List<TranscriptView> transcriptViewList = (List<TranscriptView>) transcriptServer.list(1, Integer.MAX_VALUE, "", "asc",
                    "crn", "", "", info, "").getData();

            StringBuilder exportInfoSb = new StringBuilder();
            for (int i = 0; i < titleList.length; i++) {
                if (i != 0) exportInfoSb.append(",");
                exportInfoSb.append("\"" + titleList[i].getName() + "\"");
            }
            exportInfoSb.append("\n");
            for (int i = 0; i < transcriptViewList.size(); i++) {
                Map<String, String> tvMap = BeanUtils.describe(transcriptViewList.get(i));
                for (int j = 0; j < titleList.length; j++) {
                    if (j != 0) exportInfoSb.append(",");
                    exportInfoSb.append("\"" + tvMap.get(titleList[j].getName()) + "\"");
                }
                exportInfoSb.append("\n");
            }
            exportInfoSb.append("total records:," + transcriptViewList.size());
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

        logger.info("Reporting task for all students has completed.");
        ResultMap restMessage = new ResultMap();
        restMessage.setCode(SystemConst.SUCCESS.getCode());
        restMessage.setData("all_transcript_report.csv");
        return restMessage;
    }
}
