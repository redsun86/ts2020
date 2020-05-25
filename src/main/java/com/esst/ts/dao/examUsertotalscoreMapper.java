package com.esst.ts.dao;

import com.esst.ts.model.examUsertotalscore;

public interface examUsertotalscoreMapper {
    int deleteByPrimaryKey(Integer usertotalscoreid);

    int insert(examUsertotalscore record);

    int insertSelective(examUsertotalscore record);

    examUsertotalscore selectByPrimaryKey(Integer usertotalscoreid);

    int updateByPrimaryKeySelective(examUsertotalscore record);

    int updateByPrimaryKey(examUsertotalscore record);
}