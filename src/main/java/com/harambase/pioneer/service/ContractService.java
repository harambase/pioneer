package com.harambase.pioneer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.harambase.pioneer.common.Config;
import com.harambase.pioneer.common.ResultMap;
import com.harambase.pioneer.common.constant.SystemConst;
import com.harambase.pioneer.common.support.util.FileUtil;
import com.harambase.pioneer.common.support.util.ReturnMsgUtil;
import com.harambase.pioneer.server.pojo.base.Contract;
import com.harambase.pioneer.server.service.ContractServerService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ContractService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ContractServerService contractServerService;

    @Autowired
    public ContractService(ContractServerService contractServerService) {
        this.contractServerService = contractServerService;
    }

    public ResultMap createContract(Contract contract) {
        try {
            return contractServerService.create(contract);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap deleteContract(Integer id) {
        try {
            return contractServerService.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap updateContract(Integer id, Contract contract) {
        try {
            return contractServerService.update(id, contract);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap get(Integer id) {
        try {
            return contractServerService.retrieve(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap list(int start, int length, String search, String order, String orderColumn,
                          String type, String status) {
        try {
            return contractServerService.list(String.valueOf(start / length + 1), String.valueOf(length), search, order, orderColumn, type, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap search(String search, String type, String status) {
        try {
            return contractServerService.search(search, type, status, "5");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ReturnMsgUtil.systemError();
        }
    }


    public ResultMap upload(Integer id, MultipartFile file) {

        ResultMap message = new ResultMap();
        JSONObject jsonObject = new JSONObject();

        try {
            Contract contract = (Contract) contractServerService.retrieve(id).getData();
            String name = file.getOriginalFilename();

            String fileUri;


            if (StringUtils.isNotEmpty(contract.getContractInfo())) {
                String oldInfoPath = (JSON.parseObject(contract.getContractInfo())).getString("path");
                FileUtil.deleteFileFromFTP(oldInfoPath, Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);
            }

            fileUri = FileUtil.uploadFileToFtpServer(file, Config.FTP_PATH + "/document/contractInfo/", Config.FTP_SERVER, Config.FTP_USERNAME, Config.FTP_PASSWORD);

            jsonObject.put("name", name);
            jsonObject.put("size", file.getSize());
            jsonObject.put("type", name.substring(name.lastIndexOf(".") + 1));
            jsonObject.put("path", fileUri);

            contract.setContractInfo(jsonObject.toJSONString());

            message = contractServerService.update(id, contract);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message.setMsg("上传失败");
            message.setCode(SystemConst.FAIL.getCode());
            return message;
        }
        logger.info("The file task - " + id + " has completed!");
        message.setMsg("上传成功");
        message.setData(jsonObject);
        message.setCode(SystemConst.SUCCESS.getCode());
        return message;
    }
}
