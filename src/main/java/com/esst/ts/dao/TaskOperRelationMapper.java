package com.esst.ts.dao;

import com.esst.ts.model.TaskOperRelation;

public interface TaskOperRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskOperRelation record);

    int insertSelective(TaskOperRelation record);

    TaskOperRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskOperRelation record);

    int updateByPrimaryKey(TaskOperRelation record);
}