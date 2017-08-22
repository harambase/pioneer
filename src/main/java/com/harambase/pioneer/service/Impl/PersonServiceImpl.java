package com.harambase.pioneer.service.Impl;

import com.harambase.common.*;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.dao.PersonMapper;
import com.harambase.pioneer.dao.StudentMapper;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.pojo.Student;
import com.harambase.pioneer.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonMapper personMapper;
    private final StudentMapper studentMapper;

    @Autowired
    public PersonServiceImpl(PersonMapper personMapper,
                             StudentMapper studentMapper){
        this.personMapper = personMapper;
        this.studentMapper = studentMapper;
    }

    @Override
    public HaramMessage login(Person person) {
        HaramMessage haramMessage = new HaramMessage();
        try {
            Person user = personMapper.selectByPerson(person);
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
    public HaramMessage addUser(Person person) {
        HaramMessage haramMessage = new HaramMessage();
        try {
            person.setCreatetime(DateUtil.DateToStr(new Date()));
            person.setUpdatetime(DateUtil.DateToStr(new Date()));
            person.setStatus("1");

            String info = person.getInfo();
            Integer last = (int)(Math.random() * (999 - 100 + 1) + 100);
            String userid = "9" + info.split("-")[0] + info.split("-")[1] + last;
            String username = person.getLastname().substring(0,1)+ person.getFirstname();
            String password = "pioneer" + userid;

            person.setUserid(userid);
            person.setUsername(username);
            person.setPassword(password);

            if(person.getType().equals("s")){
                Student student = new Student();
                student.setStudentid(userid);
                student.setMaxCredits(18);
                studentMapper.insert(student);
            }
            int ret = personMapper.insert(person);
            if(ret == 1){
                haramMessage.setCode(FlagDict.SUCCESS.getV());
                haramMessage.setMsg(FlagDict.SUCCESS.getM());
                haramMessage.setData(person);
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
    public HaramMessage userList(String currentPage, String pageSize, String search, String order, String orderColumn,
                                 String type, String status) {
        HaramMessage message = new HaramMessage();
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
                orderColumn = "firstname";
                break;
            case 4:
                orderColumn = "lastname";
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
        long totalSize = 0;
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("search", search);
            param.put("type", type);
            param.put("status", status);

            if(search.equals(""))
                param.put("search", null);

            totalSize = personMapper.getCountByMapPageSearchOrdered(param); //startTime, endTime);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            param.put("currentIndex", page.getCurrentIndex());
            param.put("pageSize",  page.getPageSize());
            param.put("order",  order);
            param.put("orderColumn",  orderColumn);

            //(int currentIndex, int pageSize, String search, String order, String orderColumn);
            List<Person> msgs = personMapper.getByMapPageSearchOrdered(param);

            message.setData(msgs);
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
            Person user = personMapper.selectByPrimaryKey(userid);
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
            int ret = personMapper.updateByPrimaryKeySelective(person);
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
    public HaramMessage listFaculties(String search) {
        HaramMessage message = new HaramMessage();
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("search", search);
            param.put("type", "f");
            param.put("status", "1");

            if(search.equals(""))
                param.put("search", null);

            List<Person> users = personMapper.getUsersBySearch(param);

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
    public HaramMessage listStudents(String search) {
        HaramMessage message = new HaramMessage();
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("search", search);
            param.put("type", "s");
            param.put("status", "1");

            if(search.equals(""))
                param.put("search", null);

            List<Person> msgs = personMapper.getUsersBySearch(param);

            message.setData(msgs);
            return message;

        }catch (Exception e) {
            e.printStackTrace();
            message.setMsg(FlagDict.SYSTEM_ERROR.getM());
            message.setCode(FlagDict.SYSTEM_ERROR.getV());
            return message;
        }
    }

    @Override
    public HaramMessage countPerson() {
        HaramMessage message = new HaramMessage();
        //统计用户种类
        List<Map<String, String>> data1 = new ArrayList<>();
        Map<String, String> innerData = new HashMap<>();

        int s = personMapper.countStudent();
        int f = personMapper.countFaculty();
        int a = personMapper.countAdmin();

        data1.add(MapParam.pieChartValue(String.valueOf(s), "Student"));
        data1.add(MapParam.pieChartValue(String.valueOf(f), "Faculty"));
        data1.add(MapParam.pieChartValue(String.valueOf(a), "Administrator"));


        //统计性别
        List<Map<String, String>> data2 = new ArrayList<>();
        int male = personMapper.countMale();
        int female = personMapper.countFemale();

        data2.add(MapParam.pieChartValue(String.valueOf(male), "Male"));
        data2.add(MapParam.pieChartValue(String.valueOf(female), "Female"));

        message.put("dataBeast", data1);
        message.put("xAxisData", data2);

        return message;
    }


}