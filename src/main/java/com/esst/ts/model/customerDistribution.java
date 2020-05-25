package com.esst.ts.model;

import java.util.Date;

public class customerDistribution {
    private Integer cdId;

    private String cdDogId;

    private String cdDistributionNo;

    private Date cdDistributionTime;

    private String cdAccountPrefix;

    private Integer cdAccountNum;

    private String cdStartAccount;

    private String cdEndAccount;

    private Integer cdDistributionTypeid;

    public Integer getCdId() {
        return cdId;
    }

    public void setCdId(Integer cdId) {
        this.cdId = cdId;
    }

    public String getCdDogId() {
        return cdDogId;
    }

    public void setCdDogId(String cdDogId) {
        this.cdDogId = cdDogId == null ? null : cdDogId.trim();
    }

    public String getCdDistributionNo() {
        return cdDistributionNo;
    }

    public void setCdDistributionNo(String cdDistributionNo) {
        this.cdDistributionNo = cdDistributionNo == null ? null : cdDistributionNo.trim();
    }

    public Date getCdDistributionTime() {
        return cdDistributionTime;
    }

    public void setCdDistributionTime(Date cdDistributionTime) {
        this.cdDistributionTime = cdDistributionTime;
    }

    public String getCdAccountPrefix() {
        return cdAccountPrefix;
    }

    public void setCdAccountPrefix(String cdAccountPrefix) {
        this.cdAccountPrefix = cdAccountPrefix == null ? null : cdAccountPrefix.trim();
    }

    public Integer getCdAccountNum() {
        return cdAccountNum;
    }

    public void setCdAccountNum(Integer cdAccountNum) {
        this.cdAccountNum = cdAccountNum;
    }

    public String getCdStartAccount() {
        return cdStartAccount;
    }

    public void setCdStartAccount(String cdStartAccount) {
        this.cdStartAccount = cdStartAccount == null ? null : cdStartAccount.trim();
    }

    public String getCdEndAccount() {
        return cdEndAccount;
    }

    public void setCdEndAccount(String cdEndAccount) {
        this.cdEndAccount = cdEndAccount == null ? null : cdEndAccount.trim();
    }

    public Integer getCdDistributionTypeid() {
        return cdDistributionTypeid;
    }

    public void setCdDistributionTypeid(Integer cdDistributionTypeid) {
        this.cdDistributionTypeid = cdDistributionTypeid;
    }
}