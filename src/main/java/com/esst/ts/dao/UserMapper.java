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

    @Select("select * from user ")
    @ResultMap("BaseResultMap")
    List<User> getUserLst(@Param("userID") int userID);

    @Update("update user set password = #{password} where id = #{userId}")
    int updateUserPwd(@Param("userId") int userId,@Param("password") String password);
}