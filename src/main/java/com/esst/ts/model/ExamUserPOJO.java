package com.esst.ts.model;

/**
 * 试卷关联的用户实体
 * 创建标识：梁建磊 2020-06-12
 */
public class ExamUserPOJO {
    private Integer id;//` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
    private String rel_name;//` varchar(255) DEFAULT NULL COMMENT '真实姓名',
    private String st_num;//` varchar(32) DEFAULT NULL COMMENT '学号',
    private String class_name;//` varchar(32) DEFAULT NULL COMMENT '班级',

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRel_name() {
        return rel_name;
    }

    public void setRel_name(String rel_name) {
        this.rel_name = rel_name;
    }

    public String getSt_num() {
        return st_num;
    }

    public void setSt_num(String st_num) {
        this.st_num = st_num;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}
