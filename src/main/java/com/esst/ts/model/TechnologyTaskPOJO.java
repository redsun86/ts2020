package com.esst.ts.model;

import java.util.List;

/**
 * 任务单实体
 * 创建标识：梁建磊 2020-06-11
 */
public class TechnologyTaskPOJO {
    private Integer id;//` int(11) NOT NULL AUTO_INCREMENT,
    private String task_code;//` varchar(32) DEFAULT NULL COMMENT '任务单编号',
    private String task_name;//` varchar(32) DEFAULT NULL COMMENT '任务单名称',
    private Integer technology_id;//` int(11) DEFAULT NULL COMMENT '工艺ID',
    private String shibiao;//` varchar(32) DEFAULT NULL COMMENT '时标（保留字段）',
    private List<TechnologyTaskOperatePOJO> operate_list;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTask_code() {
        return task_code;
    }

    public void setTask_code(String task_code) {
        this.task_code = task_code;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public Integer getTechnology_id() {
        return technology_id;
    }

    public void setTechnology_id(Integer technology_id) {
        this.technology_id = technology_id;
    }

    public String getShibiao() {
        return shibiao;
    }

    public void setShibiao(String shibiao) {
        this.shibiao = shibiao;
    }

    public List<TechnologyTaskOperatePOJO> getOperate_list() {
        return operate_list;
    }

    public void setOperate_list(List<TechnologyTaskOperatePOJO> operate_list) {
        this.operate_list = operate_list;
    }
}
