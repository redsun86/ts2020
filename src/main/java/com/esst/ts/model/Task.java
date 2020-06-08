package com.esst.ts.model;

public class Task {
    private Integer id;

    private String taskCode;

    private String taskName;

    private Integer technologyId;

    private String shibiao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode == null ? null : taskCode.trim();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public Integer getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Integer technologyId) {
        this.technologyId = technologyId;
    }

    public String getShibiao() {
        return shibiao;
    }

    public void setShibiao(String shibiao) {
        this.shibiao = shibiao == null ? null : shibiao.trim();
    }
}