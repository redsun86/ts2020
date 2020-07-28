package com.esst.ts.dao;

import com.esst.ts.model.UserToken;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Select("select * from user_token where user_id=#{userId} and login_type =1")
    @ResultMap("BaseResultMap")
    UserToken checkUserTokenIsLogin(@Param("userId") Integer userId);

    @Select("select user_id from user_token WHERE login_type=1 AND time_to_sec(timediff(update_time, NOW()))<#{webouttime}")
    @ResultMap("BaseResultMap")
    List<UserToken> getUserLoginList(@Param("webouttime") int webouttime);
}