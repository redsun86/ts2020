package com.esst.ts.model;

import java.util.List;

/**
 * 工艺实体
 * 包含层级结构的实体
 * 创建标识：梁建磊 2020-06-11
 */
public class TechnologyPOJO {
    private Integer id;//` int(11) NOT NULL AUTO_INCREMENT,
    private String technologyCode;//` varchar(32) DEFAULT NULL COMMENT '工艺编号',
    private String technologyZhName;//` varchar(32) DEFAULT NULL COMMENT '工艺中文名',
    private String technologyEnName;//` varchar(32) DEFAULT NULL COMMENT '工艺英文名',
    private String styleId;//` varchar(32) DEFAULT NULL COMMENT '风格编号（以“，”分割）',
    private Integer productId;//` int(11) DEFAULT NULL COMMENT '产品id',
    private Product product;
    private List<TechnologyTaskPOJO> taskList;
    private List<TechnologyTaskOperatePOJO> operateList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTechnologyCode() {
        return technologyCode;
    }

    public void setTechnologyCode(String technologyCode) {
        this.technologyCode = technologyCode;
    }

    public String getTechnologyZhName() {
        return technologyZhName;
    }

    public void setTechnologyZhName(String technologyZhName) {
        this.technologyZhName = technologyZhName;
    }

    public String getTechnologyEnName() {
        return technologyEnName;
    }

    public void setTechnologyEnName(String technologyEnName) {
        this.technologyEnName = technologyEnName;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<TechnologyTaskPOJO> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<TechnologyTaskPOJO> taskList) {
        this.taskList = taskList;
    }

    public List<TechnologyTaskOperatePOJO> getOperateList() {
        return operateList;
    }

    public void setOperateList(List<TechnologyTaskOperatePOJO> operateList) {
        this.operateList = operateList;
    }
}
