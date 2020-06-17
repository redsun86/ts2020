package com.esst.ts.service.impl;

import com.esst.ts.model.Product;
import com.esst.ts.model.ProductCountPOJO;
import com.esst.ts.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/16 15:19
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {
    @Resource
    com.esst.ts.dao.ProductMapper ProductMapper;

    @Override
    public ProductCountPOJO GetModel() {
        return ProductMapper.GetModel();
    }

    @Override
    public List<Product> GetList() {
        return ProductMapper.GetList();
    }
}
