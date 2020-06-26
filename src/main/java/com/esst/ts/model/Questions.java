package com.esst.ts.model;

public class Questions {
    private Integer id;

    private String questionName;

    private Integer exameId;

    private Integer operateId;

    private Integer troubleId;

    private Integer styleId;

    private Integer proportion;

    private Integer timeLimit;

    private Integer timeScale;

    private Integer isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExameId() {
        return exameId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public void setExameId(Integer exameId) {
        this.exameId = exameId;
    }

    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    public Integer getTroubleId() {
        return troubleId;
    }

    public void setTroubleId(Integer troubleId) {
        this.troubleId = troubleId;
    }

    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public Integer getProportion() {
        return proportion;
    }

    public void setProportion(Integer proportion) {
        this.proportion = proportion;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Integer getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(Integer timeScale) {
        this.timeScale = timeScale;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}