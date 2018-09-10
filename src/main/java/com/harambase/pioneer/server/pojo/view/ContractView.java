package com.harambase.pioneer.server.pojo.view;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contractview")
public class ContractView implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "contract_id")
    private String contractId;

    @Column(name = "owner_id")
    private String ownerId;

    @Column(name = "info")
    private String info;

    @Column(name = "type")
    private String type;

    @Column(name = "init_date")
    private String initDate;

    @Column(name = "expire_date")
    private String expireDate;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "update_time")
    private String updateTime;

    @Column(name = "operator_id")
    private String operatorId;

    @Column(name = "contract_info")
    private String contractInfo;

    @Column(name = "oname")
    private String oname;

    @Column(name = "opname")
    private String opname;

    @Column(name = "status")
    private String status;

    @Column(name = "comment")
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOpname() {
        return opname;
    }

    public void setOpname(String opname) {
        this.opname = opname;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(String contractInfo) {
        this.contractInfo = contractInfo;
    }
}