package com.esst.ts.model;

/**
 * 工况实体
 * 创建标识：梁建磊 2020-06-11
 */
public class TechnologyTaskOperatePOJO {
    private Integer id;//` int(11) NOT NULL AUTO_INCREMENT,
    private Integer technologyId;//` int(11) DEFAULT NULL COMMENT '所属工艺',
    private String operateCode;//` varchar(32) DEFAULT NULL COMMENT '工况编号',
    private String operateName;//` varchar(32) DEFAULT NULL COMMENT '工况名字',
    private Integer taskId; // 所属任务单id

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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
}
