package com.esst.ts.model;

import java.util.Date;

public class exam {
    private Integer examid;

    private Integer eExamtype;

    private Integer eTrainingid;

    private Integer ePostid;

    private Integer ePostgradeid;

    private Integer eExammode;

    private String eName;

    private Date eStarttime;

    private Date eEndtime;

    private Integer eTotalcaculate;

    private Integer eTheoryproportion;

    private Integer eOperateproportion;

    private Integer ePassline;

    private Integer eGoodline;

    private Integer eExcellentline;

    private Integer eCreateuser;

    private Date eCreatetime;

    private Date eUpdatetime;

    private Integer eIsformalexam;

    private Integer eIsenable;

    private Integer ePaperTime;

    private Integer ePaperAnticheating;

    private Integer ePaperCameratime;

    private Integer ePaperAnswermode;

    private Integer ePaperQuestiondisorder;

    private Integer ePaperOptiondisorder;

    private Integer eScoreIsshow;

    private Integer eScoreIsview;

    private String eScoreEndremark;

    private Integer eScoreIscomment;

    private String eScoreComment;

    private Integer eScoreIsnotice;

    private Integer eScoreIsredo;

    private Integer eCourseid;

    private Integer eCoursedirectorynodeid;

    private Integer eCoursedirectoryid;

    private String eUploadtemplate;

    private String eInformation;

    public Integer getExamid() {
        return examid;
    }

    public void setExamid(Integer examid) {
        this.examid = examid;
    }

    public Integer geteExamtype() {
        return eExamtype;
    }

    public void seteExamtype(Integer eExamtype) {
        this.eExamtype = eExamtype;
    }

    public Integer geteTrainingid() {
        return eTrainingid;
    }

    public void seteTrainingid(Integer eTrainingid) {
        this.eTrainingid = eTrainingid;
    }

    public Integer getePostid() {
        return ePostid;
    }

    public void setePostid(Integer ePostid) {
        this.ePostid = ePostid;
    }

    public Integer getePostgradeid() {
        return ePostgradeid;
    }

    public void setePostgradeid(Integer ePostgradeid) {
        this.ePostgradeid = ePostgradeid;
    }

    public Integer geteExammode() {
        return eExammode;
    }

    public void seteExammode(Integer eExammode) {
        this.eExammode = eExammode;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName == null ? null : eName.trim();
    }

    public Date geteStarttime() {
        return eStarttime;
    }

    public void seteStarttime(Date eStarttime) {
        this.eStarttime = eStarttime;
    }

    public Date geteEndtime() {
        return eEndtime;
    }

    public void seteEndtime(Date eEndtime) {
        this.eEndtime = eEndtime;
    }

    public Integer geteTotalcaculate() {
        return eTotalcaculate;
    }

    public void seteTotalcaculate(Integer eTotalcaculate) {
        this.eTotalcaculate = eTotalcaculate;
    }

    public Integer geteTheoryproportion() {
        return eTheoryproportion;
    }

    public void seteTheoryproportion(Integer eTheoryproportion) {
        this.eTheoryproportion = eTheoryproportion;
    }

    public Integer geteOperateproportion() {
        return eOperateproportion;
    }

    public void seteOperateproportion(Integer eOperateproportion) {
        this.eOperateproportion = eOperateproportion;
    }

    public Integer getePassline() {
        return ePassline;
    }

    public void setePassline(Integer ePassline) {
        this.ePassline = ePassline;
    }

    public Integer geteGoodline() {
        return eGoodline;
    }

    public void seteGoodline(Integer eGoodline) {
        this.eGoodline = eGoodline;
    }

    public Integer geteExcellentline() {
        return eExcellentline;
    }

    public void seteExcellentline(Integer eExcellentline) {
        this.eExcellentline = eExcellentline;
    }

    public Integer geteCreateuser() {
        return eCreateuser;
    }

    public void seteCreateuser(Integer eCreateuser) {
        this.eCreateuser = eCreateuser;
    }

    public Date geteCreatetime() {
        return eCreatetime;
    }

    public void seteCreatetime(Date eCreatetime) {
        this.eCreatetime = eCreatetime;
    }

    public Date geteUpdatetime() {
        return eUpdatetime;
    }

    public void seteUpdatetime(Date eUpdatetime) {
        this.eUpdatetime = eUpdatetime;
    }

    public Integer geteIsformalexam() {
        return eIsformalexam;
    }

    public void seteIsformalexam(Integer eIsformalexam) {
        this.eIsformalexam = eIsformalexam;
    }

    public Integer geteIsenable() {
        return eIsenable;
    }

    public void seteIsenable(Integer eIsenable) {
        this.eIsenable = eIsenable;
    }

    public Integer getePaperTime() {
        return ePaperTime;
    }

    public void setePaperTime(Integer ePaperTime) {
        this.ePaperTime = ePaperTime;
    }

    public Integer getePaperAnticheating() {
        return ePaperAnticheating;
    }

    public void setePaperAnticheating(Integer ePaperAnticheating) {
        this.ePaperAnticheating = ePaperAnticheating;
    }

    public Integer getePaperCameratime() {
        return ePaperCameratime;
    }

    public void setePaperCameratime(Integer ePaperCameratime) {
        this.ePaperCameratime = ePaperCameratime;
    }

    public Integer getePaperAnswermode() {
        return ePaperAnswermode;
    }

    public void setePaperAnswermode(Integer ePaperAnswermode) {
        this.ePaperAnswermode = ePaperAnswermode;
    }

    public Integer getePaperQuestiondisorder() {
        return ePaperQuestiondisorder;
    }

    public void setePaperQuestiondisorder(Integer ePaperQuestiondisorder) {
        this.ePaperQuestiondisorder = ePaperQuestiondisorder;
    }

    public Integer getePaperOptiondisorder() {
        return ePaperOptiondisorder;
    }

    public void setePaperOptiondisorder(Integer ePaperOptiondisorder) {
        this.ePaperOptiondisorder = ePaperOptiondisorder;
    }

    public Integer geteScoreIsshow() {
        return eScoreIsshow;
    }

    public void seteScoreIsshow(Integer eScoreIsshow) {
        this.eScoreIsshow = eScoreIsshow;
    }

    public Integer geteScoreIsview() {
        return eScoreIsview;
    }

    public void seteScoreIsview(Integer eScoreIsview) {
        this.eScoreIsview = eScoreIsview;
    }

    public String geteScoreEndremark() {
        return eScoreEndremark;
    }

    public void seteScoreEndremark(String eScoreEndremark) {
        this.eScoreEndremark = eScoreEndremark == null ? null : eScoreEndremark.trim();
    }

    public Integer geteScoreIscomment() {
        return eScoreIscomment;
    }

    public void seteScoreIscomment(Integer eScoreIscomment) {
        this.eScoreIscomment = eScoreIscomment;
    }

    public String geteScoreComment() {
        return eScoreComment;
    }

    public void seteScoreComment(String eScoreComment) {
        this.eScoreComment = eScoreComment == null ? null : eScoreComment.trim();
    }

    public Integer geteScoreIsnotice() {
        return eScoreIsnotice;
    }

    public void seteScoreIsnotice(Integer eScoreIsnotice) {
        this.eScoreIsnotice = eScoreIsnotice;
    }

    public Integer geteScoreIsredo() {
        return eScoreIsredo;
    }

    public void seteScoreIsredo(Integer eScoreIsredo) {
        this.eScoreIsredo = eScoreIsredo;
    }

    public Integer geteCourseid() {
        return eCourseid;
    }

    public void seteCourseid(Integer eCourseid) {
        this.eCourseid = eCourseid;
    }

    public Integer geteCoursedirectorynodeid() {
        return eCoursedirectorynodeid;
    }

    public void seteCoursedirectorynodeid(Integer eCoursedirectorynodeid) {
        this.eCoursedirectorynodeid = eCoursedirectorynodeid;
    }

    public Integer geteCoursedirectoryid() {
        return eCoursedirectoryid;
    }

    public void seteCoursedirectoryid(Integer eCoursedirectoryid) {
        this.eCoursedirectoryid = eCoursedirectoryid;
    }

    public String geteUploadtemplate() {
        return eUploadtemplate;
    }

    public void seteUploadtemplate(String eUploadtemplate) {
        this.eUploadtemplate = eUploadtemplate == null ? null : eUploadtemplate.trim();
    }

    public String geteInformation() {
        return eInformation;
    }

    public void seteInformation(String eInformation) {
        this.eInformation = eInformation == null ? null : eInformation.trim();
    }
}