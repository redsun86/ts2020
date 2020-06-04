package com.esst.ts.model;

public class courseTaskModel {
    private Integer id;//` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键/唯一标识',
    private Integer course_id;//` int(11) DEFAULT NULL COMMENT '对应的课程编号',
    private Integer task_id;//` int(11) DEFAULT NULL COMMENT '任务编号，用于显示的任务序号',
    private String task_name;//` varchar(255) DEFAULT NULL COMMENT '任务名称',
    private Integer isenabled;//

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public Integer getTask_id() {
        return task_id;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public Integer getIsenabled() {
        return isenabled;
    }

    public void setIsenabled(Integer isenabled) {
        this.isenabled = isenabled;
    }


}
