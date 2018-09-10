package com.harambase.pioneer.server.service;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Contract;

public interface ContractService {

    ResultMap addContract(Contract contract);

    ResultMap contractList(String s, String s1, String search, String order, String orderCol, String type, String status);

    ResultMap getContract(Integer id);

    ResultMap update(Integer id, Contract contract);

    ResultMap listContracts(String search, String type, String status, String maxLength);

    ResultMap count(String type);

    ResultMap removeContract(Integer id);

}
