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

    User getUser(@Param("userName") String userName, @Param("password") String password);

    @Select("select * from user where id in(select student_id from teacher_student_relation where teacher_id = #{userId}) and is_del=0")
    @ResultMap("BaseResultMap")
    List<User> getUserLst(@Param("userId") int userId);

    @Update("update user set password = #{password} where id = #{userId}")
    int updateUserPwd(@Param("userId") int userId,@Param("password") String password);

    @Update("update user set is_del =1 where id = #{userId}")
    int deleteuserById(@Param("userId") int userId);
}