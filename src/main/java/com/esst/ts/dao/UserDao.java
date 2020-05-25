package com.esst.ts.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.esst.ts.model.User;

@Repository
public interface UserDao {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User getUser(@Param("userName") String userName, @Param("password") String password);

    @Select("select * from user where binary user_name=#{userName}  and is_del!=1 ")
    @ResultMap("BaseResultMap")
    User getUserByUserName(@Param("userName") String userName);
}
