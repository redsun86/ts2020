package com.esst.ts.dao;

import com.esst.ts.model.ExamUserRelation;

public interface ExamUserRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExamUserRelation record);

    int insertSelective(ExamUserRelation record);

    ExamUserRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExamUserRelation record);

    int updateByPrimaryKey(ExamUserRelation record);
}