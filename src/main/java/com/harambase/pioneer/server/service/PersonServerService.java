package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Person;

public interface PersonServerService {

    ResultMap login(Person person);

    ResultMap list(String currentPage, String pageSize, String search, String order, String orderColumn, String type, String status, String role);

    ResultMap create(Person person);
    
    ResultMap delete(String userId);

    ResultMap update(String userId, Person person);

    ResultMap updateLastLoginTime(String userId);

    ResultMap retrieve(String userId);

    ResultMap retrieveByKeyword(String keyword);

    ResultMap search(String search, String type, String status, String role, String maxLength);

    ResultMap countActivePersonByType(String type);
}
