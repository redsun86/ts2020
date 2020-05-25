package com.esst.ts.dao;

import com.esst.ts.model.customerDistributionDetail;

public interface customerDistributionDetailMapper {
    int deleteByPrimaryKey(Integer cddId);

    int insert(customerDistributionDetail record);

    int insertSelective(customerDistributionDetail record);

    customerDistributionDetail selectByPrimaryKey(Integer cddId);

    int updateByPrimaryKeySelective(customerDistributionDetail record);

    int updateByPrimaryKey(customerDistributionDetail record);
}