package com.esst.ts.dao;

import com.esst.ts.model.sysToken;

public interface sysTokenMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(sysToken record);

    int insertSelective(sysToken record);

    sysToken selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(sysToken record);

    int updateByPrimaryKey(sysToken record);
}