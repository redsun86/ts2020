package com.esst.ts.dao;

import com.esst.ts.model.courseCategory;

public interface courseCategoryMapper {
    int deleteByPrimaryKey(Integer coursecategoryid);

    int insert(courseCategory record);

    int insertSelective(courseCategory record);

    courseCategory selectByPrimaryKey(Integer coursecategoryid);

    int updateByPrimaryKeySelective(courseCategory record);

    int updateByPrimaryKey(courseCategory record);
}