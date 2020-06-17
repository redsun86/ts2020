package com.esst.ts.service;

import com.esst.ts.model.Product;
import com.esst.ts.model.ProductCountPOJO;

import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/16 15:19
 */
public interface ProductService {
    ProductCountPOJO GetModel();

    List<Product> GetList();
}
