package com.esst.ts.model;

import java.util.Date;

public class customerDistributionDetail {
    private Integer cddId;

    private Integer cddDistributionId;

    private Integer cddUserId;

    private Integer cddUserStatus;

    private Integer cddUnitId;

    private Integer cddProductId;

    private Date cddAddTime;

    private Date cddUpdateTime;

    private String cddUpdateMark;

    private Integer cddUpdateDistributionId;

    public Integer getCddId() {
        return cddId;
    }

    public void setCddId(Integer cddId) {
        this.cddId = cddId;
    }

    public Integer getCddDistributionId() {
        return cddDistributionId;
    }

    public void setCddDistributionId(Integer cddDistributionId) {
        this.cddDistributionId = cddDistributionId;
    }

    public Integer getCddUserId() {
        return cddUserId;
    }

    public void setCddUserId(Integer cddUserId) {
        this.cddUserId = cddUserId;
    }

    public Integer getCddUserStatus() {
        return cddUserStatus;
    }

    public void setCddUserStatus(Integer cddUserStatus) {
        this.cddUserStatus = cddUserStatus;
    }

    public Integer getCddUnitId() {
        return cddUnitId;
    }

    public void setCddUnitId(Integer cddUnitId) {
        this.cddUnitId = cddUnitId;
    }

    public Integer getCddProductId() {
        return cddProductId;
    }

    public void setCddProductId(Integer cddProductId) {
        this.cddProductId = cddProductId;
    }

    public Date getCddAddTime() {
        return cddAddTime;
    }

    public void setCddAddTime(Date cddAddTime) {
        this.cddAddTime = cddAddTime;
    }

    public Date getCddUpdateTime() {
        return cddUpdateTime;
    }

    public void setCddUpdateTime(Date cddUpdateTime) {
        this.cddUpdateTime = cddUpdateTime;
    }

    public String getCddUpdateMark() {
        return cddUpdateMark;
    }

    public void setCddUpdateMark(String cddUpdateMark) {
        this.cddUpdateMark = cddUpdateMark == null ? null : cddUpdateMark.trim();
    }

    public Integer getCddUpdateDistributionId() {
        return cddUpdateDistributionId;
    }

    public void setCddUpdateDistributionId(Integer cddUpdateDistributionId) {
        this.cddUpdateDistributionId = cddUpdateDistributionId;
    }
}