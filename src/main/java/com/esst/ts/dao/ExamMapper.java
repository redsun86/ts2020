package com.esst.ts.dao;

import com.esst.ts.model.Exam;
import com.esst.ts.model.ExamPOJO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface ExamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Exam record);

    int insertSelective(Exam record);

    Exam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Exam record);

    int updateByPrimaryKey(Exam record);

    Exam getInsertModel(Exam record);

    List<ExamPOJO> GetList(Exam mod);

    List<ExamPOJO> GetListWithDefault(Exam mod);

    List<ExamPOJO> GetListWithStudent(Exam mod);

    @Update("UPDATE exam SET is_deleted=1 WHERE id=#{id}")
    int deleteWithId(@Param("id") int id);

    int updateStatus(Map mapData);

    @Select("select * from exam")
    @ResultMap("BaseResultMap")
    List<Exam> getExamListAll();

}