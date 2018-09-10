package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Person;

public interface PersonService {

    ResultMap updateLastLoginTime(String userId);

    ResultMap login(Person person);

    ResultMap getByKeyword(String keyword);

    ResultMap addUser(Person person);

    ResultMap userList(String s, String s1, String search, String order, String orderCol, String type, String status, String role);

    ResultMap getUser(String userid);

    ResultMap update(String userId, Person person);

    ResultMap listUsers(String search, String type, String status, String role, String maxLength);

    ResultMap countActivePerson(String type);

    ResultMap removeUser(String userid);

}
