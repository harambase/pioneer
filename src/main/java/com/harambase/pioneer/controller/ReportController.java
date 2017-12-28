package com.harambase.pioneer.controller;

import com.harambase.common.HaramMessage;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.service.ReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;


@RestController
@RequestMapping(value = "/report")
@CrossOrigin
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @RequestMapping(value = "/{studentId}/transcript", method = RequestMethod.GET)
    public void studentTranscriptReport(@PathVariable(value = "studentId") String studentId, HttpServletResponse response) {
        HaramMessage haramMessage = reportService.studentTranscriptReport(studentId);
        ReportControllerInner.download(studentId + "_transcript_report.pdf", haramMessage, response);
    }

    private static class ReportControllerInner {
        private static void download(String fileName, HaramMessage haramMessage, HttpServletResponse response) {
            String filePath = (String) haramMessage.getData();
            File file = null;
            if (StringUtils.isNotEmpty(filePath)) {
                file = new File(filePath);
            }
            if (haramMessage.getCode() != FlagDict.SUCCESS.getV()) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = null;
                try {
                    out = response.getWriter();
                    out.append("下载失败");


                } catch (IOException e2) {
                    e2.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                    if (file.exists())
                        file.delete();
                    return;
                }
            }
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName);//uri.substring(uri.lastIndexOf("/"), uri.length()) + ".csv\"");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            OutputStream outputStream = null;
            try {

                FileInputStream fileInputStream = new FileInputStream(filePath);
                outputStream = new BufferedOutputStream(response.getOutputStream());
                byte[] bytes = new byte[2048];
                int length;
                while ((length = fileInputStream.read(bytes)) > 0) {
                    outputStream.write(bytes, 0, length);
                }
                fileInputStream.close();
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if (file.exists())
                    file.delete();
            }

        }
    }
//    public void formPersonCourseSchedule(String id, String semeBelong){
//        try {
//            String seme = "";
//            if(semeBelong.charAt(5) == '1'){
//                seme = "Spring";
//            }
//            else if(semeBelong.charAt(5) == '2'){
//                seme = "Summer";
//            }
//            else if(semeBelong.charAt(5) == '3'){
//                seme = "Fall";
//            }
//
//            String semeInfo = semeBelong + " (which refers to "
//                    + semeBelong.substring(0,4) + " " + seme + " semester)";
//
//            Person per = new StudentBase();
//            per.setId(id);
//            String type = SRSFunctions.findATypeOfAPerson(per);
//
//            if(type.equals("S")){
//                File fl = new File(id +"_"+semeBelong+"_Course_Schedule.csv");
//                fl.createNewFile();
//                List<CourseBase> stuCouList = SRSFunctions.makeCoursesList(id);
//                int index = 0, i = 4, j = 0;
//                String[] info = new String[500];
//                StudentBase stu = SRSFunctions.makeAStudent(id);
//
//                info[0] = "StudentBase ID,StudentBase Name,StudentBase Earned Credits";
//                info[1] = id + "," + stu.getName() + "," + stu.getCredits();
//                info[2] = "Inquire For " + semeInfo + " courses schedule.";
//                info[3] = "CourseBase crn,CourseBase Name,CourseBase Credits,CourseBase Grade,CourseBase Start Date, CourseBase End Date, CourseBase Start Time, CourseBase End Time, "
//                        + "CourseBase schedule,instructor,Notification";
//                while(stuCouList.listIterator(index).hasNext()){
//                    String crn = stuCouList.get(index).getCrn();
//                    String semeCou = crn.substring(0, 6);
//                    if(semeCou.equals(semeBelong)){
//                        Transcript tran = SRSFunctions.makeATranscript(id, crn);
//                        CourseBase cou = stuCouList.get(index);
//                        if(tran.getGrade().equals("W")){
//                            info[i] = crn + "," + cou.getName() + "," + cou.getCredits() + "," + tran.getGrade()
//                                    + "," + cou.getStartS() +"," + cou.getStartE() + "," + cou.getStartTS()
//                                    + "," + cou.getStartTE() + "," + cou.getDay()+ "," + SRSFunctions.makeAProfessor(cou.getTeacherID()).getName()
//                                    + ",You have dropped this course.";
//                        }
//                        else if (tran.getGrade().equals("F")){
//                            info[i] =  crn + "," + cou.getName() + "," + cou.getCredits() + "," + tran.getGrade()
//                                    + "," + cou.getStartS() +"," + cou.getStartE() + "," + cou.getStartTS()
//                                    + "," + cou.getStartTE() + "," + cou.getDay()+ "," + SRSFunctions.makeAProfessor(cou.getTeacherID()).getName()
//                                    + ",You have failed this course.";
//                        }
//                        else{
//                            info[i] = crn + "," + cou.getName() + "," + cou.getCredits() + "," + tran.getGrade()
//                                    + "," + cou.getStartS() +"," + cou.getStartE() + "," + cou.getStartTS()
//                                    + "," + cou.getStartTE() + "," + cou.getDay()+ "," + SRSFunctions.makeAProfessor(cou.getTeacherID()).getName();
//                        }
//                        i++;
//                        index++;
//                    }
//                }
//                PrintWriter pw = new PrintWriter(fl);
//                while(j<i){
//                    pw.println(info[j]);
//                    j++;
//                }
//                pw.close();
//            }
//            else if(type.equals("T")){
//                File fl = new File(id +"_"+semeBelong+"_Course_Schedule.csv");
//                fl.createNewFile();
//                List<CourseBase> stuCouList = SRSFunctions.makeCoursesList(id);
//                int index = 0, i = 4, j = 0;
//                String[] info = new String[500];
//                Professor pro = SRSFunctions.makeAProfessor(id);
//
//                info[0] = "Professor ID,Professor Name";
//                info[1] = id + "," + pro.getName();
//                info[2] = "Inquire For " + semeInfo + " courses schedule.";
//                info[3] = "CourseBase crn,CourseBase Name,CourseBase Credits,CourseBase Grade,CourseBase Start Date, CourseBase End Date, CourseBase Start Time, CourseBase End Time, "
//                        + "CourseBase schedule,instructor,Notification";
//                while(stuCouList.listIterator(index).hasNext()){
//                    String crn = stuCouList.get(index).getCrn();
//                    String semeCou = crn.substring(0, 6);
//                    if(semeCou.equals(semeBelong)){
//                        CourseBase cou = stuCouList.get(index);
//                        info[i] = crn + "," + cou.getName() + "," + cou.getCredits() + ","
//                                + "," + cou.getStartS() +"," + cou.getStartE() + "," + cou.getStartTS()
//                                + "," + cou.getStartTE() + "," + cou.getDay()+ "," + SRSFunctions.makeAProfessor(cou.getTeacherID()).getName();
//                        i++;
//                        index++;
//                    }
//                }
//                PrintWriter pw = new PrintWriter(fl);
//                while(j<i){
//                    pw.println(info[j]);
//                    j++;
//                }
//                pw.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public void formCourseReport(String crn){
//        CourseBase cou = SRSFunctions.makeACourse(crn);
//        List<StudentBase> couStuList = SRSFunctions.makeStudentsList(crn);
//        Professor pro = SRSFunctions.makeAProfessor(cou.getTeacherID());
//        File fl = new File("Course_" + cou.getCrn() + "_Report.csv");
//        double pass = 0, notPass = 0, drop = 0,total = couStuList.size();
//        int  i = 6 ,j = 0;
//        double passRate = 0, failRate = 0;
//        String[] info = new String[500];
//        int index = 0;
//        info[0] = "Inquire For " + crn +  " (which name is " +cou.getName() + " ) courses Report.";
//        info[1] = "Instructor Id, instructor name";
//        info[2] = pro.getId() +","+ pro.getName();
//
//        info[5] = "StudentBase ID, StudentBase Name, StudentBase Grade, StudentBase Completion, Notification";
//
//        while(couStuList.listIterator(index).hasNext()){
//            StudentBase stu = couStuList.get(index);
//            Transcript tran = SRSFunctions.makeATranscript(stu.getId(), crn);
//            if(tran.getGrade().equals("W")){
//                info[i] = stu.getId() + "," + stu.getName() + "," + tran.getGrade() + "," + tran.getComplete() + ","
//                        + "Notice: This student has dropped this course! Not included in the total.";
//                drop++;
//                total--;
//            }
//            else if (tran.getGrade().equals("F")){
//                info[i] = stu.getId() + "," + stu.getName() + "," + tran.getGrade() + "," + tran.getComplete() + ","
//                        + "Notice: This student has dropp failed this course!";
//                notPass++;
//            }
//            else{
//                info[i] = stu.getId() + "," + stu.getName() + "," + tran.getGrade() + "," + tran.getComplete() + ",";
//                pass++;
//            }
//            i++;
//            index++;
//        }
//        if(total!= 0){
//            passRate = (pass/total);
//            failRate = (notPass/total);
//        }
//        info[3] = "CourseBase crn, CourseBase credit, CourseBase Capacity, CourseBase Total registered students";
//        info[4] = crn +"," + cou.getCredits() + "," + cou.getCapa() + "," + total;
//        info[i] = "General Grading about this CourseBase:," + "Passed StudentBase Amount = " + pass + ", Failed StudentBase Amount = "
//                + notPass + ", Dropped StudentBase = " + drop + ", Pass Rate = " + passRate + ", Failed Rate = " + failRate;
//        PrintWriter pw;
//        i++;
//        try {
//            pw = new PrintWriter(fl);
//            while(j < i){
//                pw.println(info[j]);
//                j++;
//            }
//            pw.close();
//        } catch (FileNotFoundException e) {
//        }
//    }
//    public void formStudentGeneralReport(String id){
//        try {
//            File fl = new File(id +"_General_Report.csv");
//            fl.createNewFile();
//            List<CourseBase> stuCouList = SRSFunctions.makeCoursesList(id);
//            int index = 0, i = 7, j = 0;
//            double pass = 0, notPass = 0, drop = 0, total = 0;
//            double passRate = 0, failRate = 0;
//            double credits = 0;
//            String[] info = new String[500];
//            StudentBase stu = SRSFunctions.makeAStudent(id);
//            info[0] = "StudentBase ID,StudentBase Name,StudentBase Earned Credits, Studeent faculty Advisor";
//            info[1] = id + "," + stu.getName() + "," + stu.getCredits() + "," + stu.getFa().getName();
//            info[2] = "More basic Info:,StudentBase tele, StudentBase qq, StudentBase dorm";
//            info[3] = " ," + stu.getTele() + "," + stu.getQq() + "," + stu.getDorm();
//            info[4] = "StudentBase Registering status: ,isActive Adding courses(AA/AD), isActive fully Dropping courses(AD/DD), is Active";
//            info[5] = " ," + stu.getActA() + "," + stu.getActD() + "," + stu.getActive();
//            info[6] = "Inquire for all the registered courses:";
//
//            String semeOld = "", semeBelong;
//            while(stuCouList.listIterator(index).hasNext()){
//                String crn = stuCouList.get(index).getCrn();
//                Transcript tran = SRSFunctions.makeATranscript(id, crn);
//                semeBelong = crn.substring(0,6).toString();
//                if(semeBelong.equals(semeOld)){
//                    if(tran.getGrade().equals("W")){
//                        info[i] = "," + crn + "," + stuCouList.get(index).getName()+","+stuCouList.get(index).getCredits()
//                                + "," + tran.getGrade()
//                                + "," + tran.getComplete() + ",The StudentBase has dropped this course. Not included in passing "
//                                + "rate calculation";
//                        drop++;
//                    }
//                    else if (tran.getGrade().equals("F")){
//                        info[i] = "," + crn + "," + stuCouList.get(index).getName()+","+stuCouList.get(index).getCredits()
//                                + "," + tran.getGrade()
//                                + "," + tran.getComplete() + ",The StudentBase has failed this course.";
//                        notPass++;
//                        total++;
//                        credits = Double.parseDouble(stuCouList.get(index).getCredits()) + credits;
//                    }
//                    else{
//                        info[i] ="," + crn + "," + stuCouList.get(index).getName() + "," + stuCouList.get(index).getCredits()
//                                +","+tran.getGrade() + "," + tran.getComplete();
//                        pass++;
//                        total++;
//                        credits = Double.parseDouble(stuCouList.get(index).getCredits()) + credits;
//                    }
//                    i++;
//                    index++;
//                    if(index == stuCouList.size()){
//                        if(total!= 0){
//                            passRate = (pass/total)*100;
//                            failRate = (notPass/total)*100;
//                            passRate = (double) (Math.round(passRate*100)/100.0);
//                            passRate = (double) (Math.round(failRate*100)/100.0);
//                        }
//                        info[i] = "Genreal grade information about this Semester:, Total Registered Courses Amount = " + total
//                                + ", Total Passed Courses Amount = " + pass  + ", Total Failed Courses Amount = " + notPass
//                                + ", Total Dropped Courses Amount = " + drop + ", Pass rate = " + passRate + "%, Failed rate = "
//                                + failRate + "%.";
//                        i++;
//                        info[i] = "StudentBase Registered Credits = " + credits + "StudentBase Earned Credits = " + stu.getCredits();
//                        i++;
//                        total = 0;
//                        pass = 0;
//                        notPass = 0;
//                        drop = 0;
//                        passRate = 0;
//                        failRate = 0;
//
//                    }
//                }
//                else if(!semeOld.isEmpty()){
//                    semeOld = semeBelong;
//                    if(total!= 0){
//                        passRate = (pass/total);
//                        failRate = (notPass/total);
//                        System.out.println("THere" + failRate + " " +notPass + " " +total);
//                    }
//                    info[i] = "Genreal grade information about this Semester:, Total Registered Courses Amount = " + total
//                            + ", Total Passed Courses Amount = " + pass  + ", Total Failed Courses Amount = " + notPass
//                            + ", Total Dropped Courses Amount = " + drop + ", Pass rate = " + passRate + ", Failed rate = "
//                            + failRate;
//                    total = 0;
//                    pass = 0;
//                    notPass = 0;
//                    drop = 0;
//                    passRate = 0;
//                    failRate = 0;
//                    i++;
//
//                    String seme = "";
//                    if(semeBelong.charAt(5) == '1'){
//                        seme = "Spring";
//                    }
//                    else if(semeBelong.charAt(5) == '2'){
//                        seme = "Summer";
//                    }
//                    else if(semeBelong.charAt(5) == '3'){
//                        seme = "Fall";
//                    }
//
//                    String semeInfo = semeBelong + " (which refers to "
//                            + semeBelong.substring(0,4) + " " + seme + " semester)";
//                    info[i] = "Inquire For " + semeInfo + " courses grade.";
//                    i++;
//                    info[i] = "Data Format: ,CourseBase crn,CourseBase Name,CourseBase Credits,CourseBase Grade,CourseBase Completion,Notification";
//                    i++;
//                }
//                else if(!semeBelong.isEmpty()){
//                    semeOld = semeBelong;
//                    String seme = "";
//                    if(semeBelong.charAt(5) == '1'){
//                        seme = "Spring";
//                    }
//                    else if(semeBelong.charAt(5) == '2'){
//                        seme = "Summer";
//                    }
//                    else if(semeBelong.charAt(5) == '3'){
//                        seme = "Fall";
//                    }
//
//                    String semeInfo = semeBelong + " (which refers to "
//                            + semeBelong.substring(0,4) + " " + seme + " semester)";
//                    info[i] = "Inquire For " + semeInfo + " courses grade.";
//                    i++;
//                    info[i] = "Data Format: ,CourseBase crn,CourseBase Name,CourseBase Credits,CourseBase Grade,CourseBase Completion,Notification";
//                    i++;
//                }
//            }
//            info[i] = "StudentBase Total Earned Credits = " + stu.getCredits();
//            i++;
//            PrintWriter pw = new PrintWriter(fl);
//            while(j<i){
//                pw.println(info[j]);
//                j++;
//            }
//            pw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public void formAdvReport(String id){
//        File fl = new File(id +"_Advising_Report.csv");
//        try {
//            fl.createNewFile();
//            Professor pro = SRSFunctions.makeAProfessor(id);
//            List<StudentBase> stuAdvList = SRSFunctions.makeAdvisingList(id);
//            int index = 0, i = 4, j = 0 , total = stuAdvList.size();
//            String[] info = new String[500];
//            info[0] = "Teacher ID, Teacher Name";
//            info[1] = pro.getId() + "," + pro.getName();
//            info[2] = "Inquire for all Advising Students:";
//            info[3] = "Basic Infomation:,StudentBase ID,StudentBase Name,StudentBase Earned Credits" +
//                    ", More detail Info:,StudentBase tele, StudentBase qq, StudentBase dorm" +
//                    ", StudentBase Registering status: ,isActive Adding courses(AA/AD), isActive fully Dropping courses(AD/DD), is Active";
//
//            while(stuAdvList.listIterator(index).hasNext()){
//                StudentBase stu = stuAdvList.get(index);
//                info[i] = "," + stu.getId() + "," + stu.getName() + "," + stu.getCredits()
//                        + "," + " ," + stu.getTele() + "," + stu.getQq() + "," + stu.getDorm()
//                        + "," + " ," + stu.getActA() + "," + stu.getActD() + "," + stu.getActive();
//                index++;
//                i++;
//            }
//            info[i + 1] = "Total Advising StudentBase Amount: " + total + " .";
//            PrintWriter pw = new PrintWriter(fl);
//            while(j < i){
//                pw.println(info[j]);
//                j++;
//            }
//            pw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//    public void formAllPersonsReport(){
//        File fl = new File("AllPersons.dat");
//        try {
//            Scanner sc = new Scanner(fl);
//            String[] info = new String[1000];
//            int i = 1, j = 0, total = 0;
//            info[0] = "Format:,Person's ID, Person's Name, Person's UserType, Person's Activation Status";
//            while(sc.hasNext()){
//                String line = sc.nextLine();
//                String[] split = new String[5];
//                split = line.split("\t");
//                info[i] = ",";
//                if(!split[0].equals("000000000")){
//                    for(int k = 0; k < split.length; k++){
//                        info[i] = info[i] + split[k] + ",";
//                    }
//                    i++;
//                    total++;
//                }
//            }
//            sc.close();
//            File fl2 = new File("H:\\All_Persons_Information.csv");
//            PrintWriter pw = new PrintWriter(fl2);
//            info[i] = "Total Amount of Persons = " + total;
//            i++;
//            while(j < i){
//                pw.println(info[j]);
//                j++;
//            }
//            pw.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//    public void formActiveStudentsReport(){
//        File fl = new File("Students.dat");
//        try {
//            Scanner sc = new Scanner(fl);
//            String[] info = new String[1000];
//            int i = 1, j = 0, total = 0;
//            info[0] = "Format:,StudentBase's ID, StudentBase's password, Students's name, StudentBase's Activation Status, StudentBase's Earned Credits";
//            while(sc.hasNext()){
//                String line = sc.nextLine();
//                String[] split = new String[5];
//                split = line.split("\t");
//                info[i] = ",";
//                if(!split[0].equals("001010001")){
//                    for(int k = 0; k < split.length; k++){
//                        info[i] = info[i] + split[k] + ",";
//                    }
//                    i++;
//                    total++;
//                }
//            }
//            sc.close();
//            PrintWriter pw = new PrintWriter("All_Active_Students.csv");
//            info[i] = "Total Amount of Students = " + total;
//            i++;
//            while(j < i){
//                pw.println(info[j]);
//                j++;
//            }
//            pw.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//    public void formActiveTeachersReport(){
//        File fl = new File("Teachers.dat");
//        try {
//            Scanner sc = new Scanner(fl);
//            String[] info = new String[1000];
//            int i = 1, j = 0, total = 0;
//            info[0] = "Format:,Teachers's ID, Teachers's password, Teachers's name, Teachers's Activation Status";
//            while(sc.hasNext()){
//                String line = sc.nextLine();
//                String[] split = new String[5];
//                split = line.split("\t");
//                info[i] = ",";
//                if(!split[0].equals("001010001")){
//                    for(int k = 0; k < split.length; k++){
//                        info[i] = info[i] + split[k] + ",";
//                    }
//                    i++;
//                    total++;
//                }
//            }
//            sc.close();
//            PrintWriter pw = new PrintWriter("All_Active_Teachers.csv");
//            info[i] = "Total Amount of Teachers = " + (total);
//            i++;
//            while(j < i){
//                pw.println(info[j]);
//                j++;
//            }
//            pw.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//    public void formDeactivatePersonsReport(){
//        File fl = new File("DeactivatePerson.dat");
//        try {
//            Scanner sc = new Scanner(fl);
//            String[] info = new String[1000];
//            int i = 1, j = 0, total = 0;
//            info[0] = "Format:,Person's ID, Person's Name, Person's UserType, Person's Activation Status";
//            while(sc.hasNext()){
//                String line = sc.nextLine();
//                String[] split = new String[5];
//                split = line.split("\t");
//                info[i] = ",";
//                if(!split[0].equals("000000000")){
//                    for(int k = 0; k < split.length; k++){
//                        info[i] = info[i] + split[k] + ",";
//                    }
//                    i++;
//                    total++;
//                }
//            }
//            sc.close();
//            PrintWriter pw = new PrintWriter("All_Dective_Person.csv");
//            info[i] = "Total Amount of Deactivate Persons = " + (total);
//            i++;
//            while(j < i){
//                pw.println(info[j]);
//                j++;
//            }
//            pw.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
}
