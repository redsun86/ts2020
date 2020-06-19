package com.esst.ts.dao;

import com.esst.ts.model.UserLoginLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserLoginLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLoginLog record);

    int insertSelective(UserLoginLog record);

    UserLoginLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLoginLog record);

    int updateByPrimaryKey(UserLoginLog record);

    @Select("SELECT count(DISTINCT user_id) userCount FROM user_login_log u LEFT JOIN teacher_student_relation t ON u.user_id=t.student_id WHERE t.teacher_id=#{userId};")
    int getUserLoginLogCountByTeacherID(@Param("userId") String userId);
}