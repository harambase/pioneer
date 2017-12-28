package com.harambase.pioneer.service;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.base.Person;

public interface PersonService {

    HaramMessage login(Person person);

    HaramMessage createPerson(Person person);

    HaramMessage deletePerson(String userid);

    HaramMessage updatePerson(Person person);

    HaramMessage getUser(String userid);

    HaramMessage listUser(int start, int length, String search, String order, String orderCol, String type, String status);

    HaramMessage searchPerson(String search, String type, String status);

}
