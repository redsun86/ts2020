package com.esst.ts.model;

/**
 * 工况实体
 */
public class Operate {
    /**
     * 主键/自动增长
     */
    private Integer id;
    /**
     * 所属工艺id
     */
    private Integer technologyId;
    /**
     * 工况编号
     */
    private String operateCode;
    /**
     * 工况名称
     */
    private String operateName;
    /**
     * 是否被删除（0：未删除；1：已删除）
     */
    private Integer isDeleted;
    /**
     * 所属任务单id
     */
    private Integer taskId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Integer technologyId) {
        this.technologyId = technologyId;
    }

    public String getOperateCode() {
        return operateCode;
    }

    public void setOperateCode(String operateCode) {
        this.operateCode = operateCode;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}