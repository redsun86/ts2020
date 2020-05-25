package com.esst.ts.dao;

import com.esst.ts.model.customerDistribution;

public interface customerDistributionMapper {
    int deleteByPrimaryKey(Integer cdId);

    int insert(customerDistribution record);

    int insertSelective(customerDistribution record);

    customerDistribution selectByPrimaryKey(Integer cdId);

    int updateByPrimaryKeySelective(customerDistribution record);

    int updateByPrimaryKey(customerDistribution record);
}