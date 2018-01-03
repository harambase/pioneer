package com.harambase.pioneer.service;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Person;
import org.springframework.web.multipart.MultipartFile;

public interface PersonService {

    HaramMessage login(Person person);

    HaramMessage createPerson(Person person);

    HaramMessage deletePerson(String userId);

    HaramMessage updatePerson(String userId, Person person);

    HaramMessage getUser(String userId);

    HaramMessage listUser(int start, int length, String search, String order, String orderCol, String type, String status);

    HaramMessage searchPerson(String search, String type, String status);

    HaramMessage uploadProfile(String userId, MultipartFile file);
}
