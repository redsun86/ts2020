package com.esst.ts.model;

import java.util.List;

/**
 * 工艺实体
 * 包含层级结构的实体
 * 创建标识：梁建磊 2020-06-11
 */
public class TechnologyPOJO {

    private Integer id;//` int(11) NOT NULL AUTO_INCREMENT,
    private String technology_code;//` varchar(32) DEFAULT NULL COMMENT '工艺编号',
    private String technology_zh_name;//` varchar(32) DEFAULT NULL COMMENT '工艺中文名',
    private String technology_en_name;//` varchar(32) DEFAULT NULL COMMENT '工艺英文名',
    private String style_id;//` varchar(32) DEFAULT NULL COMMENT '风格编号（以“，”分割）',
    private Integer product_id;//` int(11) DEFAULT NULL COMMENT '产品id',
    private Product product;
    private List<TechnologyTaskPOJO> task_list;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTechnology_code() {
        return technology_code;
    }

    public void setTechnology_code(String technology_code) {
        this.technology_code = technology_code;
    }

    public String getTechnology_zh_name() {
        return technology_zh_name;
    }

    public void setTechnology_zh_name(String technology_zh_name) {
        this.technology_zh_name = technology_zh_name;
    }

    public String getTechnology_en_name() {
        return technology_en_name;
    }

    public void setTechnology_en_name(String technology_en_name) {
        this.technology_en_name = technology_en_name;
    }

    public String getStyle_id() {
        return style_id;
    }

    public void setStyle_id(String style_id) {
        this.style_id = style_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<TechnologyTaskPOJO> getTask_list() {
        return task_list;
    }

    public void setTask_list(List<TechnologyTaskPOJO> task_list) {
        this.task_list = task_list;
    }
}
