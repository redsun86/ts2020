package com.esst.ts.model;

import java.util.Date;

public class paper {
    private Integer paperid;

    private Integer pPapercategoryid;

    private String pPapername;

    private Integer pType;

    private Integer pModel;

    private String pSimnetno;

    private Integer pTime;

    private Integer pAllowexamtimes;

    private String pIncludequestiontype;

    private Double pSinglevalue;

    private Double pMultiplevalue;

    private Double pJudgevalue;

    private Double pFillblankvalue;

    private Double pEssayvalue;

    private Double pCaculatevalue;

    private Double pTotal;

    private Integer pIsneedmark;

    private Integer pCreateuser;

    private Date pCreatetime;

    private Integer pOrganizeMode;

    private Integer pTotalQuestions;

    public Integer getPaperid() {
        return paperid;
    }

    public void setPaperid(Integer paperid) {
        this.paperid = paperid;
    }

    public Integer getpPapercategoryid() {
        return pPapercategoryid;
    }

    public void setpPapercategoryid(Integer pPapercategoryid) {
        this.pPapercategoryid = pPapercategoryid;
    }

    public String getpPapername() {
        return pPapername;
    }

    public void setpPapername(String pPapername) {
        this.pPapername = pPapername == null ? null : pPapername.trim();
    }

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }

    public Integer getpModel() {
        return pModel;
    }

    public void setpModel(Integer pModel) {
        this.pModel = pModel;
    }

    public String getpSimnetno() {
        return pSimnetno;
    }

    public void setpSimnetno(String pSimnetno) {
        this.pSimnetno = pSimnetno == null ? null : pSimnetno.trim();
    }

    public Integer getpTime() {
        return pTime;
    }

    public void setpTime(Integer pTime) {
        this.pTime = pTime;
    }

    public Integer getpAllowexamtimes() {
        return pAllowexamtimes;
    }

    public void setpAllowexamtimes(Integer pAllowexamtimes) {
        this.pAllowexamtimes = pAllowexamtimes;
    }

    public String getpIncludequestiontype() {
        return pIncludequestiontype;
    }

    public void setpIncludequestiontype(String pIncludequestiontype) {
        this.pIncludequestiontype = pIncludequestiontype == null ? null : pIncludequestiontype.trim();
    }

    public Double getpSinglevalue() {
        return pSinglevalue;
    }

    public void setpSinglevalue(Double pSinglevalue) {
        this.pSinglevalue = pSinglevalue;
    }

    public Double getpMultiplevalue() {
        return pMultiplevalue;
    }

    public void setpMultiplevalue(Double pMultiplevalue) {
        this.pMultiplevalue = pMultiplevalue;
    }

    public Double getpJudgevalue() {
        return pJudgevalue;
    }

    public void setpJudgevalue(Double pJudgevalue) {
        this.pJudgevalue = pJudgevalue;
    }

    public Double getpFillblankvalue() {
        return pFillblankvalue;
    }

    public void setpFillblankvalue(Double pFillblankvalue) {
        this.pFillblankvalue = pFillblankvalue;
    }

    public Double getpEssayvalue() {
        return pEssayvalue;
    }

    public void setpEssayvalue(Double pEssayvalue) {
        this.pEssayvalue = pEssayvalue;
    }

    public Double getpCaculatevalue() {
        return pCaculatevalue;
    }

    public void setpCaculatevalue(Double pCaculatevalue) {
        this.pCaculatevalue = pCaculatevalue;
    }

    public Double getpTotal() {
        return pTotal;
    }

    public void setpTotal(Double pTotal) {
        this.pTotal = pTotal;
    }

    public Integer getpIsneedmark() {
        return pIsneedmark;
    }

    public void setpIsneedmark(Integer pIsneedmark) {
        this.pIsneedmark = pIsneedmark;
    }

    public Integer getpCreateuser() {
        return pCreateuser;
    }

    public void setpCreateuser(Integer pCreateuser) {
        this.pCreateuser = pCreateuser;
    }

    public Date getpCreatetime() {
        return pCreatetime;
    }

    public void setpCreatetime(Date pCreatetime) {
        this.pCreatetime = pCreatetime;
    }

    public Integer getpOrganizeMode() {
        return pOrganizeMode;
    }

    public void setpOrganizeMode(Integer pOrganizeMode) {
        this.pOrganizeMode = pOrganizeMode;
    }

    public Integer getpTotalQuestions() {
        return pTotalQuestions;
    }

    public void setpTotalQuestions(Integer pTotalQuestions) {
        this.pTotalQuestions = pTotalQuestions;
    }
}