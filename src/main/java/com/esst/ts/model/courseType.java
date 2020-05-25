package com.esst.ts.model;

import java.util.Date;

public class courseType {
    private Integer coursetypeid;

    private String ctName;

    private Integer ctIssystemtype;

    private Integer ctShoworder;

    private Integer ctCreateuser;

    private Date ctCreatetime;

    private Integer ctIssimnet;

    public Integer getCoursetypeid() {
        return coursetypeid;
    }

    public void setCoursetypeid(Integer coursetypeid) {
        this.coursetypeid = coursetypeid;
    }

    public String getCtName() {
        return ctName;
    }

    public void setCtName(String ctName) {
        this.ctName = ctName == null ? null : ctName.trim();
    }

    public Integer getCtIssystemtype() {
        return ctIssystemtype;
    }

    public void setCtIssystemtype(Integer ctIssystemtype) {
        this.ctIssystemtype = ctIssystemtype;
    }

    public Integer getCtShoworder() {
        return ctShoworder;
    }

    public void setCtShoworder(Integer ctShoworder) {
        this.ctShoworder = ctShoworder;
    }

    public Integer getCtCreateuser() {
        return ctCreateuser;
    }

    public void setCtCreateuser(Integer ctCreateuser) {
        this.ctCreateuser = ctCreateuser;
    }

    public Date getCtCreatetime() {
        return ctCreatetime;
    }

    public void setCtCreatetime(Date ctCreatetime) {
        this.ctCreatetime = ctCreatetime;
    }

    public Integer getCtIssimnet() {
        return ctIssimnet;
    }

    public void setCtIssimnet(Integer ctIssimnet) {
        this.ctIssimnet = ctIssimnet;
    }
}