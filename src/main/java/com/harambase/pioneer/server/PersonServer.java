package com.harambase.pioneer.server;

import com.harambase.pioneer.common.HaramMessage;
import com.harambase.pioneer.server.pojo.base.Person;
import com.harambase.pioneer.server.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonServer {

    private final PersonService personService;

    @Autowired
    public PersonServer(PersonService personService) {
        this.personService = personService;
    }

    public HaramMessage create(Person person) {
        return personService.addUser(person);
    }

    public HaramMessage delete(String userId) {
        return personService.removeUser(userId);
    }

    public HaramMessage update(String userId, Person person) {
        return personService.update(userId, person);
    }

    public HaramMessage get(String userId) {
        return personService.getUser(userId);
    }

    public HaramMessage login(Person person) {
        return personService.login(person);
    }

    public HaramMessage search(String search, String type, String status) {
        return personService.listUsers(search, type, status);
    }

    public HaramMessage list(Integer start, Integer length, String search,
                             String order, String orderCol, String type, String status) {
        return personService.userList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, type, status);
    }

}
