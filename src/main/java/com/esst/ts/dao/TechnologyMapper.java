package com.esst.ts.dao;

import com.esst.ts.model.Technology;

public interface TechnologyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Technology record);

    int insertSelective(Technology record);

    Technology selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Technology record);

    int updateByPrimaryKey(Technology record);
}