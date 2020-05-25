package com.esst.ts.model;

import java.util.Date;

public class courseSysuserstudylog {
    private Integer sysuserstudylogid;

    private Integer susCourseid;

    private Integer susUserid;

    private Integer susSchemeid;

    private Integer susPostid;

    private Integer susPostgradeid;

    private Date susBegintime;

    private Date susLasttime;

    private Integer susUsetime;

    private Integer susStudymodel;

    private Integer susStudytimes;

    private Integer susStudyprogress;

    private Integer susPostabilityid;

    private String susSimnetproject;

    private Double susSimnetscore;

    private String susSimnetscorereport;

    private Integer susSimnetstudytype;

    private Integer susIssendsimnetstatistics;

    private String susSendip;

    private Integer susTheorycourseid;

    public Integer getSysuserstudylogid() {
        return sysuserstudylogid;
    }

    public void setSysuserstudylogid(Integer sysuserstudylogid) {
        this.sysuserstudylogid = sysuserstudylogid;
    }

    public Integer getSusCourseid() {
        return susCourseid;
    }

    public void setSusCourseid(Integer susCourseid) {
        this.susCourseid = susCourseid;
    }

    public Integer getSusUserid() {
        return susUserid;
    }

    public void setSusUserid(Integer susUserid) {
        this.susUserid = susUserid;
    }

    public Integer getSusSchemeid() {
        return susSchemeid;
    }

    public void setSusSchemeid(Integer susSchemeid) {
        this.susSchemeid = susSchemeid;
    }

    public Integer getSusPostid() {
        return susPostid;
    }

    public void setSusPostid(Integer susPostid) {
        this.susPostid = susPostid;
    }

    public Integer getSusPostgradeid() {
        return susPostgradeid;
    }

    public void setSusPostgradeid(Integer susPostgradeid) {
        this.susPostgradeid = susPostgradeid;
    }

    public Date getSusBegintime() {
        return susBegintime;
    }

    public void setSusBegintime(Date susBegintime) {
        this.susBegintime = susBegintime;
    }

    public Date getSusLasttime() {
        return susLasttime;
    }

    public void setSusLasttime(Date susLasttime) {
        this.susLasttime = susLasttime;
    }

    public Integer getSusUsetime() {
        return susUsetime;
    }

    public void setSusUsetime(Integer susUsetime) {
        this.susUsetime = susUsetime;
    }

    public Integer getSusStudymodel() {
        return susStudymodel;
    }

    public void setSusStudymodel(Integer susStudymodel) {
        this.susStudymodel = susStudymodel;
    }

    public Integer getSusStudytimes() {
        return susStudytimes;
    }

    public void setSusStudytimes(Integer susStudytimes) {
        this.susStudytimes = susStudytimes;
    }

    public Integer getSusStudyprogress() {
        return susStudyprogress;
    }

    public void setSusStudyprogress(Integer susStudyprogress) {
        this.susStudyprogress = susStudyprogress;
    }

    public Integer getSusPostabilityid() {
        return susPostabilityid;
    }

    public void setSusPostabilityid(Integer susPostabilityid) {
        this.susPostabilityid = susPostabilityid;
    }

    public String getSusSimnetproject() {
        return susSimnetproject;
    }

    public void setSusSimnetproject(String susSimnetproject) {
        this.susSimnetproject = susSimnetproject == null ? null : susSimnetproject.trim();
    }

    public Double getSusSimnetscore() {
        return susSimnetscore;
    }

    public void setSusSimnetscore(Double susSimnetscore) {
        this.susSimnetscore = susSimnetscore;
    }

    public String getSusSimnetscorereport() {
        return susSimnetscorereport;
    }

    public void setSusSimnetscorereport(String susSimnetscorereport) {
        this.susSimnetscorereport = susSimnetscorereport == null ? null : susSimnetscorereport.trim();
    }

    public Integer getSusSimnetstudytype() {
        return susSimnetstudytype;
    }

    public void setSusSimnetstudytype(Integer susSimnetstudytype) {
        this.susSimnetstudytype = susSimnetstudytype;
    }

    public Integer getSusIssendsimnetstatistics() {
        return susIssendsimnetstatistics;
    }

    public void setSusIssendsimnetstatistics(Integer susIssendsimnetstatistics) {
        this.susIssendsimnetstatistics = susIssendsimnetstatistics;
    }

    public String getSusSendip() {
        return susSendip;
    }

    public void setSusSendip(String susSendip) {
        this.susSendip = susSendip == null ? null : susSendip.trim();
    }

    public Integer getSusTheorycourseid() {
        return susTheorycourseid;
    }

    public void setSusTheorycourseid(Integer susTheorycourseid) {
        this.susTheorycourseid = susTheorycourseid;
    }
}