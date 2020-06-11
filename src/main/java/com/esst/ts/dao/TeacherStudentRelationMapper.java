package com.esst.ts.dao;

import com.esst.ts.model.TeacherStudentRelation;

public interface TeacherStudentRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeacherStudentRelation record);

    int insertSelective(TeacherStudentRelation record);

    TeacherStudentRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeacherStudentRelation record);

    int updateByPrimaryKey(TeacherStudentRelation record);
}