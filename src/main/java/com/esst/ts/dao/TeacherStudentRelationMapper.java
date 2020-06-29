package com.esst.ts.dao;

import com.esst.ts.model.TeacherStudentRelation;
import com.esst.ts.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

public interface TeacherStudentRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeacherStudentRelation record);

    int insertSelective(TeacherStudentRelation record);

    TeacherStudentRelation selectByPrimaryKey(Integer id);

    TeacherStudentRelation selectByTeacherId(Integer id);

    @Select("select * from teacher_student_relation where student_id=#{userId} and teacher_id = #{teacherId}")
    @ResultMap("BaseResultMap")
    TeacherStudentRelation selectByUserAndTeacher(@Param("userId") int userId, @Param("teacherId") int teacherId);


    int updateByPrimaryKeySelective(TeacherStudentRelation record);

    int updateByPrimaryKey(TeacherStudentRelation record);
}