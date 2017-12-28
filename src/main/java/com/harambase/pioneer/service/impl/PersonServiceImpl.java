package com.harambase.pioneer.service.impl;

import com.harambase.common.Config;
import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Person;
import com.harambase.pioneer.server.PersonServer;
import com.harambase.pioneer.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PersonServiceImpl implements PersonService {

    private final static String IP = Config.SERVER_IP;
    private final static int PORT = Config.SERVER_PORT;

    private final PersonServer personServer;

    @Autowired
    public PersonServiceImpl(PersonServer personServer) {
        this.personServer = personServer;
    }

    @Override
    public HaramMessage login(Person person) {
        return personServer.login(IP, PORT, person);
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
    public HaramMessage listUser(int start, int length, String search, String order, String orderColumn,
                                 String type, String status) {
        return personServer.list(IP, PORT, start, length, search, order, orderColumn, type, status);
    }

    @Override
    public HaramMessage searchPerson(String search, String type, String status) {
        return personServer.getPersonBySearch(IP, PORT, search, type, status);
    }

}
