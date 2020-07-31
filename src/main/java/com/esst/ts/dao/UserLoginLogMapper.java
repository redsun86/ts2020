package com.esst.ts.dao;

import com.esst.ts.model.UserLoginLog;
import com.esst.ts.model.UserLoginLogPOJO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserLoginLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLoginLog record);

    int insertSelective(UserLoginLog record);

    UserLoginLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLoginLog record);

    int updateByPrimaryKey(UserLoginLog record);

    @Select("SELECT count(DISTINCT user_id) userCount FROM user_login_log u LEFT JOIN teacher_student_relation t ON u.user_id=t.student_id WHERE t.teacher_id=#{userId} and u.create_time>CAST(SYSDATE()AS DATE);")
    int getUserLoginLogCountByTeacherID(@Param("userId") String userId);

    @Select("select * from user_login_log where  create_time >=#{beginDate} and create_time <=#{endDate} and status=1 and is_admin=1 GROUP BY user_id")
    @ResultMap("BaseResultMap")
    List<UserLoginLog> getUserloginLogForDate(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    @Select("select id,date_format(create_time,'%Y-%m-%d') AS newcreate_time FROM user_login_log where is_admin=1 and status=1 and YEAR(create_time)=#{year} and MONTH(create_time)=#{month} GROUP BY newcreate_time")
    @ResultMap("BasePOJOResultMap")
    List<UserLoginLogPOJO> getDistinctDateForDate(@Param("year") int year, @Param("month") int month);

    @Select("select id,date_format(create_time,'%Y-%m-%d %h:%m:%s') AS newcreate_time FROM user_login_log where is_admin=1 and status=1 and user_id=#{userId} and create_time >=#{beginDate} and create_time <=#{endDate}")
    @ResultMap("BasePOJOResultMap")
    List<UserLoginLogPOJO> getUserLogByUserId(@Param("userId") Integer userId,@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    @Select("SELECT u.user_id,MIN(u.create_time) create_time FROM user_login_log u LEFT JOIN teacher_student_relation t ON u.user_id=t.student_id WHERE t.teacher_id=#{userId} and u.create_time>CAST(SYSDATE()AS DATE) GROUP BY u.user_id")
    @ResultMap("BaseResultMap")
    List<UserLoginLog> getUserLoginLogByTeacherID(@Param("userId") int userId);

    @Select("SELECT user_id,MIN(create_time) create_time FROM user_login_log where status=1 and create_time >=#{beginDate} and create_time <=#{endDate} GROUP BY user_id")
    @ResultMap("BaseResultMap")
    List<UserLoginLog> getStudentrLoginLog(@Param("beginDate") String beginDate, @Param("endDate") String endDate);
}