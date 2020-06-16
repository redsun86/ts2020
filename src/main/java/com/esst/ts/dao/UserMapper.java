package com.esst.ts.dao;

import com.esst.ts.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.*;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKeyWithBLOBs(User record);

    int updateByPrimaryKey(User record);

    @Select("select * from user where st_num = #{num} and id in(select student_id from teacher_student_relation where teacher_id = #{userId})")
    @ResultMap("BaseResultMap")
    User selectByNum(@Param("num") String num,@Param("userId") int userId);

    @Select("select * from user where user_name = #{userName} and password = #{passWord} and is_del=0")
    @ResultMap("BaseResultMap")
    User loginByTeacher(@Param("userName") String userName, @Param("passWord") String passWord);

    @Select("select * from user where rel_name = #{trueName} and st_num = #{num} and is_del=0")
    @ResultMap("BaseResultMap")
    User loginByStudent(@Param("trueName") String trueName, @Param("num") String num);

    @Select("select * from user where id in(select student_id from teacher_student_relation where teacher_id = #{userId}) and is_del=0")
    @ResultMap("BaseResultMap")
    List<User> getUserLst(@Param("userId") int userId);

    @Update("update user set password = #{password} where id = #{userId}")
    int updateUserPwd(@Param("userId") int userId,@Param("password") String password);

    @Update("update user set is_del =1 where id = #{userId}")
    int deleteUserById(@Param("userId") int userId);

    @Select("select * from user order by id DESC limit 1")
    @ResultMap("BaseResultMap")
    User selectLastRecord();

    @Select("select * from User")
    @ResultMap("BaseResultMap")
    List<User> getUserListAll();
}