package com.harambase.pioneer.server.service.impl;

import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.DateUtil;
import com.harambase.pioneer.common.support.util.IDUtil;
import com.harambase.pioneer.common.support.util.PageUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.dao.base.ContractDao;
import com.harambase.pioneer.server.dao.repository.ContractRepository;
import com.harambase.pioneer.server.pojo.base.Contract;
import com.harambase.pioneer.server.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ContractRepository contractRepository;

    //Only for search and paging functionality
    private final ContractDao contractDao;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository, ContractDao contractDao) {
        this.contractRepository = contractRepository;
        this.contractDao = contractDao;
    }

    @Override
    public ResultMap addContract(Contract contract) {

        try {
            String contractId;
            String initDate = contract.getInitDate();

            List<Contract> contractList = contractRepository.findByInitDate(initDate);

            contractId = IDUtil.genContractID(contract.getInitDate());

            for (int i = 0; i < contractList.size(); i++) {
                Contract c = contractList.get(i);
                if (contractId.equals(c.getContractId())) {
                    contractId = IDUtil.genUserID(initDate);
                    i = 0;
                }
            }

            contract.setContractId(contractId);
            contract.setCreateTime(DateUtil.DateToStr(new Date()));
            contract.setUpdateTime(DateUtil.DateToStr(new Date()));

            Contract newContract = contractRepository.save(contract);
            return newContract != null ? ReturnMsgUtil.success(newContract) : ReturnMsgUtil.fail();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }

    }

    @Override
    public ResultMap removeContract(Integer id) {
        try {
            Contract contract = contractRepository.findOne(id);
            contractRepository.delete(contract);
            int count = contractRepository.countById(id);
            return count == 0 ? ReturnMsgUtil.success(null) : ReturnMsgUtil.fail();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap update(Integer id, Contract contract) {
        try {
            contract.setId(id);
            contract.setUpdateTime(DateUtil.DateToStr(new Date()));
            Contract newContract = contractRepository.save(contract);
            return newContract != null ? ReturnMsgUtil.success(newContract) : ReturnMsgUtil.fail();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap getContract(Integer id) {
        try {
            Contract contract = contractRepository.findOne(id);
            return ReturnMsgUtil.success(contract);
        } catch (Exception e) {
            logger.error(e.toString());
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap contractList(String currentPage, String pageSize, String search, String order, String orderColumn,
                                  String type, String status) {
        ResultMap message = new ResultMap();
        try {

            long totalSize = contractDao.getCountByMapPageSearchOrdered(search, type, status);

            Page page = new Page();
            page.setCurrentPage(PageUtil.getcPg(currentPage));
            page.setPageSize(PageUtil.getLimit(pageSize));
            page.setTotalRows(totalSize);

            List<LinkedHashMap> contractList = contractDao.getByMapPageSearchOrdered(page.getCurrentIndex(), page.getPageSize(), search, order, orderColumn, type, status);

            message.setData(contractList);
            message.put("page", page);
            message.setMsg(SystemConst.SUCCESS.getMsg());
            message.setCode(SystemConst.SUCCESS.getCode());
            return message;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    @Override
    public ResultMap listContracts(String search, String type, String status, String maxLength) {
        try {
            List<LinkedHashMap> contracts = contractDao.getContractBySearch(search, type, status, maxLength);
            return ReturnMsgUtil.success(contracts);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

    @Override
    public ResultMap count(String type) {
        try {
            int count = contractRepository.countByType(type);
            return ReturnMsgUtil.success(count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }

}
