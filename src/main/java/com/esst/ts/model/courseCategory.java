package com.esst.ts.model;

import java.util.Date;

public class courseCategory {
    private Integer coursecategoryid;

    private Integer ccPid;

    private String ccName;

    private Integer ccShoworder;

    private Boolean ccDeleted;

    private String ccRemark;

    private Integer ccCreateuser;

    private Date ccCreatetime;

    public Integer getCoursecategoryid() {
        return coursecategoryid;
    }

    public void setCoursecategoryid(Integer coursecategoryid) {
        this.coursecategoryid = coursecategoryid;
    }

    public Integer getCcPid() {
        return ccPid;
    }

    public void setCcPid(Integer ccPid) {
        this.ccPid = ccPid;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName == null ? null : ccName.trim();
    }

    public Integer getCcShoworder() {
        return ccShoworder;
    }

    public void setCcShoworder(Integer ccShoworder) {
        this.ccShoworder = ccShoworder;
    }

    public Boolean getCcDeleted() {
        return ccDeleted;
    }

    public void setCcDeleted(Boolean ccDeleted) {
        this.ccDeleted = ccDeleted;
    }

    public String getCcRemark() {
        return ccRemark;
    }

    public void setCcRemark(String ccRemark) {
        this.ccRemark = ccRemark == null ? null : ccRemark.trim();
    }

    public Integer getCcCreateuser() {
        return ccCreateuser;
    }

    public void setCcCreateuser(Integer ccCreateuser) {
        this.ccCreateuser = ccCreateuser;
    }

    public Date getCcCreatetime() {
        return ccCreatetime;
    }

    public void setCcCreatetime(Date ccCreatetime) {
        this.ccCreatetime = ccCreatetime;
    }
}