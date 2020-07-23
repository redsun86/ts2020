package com.esst.ts.dao;

import com.esst.ts.model.TeacherStudentRelation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    /**
     * 常规方法——根据指定条件获取数据集合
     *
     * @param mod 请求参数
     * @return 返回数据集合
     */
    List<TeacherStudentRelation> GetList(TeacherStudentRelation mod);

    /**
     * 常规方法——获取在线老师的集合
     *
     * @return
     */
    List<TeacherStudentRelation> GetOnLineTeacherList();
}