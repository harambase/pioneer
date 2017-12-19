package com.harambase.pioneer.service.impl;

import com.harambase.common.*;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.pojo.base.AdviseBase;
import com.harambase.pioneer.pojo.base.MessageWithBLOBs;
import com.harambase.pioneer.pojo.base.StudentBase;
import com.harambase.pioneer.pojo.base.TranscriptBase;
import com.harambase.support.util.DateUtil;
import com.harambase.support.util.IDUtil;
import com.harambase.support.util.PageUtil;
import com.harambase.support.util.Pinyin4jUtil;
import com.harambase.support.charts.StaticGexfGraph;
import com.harambase.pioneer.server.*;
import com.harambase.pioneer.pojo.*;
import com.harambase.pioneer.pojo.Course;
import com.harambase.pioneer.service.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PersonServiceImpl implements PersonService {

    private final PersonServer personServer;
    private final StudentServer studentServer;
    private final CourseServer courseServer;
    private final TranscriptServer transcriptServer;
    private final AdviseServer adviseServer;
    private final MessageServer messageServer;

    @Autowired
    public PersonServiceImpl(PersonServer personServer, StudentServer studentServer,
                             CourseServer courseServer, TranscriptServer transcriptServer,
                             AdviseServer adviseServer, MessageServer messageServer){
        this.personServer = personServer;
        this.studentServer = studentServer;
        this.courseServer = courseServer;
        this.transcriptServer = transcriptServer;
        this.adviseServer = adviseServer;
        this.messageServer = messageServer;
    }

    @Override
    public HaramMessage login(Person person) {

        HaramMessage haramMessage = new HaramMessage();
        try {
            Person user = personRepository.findByUserid(person.getUserid());
            if(user != null && user.getStatus().equals("1")) {
                haramMessage.setData(user);
                haramMessage.setCode(FlagDict.SUCCESS.getV());
                haramMessage.setMsg(FlagDict.SUCCESS.getM());
            }
            else if(user != null && user.getStatus().equals("0")){
                haramMessage.setCode(FlagDict.USER_DISABLED.getV());
                haramMessage.setMsg(FlagDict.USER_DISABLED.getM());
            }
            else{
                haramMessage.setCode(FlagDict.FAIL.getV());
                haramMessage.setMsg(FlagDict.FAIL.getM());
            }
            return haramMessage;
        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }

    @Override
    @Transactional
    public HaramMessage addUser(Person person) {
        HaramMessage haramMessage = new HaramMessage();
        try {
            String userid, password;

            String info = person.getInfo();
            List<Person> people = personRepository.findByInfo(info);

            if(person.getUserid() == null)
                userid = IDUtil.genUserID(info);
            else
                userid = person.getUserid();

            for (int i = 0; i < people.size(); i++) {
                Person p = people.get(i);
                if (userid.equals(p.getUserid())) {
                    userid = IDUtil.genUserID(info);
                    i = 0;
                }
            }
            person.setUserid(userid);

            if(person.getPassword() == null) {
                password = "Pioneer" + userid;
                person.setPassword(password);
            }
            
            String firstPY = Pinyin4jUtil.converterToFirstSpell(person.getLastname());
            String lastPY = Pinyin4jUtil.converterToFirstSpell(person.getFirstname());
            String username = lastPY + firstPY + userid.substring(7,10);

            person.setUsername(username);
            person.setCreatetime(DateUtil.DateToStr(new Date()));
            person.setUpdatetime(DateUtil.DateToStr(new Date()));
            person.setStatus("1");

            if(person.getType().contains("s")){
                StudentBase student = new StudentBase();
                student.setStudentid(userid);
                student.setMaxCredits(12);
                studentServer.insert(student);
            }
            int ret = personServer.insert(person);
            if(ret == 1){
                haramMessage.setCode(FlagDict.SUCCESS.getV());
                haramMessage.setMsg(FlagDict.SUCCESS.getM());
            }
            else{
                haramMessage.setCode(FlagDict.FAIL.getV());
                haramMessage.setMsg(FlagDict.FAIL.getM());
            }
            MessageWithBLOBs message = new MessageWithBLOBs();
            message.setDate(DateUtil.DateToStr(new Date()));
            message.setReceiverid(userid);
            message.setSenderid(IDUtil.ROOT);
            message.setBody("您的接收到来自管理员的一条消息:你的用户已成功创建");
            message.setTitle("账户信息");
            message.setStatus("UNREAD");
            message.setTag("work");
            message.setLabels("['inbox','important']");

            ret = messageServer.insertSelective(message);
            if(ret <= 0)
                throw new RuntimeException("MessageWithBLOBs 插入失败!");

            else if(ret == 1){
                haramMessage.setCode(FlagDict.SUCCESS.getV());
                haramMessage.setMsg(FlagDict.SUCCESS.getM());
                haramMessage.setData(person);
            }

            return haramMessage;
        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }

    @Override
    public HaramMessage userList(String currentPage, String pageSize, String search, String order, String orderColumn,
                                 String type, String status) {
        HaramMessage message = new HaramMessage();
        if(StringUtils.isNotEmpty(type)){
            switch (Integer.parseInt(orderColumn)) {
                case 0:
                    orderColumn = "userid";
                    break;
                case 1:
                    orderColumn = "firstname";
                    break;
                case 2:
                    orderColumn = "lastname";
                    break;
            }
        }
        else {
            switch (Integer.parseInt(orderColumn)) {
                case 0:
                    orderColumn = "id";
                    break;
                case 1:
                    orderColumn = "userid";
                    break;
                case 2:
                    orderColumn = "username";
                    break;
                case 3:
                    orderColumn = "lastname";
                    break;
                case 4:
                    orderColumn = "firstname";
                    break;
                case 5:
                    orderColumn = "password";
                    break;
                case 6:
                    orderColumn = "type";
                    break;
                case 7:
                    orderColumn = "status";
                    break;
                default:
                    orderColumn = "updatetime";
                    break;
            }
        }
        long totalSize = 0;
        try {
//            Map<String, Object> param = new HashMap<>();
//            param.put("search", search);
//            param.put("type", type);
//            param.put("status", status);
//
//            if(StringUtils.isEmpty(search))
//                param.put("search", null);

//            totalSize = personServer.getCountByMapPageSearchOrdered(param); //startTime, endTime);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

//            param.put("currentIndex", page.getCurrentIndex());
//            param.put("pageSize",  page.getPageSize());
//            param.put("order",  order);
//            param.put("orderColumn",  orderColumn);

            //(int currentIndex, int pageSize, String search, String order, String orderColumn);
//            List<Person> msgs = personServer.getByMapPageSearchOrdered(param);

            Sort sort = new Sort(Sort.Direction.DESC, orderColumn);
            Pageable pageable = new PageRequest(page.getCurrentIndex(), page.getPageSize(), sort);
            List<Person> personList = personRepository.findAll(pageable).getContent();
            message.setData(personList);
            message.put("page", page);
            message.setMsg(FlagDict.SUCCESS.getM());
            message.setCode(FlagDict.SUCCESS.getV());
            return message;

        }catch (Exception e) {
            e.printStackTrace();
            message.setMsg(FlagDict.SYSTEM_ERROR.getM());
            message.setCode(FlagDict.SYSTEM_ERROR.getV());
            return message;
        }
    }

    @Override
    public HaramMessage getUser(String userid) {
        HaramMessage haramMessage = new HaramMessage();
        try {
            Person user = personServer.selectByPrimaryKey(userid);
            if(user != null) {
                haramMessage.setData(user);
                haramMessage.setCode(FlagDict.SUCCESS.getV());
                haramMessage.setMsg(FlagDict.SUCCESS.getM());
            }
            else{
                haramMessage.setCode(FlagDict.FAIL.getV());
                haramMessage.setMsg(FlagDict.FAIL.getM());
            }
            return haramMessage;
        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }

    @Override
    public HaramMessage update(Person person) {
        HaramMessage haramMessage = new HaramMessage();
        try {
            person.setUpdatetime(DateUtil.DateToStr(new Date()));
            int ret = personServer.updateByPrimaryKeySelective(person);
            if(ret == 1) {
                haramMessage.setData(person);
                haramMessage.setCode(FlagDict.SUCCESS.getV());
                haramMessage.setMsg(FlagDict.SUCCESS.getM());
            }
            else{
                haramMessage.setCode(FlagDict.FAIL.getV());
                haramMessage.setMsg(FlagDict.FAIL.getM());
            }
            return haramMessage;
        }catch (Exception e){
            e.printStackTrace();
            haramMessage.setCode(FlagDict.SYSTEM_ERROR.getV());
            haramMessage.setMsg(FlagDict.SYSTEM_ERROR.getM());
            return haramMessage;
        }
    }

    @Override
    public HaramMessage listUsers(String search, String type, String status) {
        HaramMessage message = new HaramMessage();
        try {
            List<Person> users = personDao.getPersonBySearch(search, type, status);
            
            message.setData(users);
            message.setMsg(FlagDict.SUCCESS.getM());
            message.setCode(FlagDict.SUCCESS.getV());

            return message;

        }catch (Exception e) {
            e.printStackTrace();
            message.setMsg(FlagDict.SYSTEM_ERROR.getM());
            message.setCode(FlagDict.SYSTEM_ERROR.getV());
            return message;
        }
    }
    
    @Override
    public HaramMessage userChart() {
        HaramMessage message = new HaramMessage();
        //统计用户种类
        List<Map<String, String>> data1 = new ArrayList<>();
        Map<String, String> param = new HashMap<>();
        param.put("status", null);

        int s = personServer.countStudent(param);
        int f = personServer.countFaculty(param);
        int a = personServer.countAdmin();

        data1.add(MapParam.pieChartValue(String.valueOf(s), "StudentBase"));
        data1.add(MapParam.pieChartValue(String.valueOf(f), "Faculty"));
        data1.add(MapParam.pieChartValue(String.valueOf(a), "Administrator"));


        //统计性别
        List<Map<String, String>> data2 = new ArrayList<>();
        int male = personServer.countMale();
        int female = personServer.countFemale();

        data2.add(MapParam.pieChartValue(String.valueOf(male), "Male"));
        data2.add(MapParam.pieChartValue(String.valueOf(female), "Female"));

        message.put("dataBeast", data1);
        message.put("xAxisData", data2);

        return message;
    }

    @Override
    public HaramMessage getRelationChart() {
        HaramMessage message = new HaramMessage();
        try {

            List<Person> personList = personServer.getAllUsers();
            List<Course> courseList = courseServer.getAllActiveCourses();
            List<TranscriptBase> transcriptList = transcriptServer.getAllTranscripts();
            List<AdviseBase> adviseList = adviseServer.getAllAdvise();

            String xml = StaticGexfGraph.graphGenerator(personList, courseList, transcriptList, adviseList);
            message.setData(xml);
        }catch (Exception e){
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public HaramMessage countActivePerson(String type) {
        HaramMessage message = new HaramMessage();
        Map<String, String> param = new HashMap<>();
        param.put("status", "1");
        //统计用户种类
        switch (type){
            case "s":
                int s = personServer.countStudent(param);
                message.setData(s);
                return message;
            case "f":
                int f = personServer.countFaculty(param);
                message.setData(f);
                return message;
        }

        message.setData(0);
        return message;
    }

    @Override
    @Transactional
    public HaramMessage removeUser(String userid) {
        HaramMessage message = new HaramMessage();
        
        try {
            if(!userid.equals(IDUtil.ROOT)) {
                Person p = personServer.selectByPrimaryKey(userid);
                if (p != null) {
                    String type = p.getType();
                    if (type.contains("s")) {
                        studentServer.deleteByPrimaryKey(userid);
                        transcriptServer.deleteByStudentid(userid);
                    } else if (type.contains("f")) {
                        List<Course> courseViewList = courseServer.getAllActiveCourses();
                        for (Course c : courseViewList) {
                            String facultyid = c.getFacultyid();
                            if (facultyid.equals(userid)) {
                                String opTime = DateUtil.DateToStr(new Date());
                                c.setFacultyid(IDUtil.ROOT);
                                c.setComment(c.getFaculty() + "老师被删除, 删除时间：" + opTime);
                                c.setUpdatetime(DateUtil.DateToStr(new Date()));
                                courseServer.updateByPrimaryKeySelective(c);
                            }
                        }
                    }
                    adviseServer.deleteByUserID(userid);
                    personServer.deleteByPrimaryKey(userid);
                }
                message.setMsg(FlagDict.SUCCESS.getM());
                message.setCode(FlagDict.SUCCESS.getV());
            }
            else{
                message.setCode(FlagDict.DELETE_BLOCK.getV());
                message.setMsg(FlagDict.DELETE_BLOCK.getM());
            }
            return message;
        }catch (Exception e){
            e.printStackTrace();
            message.setCode(FlagDict.SYSTEM_ERROR.getV());
            message.setMsg(FlagDict.SYSTEM_ERROR.getM());
        }
        return message;
    }

}
