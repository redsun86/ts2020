package com.esst.ts.dao;

import com.esst.ts.model.courseCategoryApplys;

public interface courseCategoryApplysMapper {
    int deleteByPrimaryKey(Integer ccaId);

    int insert(courseCategoryApplys record);

    int insertSelective(courseCategoryApplys record);

    courseCategoryApplys selectByPrimaryKey(Integer ccaId);

    int updateByPrimaryKeySelective(courseCategoryApplys record);

    int updateByPrimaryKey(courseCategoryApplys record);
}