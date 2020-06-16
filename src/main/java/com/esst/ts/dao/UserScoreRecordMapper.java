package com.esst.ts.dao;

import com.esst.ts.model.UserScoreRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserScoreRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserScoreRecord record);

    int insertSelective(UserScoreRecord record);

    UserScoreRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserScoreRecord record);

    int updateByPrimaryKey(UserScoreRecord record);

    @Select("select * from user_score_record where user_id=#{userId} and task_id =#{taskId}and operate_id =#{operateId} and end_time IS NULL")
    List<UserScoreRecord> getUserScoreRecore(@Param("userId") int userid, @Param("taskId")int taskid,@Param("operateId") int operateid);

    //@Select("select * from user_score_record where user_id=#{userId} and task_id =#{taskId} and opreate_id")
}