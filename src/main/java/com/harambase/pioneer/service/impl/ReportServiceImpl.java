package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.server.PersonServer;
import com.harambase.pioneer.server.StudentServer;
import com.harambase.pioneer.server.TranscriptServer;
import com.harambase.pioneer.service.ReportService;
import com.harambase.support.document.jlr.JLRConverter;
import com.harambase.support.document.jlr.JLRGenerator;
import com.harambase.support.document.jlr.JLROpener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.LinkedHashMap;

@Service
public class ReportServiceImpl implements ReportService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final TranscriptServer transcriptServer;
    private final PersonServer personServer;
    private final StudentServer studentServer;

    @Autowired
    public ReportServiceImpl(TranscriptServer transcriptServer, PersonServer personServer, StudentServer studentServer) {
        this.transcriptServer = transcriptServer;
        this.studentServer = studentServer;
        this.personServer = personServer;
    }

    @Override
    public HaramMessage studentTranscriptReport(String studentid) {

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
            converter.replace("info", studentInfoMap.get("info"));
            converter.replace("address", studentInfoMap.get("address"));

            //学分TOTAL:
            LinkedHashMap studentViewMap = (LinkedHashMap) studentServer.transcriptDetail(IP, PORT, studentid).getData();
            int complete = (Integer) studentViewMap.get("complete");
            int progress = (Integer) studentViewMap.get("progress");
            int incomplete = (Integer) studentViewMap.get("progress");
            int total = complete + progress + incomplete;
            //todo: quality points caculation
            int points = complete * 4;
            double gpa = (double) points / (double) (complete + incomplete);

            converter.replace("total", total);
            converter.replace("complete", complete);
            converter.replace("incomplete", incomplete);
            converter.replace("points", points);
            converter.replace("gpa", gpa);

            //成绩详情
//            List<LinkedHashMap> transcriptList = (List<LinkedHashMap>) transcriptServer.transcriptList(IP, PORT, 1, Integer.MAX_VALUE, "", "", "", studentid, "").getData();
//            Map<String, List<List<Object>>> transcripts = new HashMap<>();
//            Set<String> infoSet = new HashSet<>();
//            for (LinkedHashMap transcriptView : transcriptList) {
//                infoSet.add((String)transcriptView.get("info"));
//            }
//
//            for(String info: infoSet){
//                List<List<Object>> transcriptInfoList = new ArrayList<>();
//                for (LinkedHashMap transcriptMap : transcriptList) {
//                    if(transcriptMap.get("info").equals(info)) {
//                        List<Object> transcriptDetail = new ArrayList<>();
//
//                        Transcript transcript = new Transcript();
//                        BeanUtils.populate(transcript, transcriptMap);
//
//                        transcriptDetail.add(transcript.getCname() + "-");//todo:course abbr-course level
//                        transcriptDetail.add(transcript.getCname());
//                        transcriptDetail.add(transcript.getCredits());
//                        transcriptDetail.add(transcript.getCredits());
//                        transcriptDetail.add(transcript.getGrade());
//                        transcriptDetail.add(points);//todo: quality points calculator
//
//                        transcriptInfoList.add(transcriptDetail);
//
//                    }
//                }
//                transcripts.put(info, transcriptInfoList);
//            }

//            converter.replace("infoSet", infoSet);
//            converter.replace("transcripts", transcripts.get("2017-01"));

            //输出
            converter.parse(template, reportTex);
            File projectDir = new File(Config.serverPath + dirPath);

            //PDF生成
            JLRGenerator pdfGen = new JLRGenerator();
            pdfGen.generate(reportTex, projectDir, projectDir);

//          //PDF自动打开
//            File pdf1 = pdfGen.getPDF();
//            JLROpener.open(pdf1);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        HaramMessage restMessage = new HaramMessage();
        restMessage.setCode(FlagDict.SUCCESS.getV());
        restMessage.setData(dirPath + studentid + ".pdf");
        return restMessage;
    }
}
