package com.esst.ts.dao;

import com.esst.ts.model.ExamUserRelation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ExamUserRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExamUserRelation record);

    int insertUserIds(Map mapData);

    int deleteUserIds(Map mapData);

    int insertSelective(ExamUserRelation record);

    ExamUserRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExamUserRelation record);

    int updateByPrimaryKey(ExamUserRelation record);

    @Select("SELECT e.id,e.exam_id,user_id FROM exam_user_relation e " +
            "where e.exam_id=#{exameId}")
    @ResultMap("BaseResultMap")
    List<ExamUserRelation> GetList(@Param("exameId") int exameId);
}