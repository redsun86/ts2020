package com.esst.ts.dao;

import com.esst.ts.model.Questions;
import com.esst.ts.model.QuestionsPOJO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface QuestionsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Questions record);

    int insertSelective(Questions record);

    Questions selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Questions record);

    int updateByPrimaryKey(Questions record);

    @Update("UPDATE questions SET is_deleted=1 WHERE id=#{id}")
    int deleteWithId(@Param("id") int id);

    @Select("select q.id,e.id exam_id,e.exam_name,t.id technology_id,t.technology_zh_name technology_name" +
            ",o.id operate_id,o.operate_name,tr.id trouble_id,tr.trouble_name,s.id style_id,s.style_name" +
            ",q.proportion,q.time_limit,q.time_scale from questions q LEFT JOIN exam e on q.exame_id=e.id " +
            "LEFT JOIN operate o ON q.operate_id=o.id LEFT JOIN technology t on t.id=o.technology_id " +
            "LEFT JOIN trouble tr on q.trouble_id=tr.id LEFT JOIN style s on q.style_id=s.id " +
            "where q.exame_id=#{exameId}")
    @ResultMap("BasePOJOResultMap")
    List<QuestionsPOJO> GetList(@Param("exameId") int exameId);
}