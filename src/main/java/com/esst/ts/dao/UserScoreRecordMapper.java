package com.esst.ts.dao;

import com.esst.ts.model.UserScoreRecord;
import com.esst.ts.model.UserScoreRecordPOJO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserScoreRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserScoreRecord record);

    int insertSelective(UserScoreRecord record);

    UserScoreRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserScoreRecord record);

    int updateByPrimaryKey(UserScoreRecord record);

    @Select("select * from user_score_record where user_id=#{userId} and task_id =#{taskId} and operate_id =#{operateId} and end_time IS NULL")
    List<UserScoreRecord> getUserScoreRecore(@Param("userId") int userid, @Param("taskId")int taskid,@Param("operateId") int operateid);

    @Select("select * from user_score_record where FROM_UNIXTIME(begin_time/1000, '%Y-%m-%d')>=#{beginDate} and FROM_UNIXTIME(begin_time/1000, '%Y-%m-%d')<=#{endDate} and user_id in(SELECT student_id from teacher_student_relation where teacher_id=#{userId})")
    List<UserScoreRecord> getUserStudyRecord(@Param("beginDate") String beginDate,@Param("endDate") String endDate,@Param("userId") int userId);

    @Select("SELECT r.*,r.end_time-r.begin_time as lear_time,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} AND r.task_id=#{taskId} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY total_score,begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfo(@Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("taskId") int taskId);

    @Select("SELECT r.*,r.end_time-r.begin_time as lear_time,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY total_score,begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoAll(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    @Select("SELECT r.*,r.end_time-r.begin_time as lear_time,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} AND r.user_id IN (SELECT student_id FROM teacher_student_relation WHERE teacher_id =#{userId}) GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY total_score,begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoByUserIdAll(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("userId") int userId);

    @Select("SELECT r.*,r.end_time-r.begin_time as lear_time,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} AND r.user_id IN (SELECT student_id FROM teacher_student_relation WHERE teacher_id =#{userId}) AND r.task_id=#{taskId} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY total_score,begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoBytaskId(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("userId") int userId,@Param("taskId") int taskId);

    @Select("SELECT r.*,r.end_time-r.begin_time as lear_time,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} AND r.task_id=#{taskId} AND r.study_type=#{studyType} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY total_score,begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfos(@Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("taskId") int taskId,@Param("studyType") String studyType);

    @Select("SELECT r.*,r.end_time-r.begin_time as lear_time,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} AND r.study_type=#{studyType} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY total_score,begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoAlls(@Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("studyType") String studyType);

    @Select("SELECT r.*,r.end_time-r.begin_time as lear_time,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} AND r.user_id IN (SELECT student_id FROM teacher_student_relation WHERE teacher_id =#{userId}) AND r.study_type=#{studyType} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY total_score,begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoByUserIdAlls(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("userId") int userId,@Param("studyType") String studyType);

    @Select("SELECT r.*,r.end_time-r.begin_time as lear_time,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} AND r.user_id IN (SELECT student_id FROM teacher_student_relation WHERE teacher_id =#{userId}) AND r.task_id=#{taskId} AND r.study_type=#{studyType} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY total_score,begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoBytaskIds(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("userId") int userId,@Param("taskId") int taskId,@Param("studyType") String studyType);
    //@Select("select * from user_score_record where user_id=#{userId} and task_id =#{taskId} and opreate_id")
}