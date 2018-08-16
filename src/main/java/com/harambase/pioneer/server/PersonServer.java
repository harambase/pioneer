package com.harambase.pioneer.server;

import com.harambase.pioneer.common.ResultMap;
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

    public ResultMap create(Person person) {
        return personService.addUser(person);
    }

    public ResultMap delete(String userId) {
        return personService.removeUser(userId);
    }

    public ResultMap update(String userId, Person person) {
        return personService.update(userId, person);
    }

    public ResultMap get(String userId) {
        return personService.getUser(userId);
    }

    public ResultMap login(Person person) {
        return personService.login(person);
    }

    public ResultMap search(String search, String type, String status, String role) {
        return personService.listUsers(search, type, status, role, "");
    }

    public ResultMap list(Integer start, Integer length, String search,
                          String order, String orderCol, String type, String status, String role) {
        return personService.userList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, type, status, role);
    }

    public ResultMap updateLastLoginTime(String username) {
        return personService.updateLastLoginTime(username);
    }
}
