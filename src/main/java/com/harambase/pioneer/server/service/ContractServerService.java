package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Contract;

public interface ContractServerService {

    ResultMap list(String s, String s1, String search, String order, String orderCol, String type, String status);

    ResultMap create(Contract contract);

    ResultMap delete(Integer id);

    ResultMap update(Integer id, Contract contract);

    ResultMap retrieve(Integer id);

    ResultMap search(String search, String type, String status, String maxLength);

    ResultMap countByType(String type);

}
