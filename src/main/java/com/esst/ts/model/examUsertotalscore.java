package com.esst.ts.model;

import java.util.Date;

public class examUsertotalscore {
    private Integer usertotalscoreid;

    private Integer utsUserid;

    private Integer utsExamid;

    private Date utsStarttime;

    private Date utsEndtime;

    private Integer utsIsexamed;

    private Integer utsIssimnetexamed;

    private Integer utsExamtimes;

    private Integer utsSimnetexamtimes;

    private Double utsAcademicscore;

    private Double utsSimnetscore;

    private Double utsScore;

    private Double utsPercentscore;

    private Integer utsPerformancerating;

    private Integer utsExamcompletionstatus;

    private String utsScorereport;

    private Integer utsIssendsimnetstatistics;

    private String utsSendip;

    public Integer getUsertotalscoreid() {
        return usertotalscoreid;
    }

    public void setUsertotalscoreid(Integer usertotalscoreid) {
        this.usertotalscoreid = usertotalscoreid;
    }

    public Integer getUtsUserid() {
        return utsUserid;
    }

    public void setUtsUserid(Integer utsUserid) {
        this.utsUserid = utsUserid;
    }

    public Integer getUtsExamid() {
        return utsExamid;
    }

    public void setUtsExamid(Integer utsExamid) {
        this.utsExamid = utsExamid;
    }

    public Date getUtsStarttime() {
        return utsStarttime;
    }

    public void setUtsStarttime(Date utsStarttime) {
        this.utsStarttime = utsStarttime;
    }

    public Date getUtsEndtime() {
        return utsEndtime;
    }

    public void setUtsEndtime(Date utsEndtime) {
        this.utsEndtime = utsEndtime;
    }

    public Integer getUtsIsexamed() {
        return utsIsexamed;
    }

    public void setUtsIsexamed(Integer utsIsexamed) {
        this.utsIsexamed = utsIsexamed;
    }

    public Integer getUtsIssimnetexamed() {
        return utsIssimnetexamed;
    }

    public void setUtsIssimnetexamed(Integer utsIssimnetexamed) {
        this.utsIssimnetexamed = utsIssimnetexamed;
    }

    public Integer getUtsExamtimes() {
        return utsExamtimes;
    }

    public void setUtsExamtimes(Integer utsExamtimes) {
        this.utsExamtimes = utsExamtimes;
    }

    public Integer getUtsSimnetexamtimes() {
        return utsSimnetexamtimes;
    }

    public void setUtsSimnetexamtimes(Integer utsSimnetexamtimes) {
        this.utsSimnetexamtimes = utsSimnetexamtimes;
    }

    public Double getUtsAcademicscore() {
        return utsAcademicscore;
    }

    public void setUtsAcademicscore(Double utsAcademicscore) {
        this.utsAcademicscore = utsAcademicscore;
    }

    public Double getUtsSimnetscore() {
        return utsSimnetscore;
    }

    public void setUtsSimnetscore(Double utsSimnetscore) {
        this.utsSimnetscore = utsSimnetscore;
    }

    public Double getUtsScore() {
        return utsScore;
    }

    public void setUtsScore(Double utsScore) {
        this.utsScore = utsScore;
    }

    public Double getUtsPercentscore() {
        return utsPercentscore;
    }

    public void setUtsPercentscore(Double utsPercentscore) {
        this.utsPercentscore = utsPercentscore;
    }

    public Integer getUtsPerformancerating() {
        return utsPerformancerating;
    }

    public void setUtsPerformancerating(Integer utsPerformancerating) {
        this.utsPerformancerating = utsPerformancerating;
    }

    public Integer getUtsExamcompletionstatus() {
        return utsExamcompletionstatus;
    }

    public void setUtsExamcompletionstatus(Integer utsExamcompletionstatus) {
        this.utsExamcompletionstatus = utsExamcompletionstatus;
    }

    public String getUtsScorereport() {
        return utsScorereport;
    }

    public void setUtsScorereport(String utsScorereport) {
        this.utsScorereport = utsScorereport == null ? null : utsScorereport.trim();
    }

    public Integer getUtsIssendsimnetstatistics() {
        return utsIssendsimnetstatistics;
    }

    public void setUtsIssendsimnetstatistics(Integer utsIssendsimnetstatistics) {
        this.utsIssendsimnetstatistics = utsIssendsimnetstatistics;
    }

    public String getUtsSendip() {
        return utsSendip;
    }

    public void setUtsSendip(String utsSendip) {
        this.utsSendip = utsSendip == null ? null : utsSendip.trim();
    }
}