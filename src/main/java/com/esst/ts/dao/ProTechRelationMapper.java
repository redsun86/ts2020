package com.esst.ts.dao;

import com.esst.ts.model.ProTechRelation;

public interface ProTechRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProTechRelation record);

    int insertSelective(ProTechRelation record);

    ProTechRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProTechRelation record);

    int updateByPrimaryKey(ProTechRelation record);
}