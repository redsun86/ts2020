package com.esst.ts.model;

/**
 * 工况实体
 * 创建标识：梁建磊 2020-06-11
 */
public class TechnologyTaskOperatePOJO {
    private Integer id;//` int(11) NOT NULL AUTO_INCREMENT,
    private Integer technology_id;//` int(11) DEFAULT NULL COMMENT '所属工艺',
    private String operate_code;//` varchar(32) DEFAULT NULL COMMENT '工况编号',
    private String operate_name;//` varchar(32) DEFAULT NULL COMMENT '工况名字',

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTechnology_id() {
        return technology_id;
    }

    public void setTechnology_id(Integer technology_id) {
        this.technology_id = technology_id;
    }

    public String getOperate_code() {
        return operate_code;
    }

    public void setOperate_code(String operate_code) {
        this.operate_code = operate_code;
    }

    public String getOperate_name() {
        return operate_name;
    }

    public void setOperate_name(String operate_name) {
        this.operate_name = operate_name;
    }
}
