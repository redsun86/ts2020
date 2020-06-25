package com.esst.ts.dao;

import com.esst.ts.model.UserTaskRelation;

import java.util.Map;

public interface UserTaskRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTaskRelation record);

    int insertTaskIds(Map mapData);

    int deleteTaskIds(Map mapData);

    int insertSelective(UserTaskRelation record);

    UserTaskRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserTaskRelation record);

    int updateByPrimaryKey(UserTaskRelation record);

}