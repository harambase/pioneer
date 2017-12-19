package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.common.MapParam;
import com.harambase.common.Page;
import com.harambase.common.constant.FlagDict;
import com.harambase.pioneer.pojo.Course;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.pojo.base.AdviseBase;
import com.harambase.pioneer.pojo.base.TranscriptBase;
import com.harambase.pioneer.server.*;
import com.harambase.pioneer.service.PersonService;
import com.harambase.support.charts.StaticGexfGraph;
import com.harambase.support.util.DateUtil;
import com.harambase.support.util.IDUtil;
import com.harambase.support.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PersonServiceImpl implements PersonService {

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;
    
    private final PersonServer personServer;

    @Autowired
    public PersonServiceImpl(PersonServer personServer){
        this.personServer = personServer;
    }

    @Override
    public HaramMessage login(Person person) {
        return personServer.login(IP, PORT, person.getUserid());
    }

    @Override
    public HaramMessage createPerson(Person person) {
        return personServer.create(IP, PORT, person);
    }

    @Override
    public HaramMessage deletePerson(String userid) {
        return personServer.delete(IP, PORT, userid);
    }

    @Override
    public HaramMessage updatePerson(Person person) {
        return personServer.update(IP, PORT, person);
    }
    
    @Override
    public HaramMessage getUser(String userid) {
        return personServer.get(IP, PORT, userid);
    }
    
    @Override
    public HaramMessage listUser(String currentPage, String pageSize, String search, String order, String orderColumn,
                                 String type, String status) {
        Page page = new Page();
        page.setCurrentPage(PageUtil.getcPg(currentPage));
        page.setPageSize(PageUtil.getLimit(pageSize));
        return personServer.list(IP, PORT, page.getCurrentIndex(), page.getPageSize(), search, order, orderColumn, type, status);
    }
    
    @Override
    public HaramMessage searchPerson(String search, String type, String status) {
        return personServer.getPersonBySearch(IP, PORT, 0, Integer.MAX_VALUE, search, "desc", "id", type, status);
    }
    
    @Override
    public HaramMessage getUserChart() {
        return personServer.userChart(IP, PORT);
    }

    @Override
    public HaramMessage getRelationChart() {
        return personServer.relationChart(IP, PORT);
    }

    @Override
    public HaramMessage countPerson(String status, String type) {
        return personServer.countPerson(IP, PORT, status, type);
    }

}
