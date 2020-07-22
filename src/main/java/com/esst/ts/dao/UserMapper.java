package com.esst.ts.dao;

import com.esst.ts.model.User;
import org.apache.ibatis.annotations.*;

import java.util.*;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKeyWithBLOBs(User record);

    int updateByPrimaryKey(User record);

    @Select("select * from user where is_admin=-1")
    @ResultMap("BaseResultMap")
    User selectTeacher();

    @Update("update user set rel_name=#{trueName},mobile=#{mobile} where id=#{userId}")
    int updateUserInfo(@Param("userId") int userId,@Param("trueName") String trueName,@Param("mobile") String mobile,@Param("loginName") String loginName);

    @Select("select * from user where st_num = #{num} and id in(select student_id from teacher_student_relation where teacher_id = #{userId})")
    @ResultMap("BaseResultMap")
    User selectByNum(@Param("num") String num,@Param("userId") int userId);

    @Select("select * from user where st_num = #{num} and is_admin=0")
    @ResultMap("BaseResultMap")
    User getCheckUserByNum(@Param("num") String num);

    @Select("select * from user where user_name = #{userName} and password = #{passWord} and is_admin=1 and is_del=0")
    @ResultMap("BaseResultMap")
    User loginByTeacher(@Param("userName") String userName, @Param("passWord") String passWord);

    @Select("select * from user where rel_name =#{trueName} and st_num =#{num} and is_admin=0 AND id in(select student_id from teacher_student_relation where is_del=0)")
    @ResultMap("BaseResultMap")
    User loginByStudent(@Param("trueName") String trueName, @Param("num") String num);

    @Select("select * from user where id in(select student_id from teacher_student_relation where teacher_id = #{userId} and is_del=0)")
    @ResultMap("BaseResultMap")
    List<User> getUserLst(@Param("userId") int userId);

    @Select("select * from user where rel_name=#{userTrueName} AND is_admin=0")
    @ResultMap("BaseResultMap")
    List<User> getUserByTrueName(@Param("userTrueName") String userTrueName);

    @Update("update user set password = #{password} where id = #{userId}")
    int updateUserPwd(@Param("userId") int userId,@Param("password") String password);

    //@Update("update teacher_student_relation set is_del=1 where student_id = #{studentId} and teacher_id=#{teacherId}")
    @Delete("delete from teacher_student_relation where student_id = #{studentId} and teacher_id=#{teacherId}")
    int deleteUserById(@Param("studentId") int studentId,@Param("teacherId") int teacherId);

    @Select("select * from user order by id DESC limit 1")
    @ResultMap("BaseResultMap")
    User selectLastRecord();

    @Select("select * from User")
    @ResultMap("BaseResultMap")
    List<User> getUserListAll();

    int importBatchInsert(List<User> list);
}