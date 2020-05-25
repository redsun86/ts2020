package com.esst.ts.model;

import java.util.Date;

public class examUserdetailscore {
    private Integer userdetailscoreid;

    private Integer udsExamid;

    private Integer udsExammodel;

    private Integer udsPaperid;

    private Integer udsUserid;

    private Date udsStarttime;

    private Date udsEndtime;

    private Double udsObjectivescore;

    private Double udsSubjectivescore;

    private String udsSubsimuscore;

    private Double udsScore;

    private Double udsPercentscore;

    private Integer udsIsexamed;

    private Integer udsMarkinguserid;

    private Integer udsIsmarked;

    private Date udsMarkingtime;

    private String udsSimnetscorereport;

    private Integer udsIssend;

    private String udsSendip;

    public Integer getUserdetailscoreid() {
        return userdetailscoreid;
    }

    public void setUserdetailscoreid(Integer userdetailscoreid) {
        this.userdetailscoreid = userdetailscoreid;
    }

    public Integer getUdsExamid() {
        return udsExamid;
    }

    public void setUdsExamid(Integer udsExamid) {
        this.udsExamid = udsExamid;
    }

    public Integer getUdsExammodel() {
        return udsExammodel;
    }

    public void setUdsExammodel(Integer udsExammodel) {
        this.udsExammodel = udsExammodel;
    }

    public Integer getUdsPaperid() {
        return udsPaperid;
    }

    public void setUdsPaperid(Integer udsPaperid) {
        this.udsPaperid = udsPaperid;
    }

    public Integer getUdsUserid() {
        return udsUserid;
    }

    public void setUdsUserid(Integer udsUserid) {
        this.udsUserid = udsUserid;
    }

    public Date getUdsStarttime() {
        return udsStarttime;
    }

    public void setUdsStarttime(Date udsStarttime) {
        this.udsStarttime = udsStarttime;
    }

    public Date getUdsEndtime() {
        return udsEndtime;
    }

    public void setUdsEndtime(Date udsEndtime) {
        this.udsEndtime = udsEndtime;
    }

    public Double getUdsObjectivescore() {
        return udsObjectivescore;
    }

    public void setUdsObjectivescore(Double udsObjectivescore) {
        this.udsObjectivescore = udsObjectivescore;
    }

    public Double getUdsSubjectivescore() {
        return udsSubjectivescore;
    }

    public void setUdsSubjectivescore(Double udsSubjectivescore) {
        this.udsSubjectivescore = udsSubjectivescore;
    }

    public String getUdsSubsimuscore() {
        return udsSubsimuscore;
    }

    public void setUdsSubsimuscore(String udsSubsimuscore) {
        this.udsSubsimuscore = udsSubsimuscore == null ? null : udsSubsimuscore.trim();
    }

    public Double getUdsScore() {
        return udsScore;
    }

    public void setUdsScore(Double udsScore) {
        this.udsScore = udsScore;
    }

    public Double getUdsPercentscore() {
        return udsPercentscore;
    }

    public void setUdsPercentscore(Double udsPercentscore) {
        this.udsPercentscore = udsPercentscore;
    }

    public Integer getUdsIsexamed() {
        return udsIsexamed;
    }

    public void setUdsIsexamed(Integer udsIsexamed) {
        this.udsIsexamed = udsIsexamed;
    }

    public Integer getUdsMarkinguserid() {
        return udsMarkinguserid;
    }

    public void setUdsMarkinguserid(Integer udsMarkinguserid) {
        this.udsMarkinguserid = udsMarkinguserid;
    }

    public Integer getUdsIsmarked() {
        return udsIsmarked;
    }

    public void setUdsIsmarked(Integer udsIsmarked) {
        this.udsIsmarked = udsIsmarked;
    }

    public Date getUdsMarkingtime() {
        return udsMarkingtime;
    }

    public void setUdsMarkingtime(Date udsMarkingtime) {
        this.udsMarkingtime = udsMarkingtime;
    }

    public String getUdsSimnetscorereport() {
        return udsSimnetscorereport;
    }

    public void setUdsSimnetscorereport(String udsSimnetscorereport) {
        this.udsSimnetscorereport = udsSimnetscorereport == null ? null : udsSimnetscorereport.trim();
    }

    public Integer getUdsIssend() {
        return udsIssend;
    }

    public void setUdsIssend(Integer udsIssend) {
        this.udsIssend = udsIssend;
    }

    public String getUdsSendip() {
        return udsSendip;
    }

    public void setUdsSendip(String udsSendip) {
        this.udsSendip = udsSendip == null ? null : udsSendip.trim();
    }
}