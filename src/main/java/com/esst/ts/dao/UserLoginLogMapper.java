package com.esst.ts.dao;

import com.esst.ts.model.UserLoginLog;

public interface UserLoginLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLoginLog record);

    int insertSelective(UserLoginLog record);

    UserLoginLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLoginLog record);

    int updateByPrimaryKey(UserLoginLog record);
}