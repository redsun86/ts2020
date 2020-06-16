package com.esst.ts.dao;

import com.esst.ts.model.userLoginLog;

public interface userLoginLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(userLoginLog record);

    int insertSelective(userLoginLog record);

    userLoginLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(userLoginLog record);

    int updateByPrimaryKey(userLoginLog record);
}