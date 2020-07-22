package com.esst.ts.model;

/**
 * 创建标识：梁建磊 2020/7/7 13:23
 */
public class StatisticalPOJO {
    /**
     * 学习类型。0：任务单；1：试卷
     */
    private Integer studyType;
    private Integer exameId;
    private Integer userId;
    /**
     * 是否历史数据。0：否；1：是；
     */
    private Integer isHistory;
    private String startTime;
    private String stopTime;
    private Short isAdmin;

    public Integer getStudyType() {
        return studyType;
    }

    public void setStudyType(Integer studyType) {
        this.studyType = studyType;
    }

    public Integer getExameId() {
        return exameId;
    }

    public void setExameId(Integer exameId) {
        this.exameId = exameId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(Integer isHistory) {
        this.isHistory = isHistory;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public Short getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Short isAdmin) {
        this.isAdmin = isAdmin;
    }
}
