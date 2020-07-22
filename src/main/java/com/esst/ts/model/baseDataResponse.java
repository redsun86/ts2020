package com.esst.ts.model;

/**
 * 学员成绩记录的基础数据实体
 * 创建标识：梁建磊 2020/7/20 17:14
 */
public class baseDataResponse {
    /**
     * 日期
     */
    private String dateTime;
    /**
     * 所属教师的用户id
     */
    private Integer teacherId;
    /**
     * 学员的用户id
     */
    private Integer userId;
    /**
     * 任务单/试卷id
     */
    private Integer taskId;
    /**
     * 提交报告的相关字符串
     */
    private String report;
    /**
     * 任务单/试卷名称
     */
    private String taskName;
    /**
     * 工况/试题id
     */
    private Integer operateId;
    /**
     * 工况/试题名称
     */
    private String operateName;
    /**
     * 学习类型。0：任务单；1：试卷
     */
    private Integer studyType;
    /**
     * 学习时长
     */
    private Double studyDuration;
    /**
     * 实际得分
     */
    private Float propScroe;
    /**
     * 任务单/试卷总分
     */
    private Float totalScore;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public Integer getStudyType() {
        return studyType;
    }

    public void setStudyType(Integer studyType) {
        this.studyType = studyType;
    }

    public Double getStudyDuration() {
        return studyDuration;
    }

    public void setStudyDuration(Double studyDuration) {
        this.studyDuration = studyDuration;
    }

    public Float getPropScroe() {
        return propScroe;
    }

    public void setPropScroe(Float propScroe) {
        this.propScroe = propScroe;
    }

    public Float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Float totalScore) {
        this.totalScore = totalScore;
    }
}
