package com.esst.ts.dao;

import com.esst.ts.model.UserTaskRelation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserTaskRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserTaskRelation record);

    int insertSelective(UserTaskRelation record);

    UserTaskRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserTaskRelation record);

    int updateByPrimaryKey(UserTaskRelation record);

    @Update("delete from user_task_relation where user_id=#{userId}")
    int deleteWithUserId(@Param("userId") int userId);


    @Update("delete from user_task_relation where user_id=#{userId} and task_id=#{taskId}")
    int deleteWithTaskIdUserId(@Param("userId") int userId,@Param("taskId") int taskId);

}