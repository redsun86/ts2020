package com.esst.ts.dao;

import com.esst.ts.model.Operate;

public interface OperateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Operate record);

    int insertSelective(Operate record);

    Operate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Operate record);

    int updateByPrimaryKey(Operate record);
}