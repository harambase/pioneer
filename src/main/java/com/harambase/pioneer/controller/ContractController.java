package com.harambase.pioneer.controller;

import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.Page;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.server.pojo.base.Contract;
import com.harambase.pioneer.service.ContractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping(value = "/contract")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PreAuthorize("hasAnyRole('LOGISTIC','ADMIN')")
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Contract contract) {
        ResultMap message = contractService.createContract(contract);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('LOGISTIC','ADMIN')")
    @RequestMapping(value = "/{contractId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("contractId") String contractid) {
        ResultMap message = contractService.deleteContract(contractid);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('LOGISTIC','ADMIN')")
    @RequestMapping(value = "/{contractId}", produces = "application/json", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("contractId") String contractId, @RequestBody Contract contract) {
        ResultMap message = contractService.updateContract(contractId, contract);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('LOGISTIC','ADMIN')")
    @RequestMapping(value = "/{contractId}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable(value = "contractId") String contractId) {
        ResultMap resultMap = contractService.get(contractId);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('LOGISTIC','ADMIN')")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity search(@RequestParam(value = "search", required = false) String search,
                                 @RequestParam(value = "type", required = false) String type,
                                 @RequestParam(value = "status", required = false) String status) {
        ResultMap message = contractService.search(search, type, status);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('LOGISTIC','ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity list(@RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                               @RequestParam(value = "length", required = false, defaultValue = "100") Integer length,
                               @RequestParam(value = "search", required = false, defaultValue = "") String search,
                               @RequestParam(value = "order", required = false, defaultValue = "desc") String order,
                               @RequestParam(value = "orderCol", required = false, defaultValue = "contract_id") String orderCol,
                               @RequestParam(value = "type", required = false) String type,
                               @RequestParam(value = "status", required = false) String status) {
        ResultMap message = contractService.list(start * length - 1, length, search, order, orderCol, type, status);
        message.put("recordsTotal", ((Page) message.get("page")).getTotalRows());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('LOGISTIC','ADMIN')")
    @RequestMapping(value = "/info/{contractId}", method = RequestMethod.PUT)
    public ResponseEntity uploadProfile(@RequestParam(value = "file", required = false) MultipartFile file,
                                        @PathVariable String contractId) {
        ResultMap message = contractService.upload(contractId, file);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/info/{contractId}", method = RequestMethod.GET)
    public void downloadUserInfo(@PathVariable(value = "contractId") String contractId, HttpServletResponse response) {
        ResultMap message = contractService.get(contractId);
        String contractInfo = ((Contract) message.getData()).getContractInfo();
        if (StringUtils.isNotEmpty(contractInfo)) {
            JSONObject info = JSONObject.parseObject(contractInfo);
            try {
                FileUtil.downloadFileFromFtpServer(response, info.getString("name"), info.getString("path"), Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
