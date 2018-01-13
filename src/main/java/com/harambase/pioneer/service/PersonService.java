package com.harambase.pioneer.service;

import com.harambase.common.HaramMessage;
import com.harambase.pioneer.pojo.Person;
import org.springframework.web.multipart.MultipartFile;

public interface PersonService {

    HaramMessage login(Person person);

    HaramMessage createPerson(Person person);

    HaramMessage deletePerson(String userId);

    HaramMessage updatePerson(String userId, Person person);

    HaramMessage get(String userId);

    HaramMessage list(int start, int length, String search, String order, String orderCol, String type, String status);

    HaramMessage search(String search, String type, String status);

    HaramMessage upload(String userId, MultipartFile file, String mode);
}
