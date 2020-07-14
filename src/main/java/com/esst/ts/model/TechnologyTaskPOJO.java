package com.esst.ts.model;

import java.util.List;

/**
 * 任务单实体
 * 创建标识：梁建磊 2020-06-11
 */
public class TechnologyTaskPOJO {
    private Integer id;//` int(11) NOT NULL AUTO_INCREMENT,
    private String taskCode;//` varchar(32) DEFAULT NULL COMMENT '任务单编号',
    private String taskName;//` varchar(32) DEFAULT NULL COMMENT '任务单名称',
    private Integer technologyId;//` int(11) DEFAULT NULL COMMENT '工艺ID',
    private String shiBiao;//` varchar(32) DEFAULT NULL COMMENT '时标（保留字段）',
    private Integer status;//状态（1：发布/2：未发布）
    private Integer teacherId;//教师id/发布者id,
    private List<Operate> operateList;

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
        this.taskCode = taskCode;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTechnologyId() {
        return technologyId;
    }

    public void setTechnologyId(Integer technologyId) {
        this.technologyId = technologyId;
    }

    public String getShiBiao() {
        return shiBiao;
    }

    public void setShiBiao(String shiBiao) {
        this.shiBiao = shiBiao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public List<Operate> getOperateList() {
        return operateList;
    }

    public void setOperateList(List<Operate> operateList) {
        this.operateList = operateList;
    }
}
