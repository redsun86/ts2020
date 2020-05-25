package com.esst.ts.dao;

import com.esst.ts.model.exam;

public interface examMapper {
    int deleteByPrimaryKey(Integer examid);

    int insert(exam record);

    int insertSelective(exam record);

    exam selectByPrimaryKey(Integer examid);

    int updateByPrimaryKeySelective(exam record);

    int updateByPrimaryKeyWithBLOBs(exam record);

    int updateByPrimaryKey(exam record);
}