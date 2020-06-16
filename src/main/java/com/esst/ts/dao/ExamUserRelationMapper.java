package com.esst.ts.dao;

import com.esst.ts.model.ExamUserRelation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ExamUserRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ExamUserRelation record);

    int insertSelective(ExamUserRelation record);

    ExamUserRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ExamUserRelation record);

    int updateByPrimaryKey(ExamUserRelation record);

    @Update("delete from exam_user_relation where exam_id=#{exameId}")
    int deleteWithExameId(@Param("exameId") int exameId);

    @Select("SELECT e.id,e.exam_id,user_id FROM exam_user_relation e " +
            "where e.exam_id=#{exameId}")
    @ResultMap("BaseResultMap")
    List<ExamUserRelation> GetList(@Param("exameId") int exameId);
}