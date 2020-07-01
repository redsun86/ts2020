package com.esst.ts.service;

import com.esst.ts.model.Product;
import com.esst.ts.model.ProductCountPOJO;

import java.util.List;

/**
 * 产品包——业务逻辑层接口定义
 * <p>产品==》Product</p>
 * <p>创建标识：梁建磊 2020/6/16 15:19</p>
 */
public interface ProductService {
    /**
     * 自定义方法——获取所有产品包数量、工艺单元数量、任务单数量、工况数量
     *
     * @return 返回实体
     */
    ProductCountPOJO GetModel();

    /**
     * 自定义方法——获取产品包所有数据集合
     *
     * @return 返回数据集合
     */
    List<Product> GetList();
}
