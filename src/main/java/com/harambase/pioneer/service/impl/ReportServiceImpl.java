package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.common.constant.FlagDict;
import com.harambase.support.util.DateUtil;
import com.harambase.pioneer.server.PersonServer;
import com.harambase.pioneer.server.StudentServer;
import com.harambase.pioneer.server.TranscriptServer;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.pojo.Student;
import com.harambase.pioneer.pojo.Transcript;
import com.harambase.pioneer.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final TranscriptServer transcriptServer;
    private final PersonServer personServer;
    private final StudentServer studentServer;

    @Autowired
    public ReportServiceImpl(TranscriptServer transcriptServer, PersonServer personServer, StudentServer studentServer){
        this.transcriptServer = transcriptServer;
        this.studentServer = studentServer;
        this.personServer = personServer;
    }

    @Override
    public HaramMessage studentTranscriptReport(String studentid){

        FileOutputStream fos = null;
        String filePath = Config.TEMP_FILE_PATH + studentid + ".pdf";
        try {


            StringBuilder exportInfoSb = new StringBuilder();

            List<Transcript> transcriptViewList = new ArrayList<>();//transcriptServer.studentTranscripts(studentid);
            Person student = new Person();//personServer.selectByUserId(studentid);
            Student studentView = new Student();//studentServer.creditsDetail(studentid);

            if(transcriptViewList != null && transcriptViewList.size()>0) {
                exportInfoSb.append("先锋学校学生成绩单\n")
                            .append("生成时间:" + DateUtil.DateToStr(new Date())+"\n")
                            .append("---------------------------------------------\n")
                            .append("学生信息")
                            .append("姓名：" + student.getLastname() + ", " + student.getFirstname() + "\n")
                            .append("生日：" + student.getBirthday() + "\n")
                            .append("----------------------------------------------\n")
                            .append("成绩信息");

                Set<String> infoSet = new HashSet<>();
                List<Transcript> usedTranscript;
                for (Transcript transcriptView: transcriptViewList){
                    infoSet.add(transcriptView.getInfo());
                }
                String semeterInfo = "";
                for (String info : infoSet) {
                    usedTranscript = new ArrayList<>();
                    semeterInfo += "学期：" + info + "\n";
                    semeterInfo += "学生状态：\n";
                    semeterInfo += "课程,课程名,教师,成绩,学分,总学时\n";

                    for(Transcript transcriptView: transcriptViewList){
                        if(transcriptView.getInfo().equals(info)){
                            semeterInfo += transcriptView.getCrn() + ","
                                    + transcriptView.getCoursename() + ","
                                    + transcriptView.getFname() + ","
                                    + transcriptView.getGrade() + ","
                                    + transcriptView.getCredits() + ","
                                    + "  " + "\n";

                        }
                        usedTranscript.add(transcriptView);
                    }

                    transcriptViewList.removeAll(usedTranscript);
                }
                exportInfoSb.append(semeterInfo);
//                fos.write(exportInfoSb.toString().getBytes("UTF-8"));
                fos = new FileOutputStream(new File(filePath), true);

            }

        } catch (Exception e) {
            e.printStackTrace();
            HaramMessage restMessage = new HaramMessage();
            restMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            return restMessage;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        HaramMessage restMessage = new HaramMessage();
        restMessage.setCode(FlagDict.SUCCESS.getV());
        restMessage.setData(filePath);
        return restMessage;
    }
}
