package com.esst.ts.model;

/**
 * 创建标识：梁建磊 2020/7/7 13:23
 */
public class StatisticalPOJO {
    /**
     * 登录者用户id
     */
    private Integer userId;
    /**
     * 学习类型。0：任务单；1：试卷
     */
    private Integer studyType;
    private Integer exameId;
    /**
     * 所属老师用户id
     */
    private Integer teacherId;
    /**
     * 是否历史数据。0：否；1：是；
     */
    private Integer isHistory;
    private String startTime;
    private String stopTime;
    private Short isAdmin;

    /**
     * 总时长（min：分钟），用于动态生成区间
     */
    private String totalDuration;
    /**
     * 最高分，用于动态生成区间
     */
    private String totalScore;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

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

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
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

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }
}
