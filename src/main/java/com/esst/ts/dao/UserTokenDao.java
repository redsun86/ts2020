package com.esst.ts.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.esst.ts.model.UserToken;

@Repository
public interface UserTokenDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserToken record);

    int insertSelective(UserToken record);

    UserToken selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserToken record);

    int updateByPrimaryKey(UserToken record);

    @Delete("delete from user_token where user_id=#{userId} and login_type =#{loginType}")
    int invalidToken(@Param("userId") int userId, @Param("loginType") int loginType);

    @Delete("delete from user_token where user_id=#{userId}")
    int delToken(@Param("userId") int userId);

    @Select("select * from user_token where user_id=#{userId} and login_type =#{loginType}")
    @ResultMap("BaseResultMap")
    UserToken getUserToken(@Param("userId") Integer userId, @Param("loginType") int loginType);

    @Select("select * from user_token where user_id=#{userId} and token=#{token} and login_type =1")
    @ResultMap("BaseResultMap")
    UserToken checkUserTokenIsLogin(@Param("userId") Integer userId, @Param("token") String token);

}