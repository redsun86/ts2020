package com.esst.ts.dao;

import com.esst.ts.model.examUser;

public interface examUserMapper {
    int deleteByPrimaryKey(Integer examuserid);

    int insert(examUser record);

    int insertSelective(examUser record);

    examUser selectByPrimaryKey(Integer examuserid);

    int updateByPrimaryKeySelective(examUser record);

    int updateByPrimaryKey(examUser record);
}