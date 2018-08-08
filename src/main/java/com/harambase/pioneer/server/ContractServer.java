package com.harambase.pioneer.server;

import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.server.pojo.base.Contract;
import com.harambase.pioneer.server.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractServer {

    private final ContractService contractService;

    @Autowired
    public ContractServer(ContractService contractService) {
        this.contractService = contractService;
    }

    public ResultMap create(Contract contract) {
        return contractService.addContract(contract);
    }

    public ResultMap delete(String contractId) {
        return contractService.removeContract(contractId);
    }

    public ResultMap update(String contractId, Contract contract) {
        return contractService.update(contractId, contract);
    }

    public ResultMap get(String contractId) {
        return contractService.getContract(contractId);
    }


    public ResultMap search(String search, String type, String status) {
        return contractService.listContracts(search, type, status,"5");
    }

    public ResultMap list(Integer start, Integer length, String search,
                             String order, String orderCol, String type, String status) {
        return contractService.contractList(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderCol, type, status);
    }

}
