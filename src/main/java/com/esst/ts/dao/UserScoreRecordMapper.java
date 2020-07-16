package com.esst.ts.dao;

import com.esst.ts.model.UserScoreRecord;
import com.esst.ts.model.UserScoreRecordPOJO;
import org.apache.ibatis.annotations.Insert;
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

    @Select("SELECT r.*,r.end_time-r.begin_time as learn_time,FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') as studyDate,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} AND r.task_id=#{taskId} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfo(@Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("taskId") int taskId);

    @Select("SELECT r.*,r.end_time-r.begin_time as learn_time,FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') as studyDate,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoAll(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

    @Select("SELECT r.*,r.end_time-r.begin_time as learn_time,FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') as studyDate,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} AND r.user_id IN (SELECT student_id FROM teacher_student_relation WHERE teacher_id =#{userId}) GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoByUserAndDate(@Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("userId") int userId);

    @Select("SELECT r.*,r.end_time-r.begin_time as learn_time,FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') as studyDate,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} AND r.user_id IN (SELECT student_id FROM teacher_student_relation WHERE teacher_id =#{userId}) GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoByUserIdAll(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("userId") int userId);

    @Select("SELECT r.*,r.end_time-r.begin_time as learn_time,FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') as studyDate,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} AND r.user_id IN (SELECT student_id FROM teacher_student_relation WHERE teacher_id =#{userId}) AND r.task_id=#{taskId} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoBytaskId(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("userId") int userId,@Param("taskId") int taskId);

    @Select("SELECT r.*,r.end_time-r.begin_time as learn_time,FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') as studyDate,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} AND r.task_id=#{taskId} AND r.study_type=#{studyType} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfos(@Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("taskId") int taskId,@Param("studyType") String studyType);

    @Select("SELECT r.*,r.end_time-r.begin_time as learn_time,FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') as studyDate,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} AND r.study_type=#{studyType} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoAlls(@Param("beginDate") String beginDate, @Param("endDate") String endDate,@Param("studyType") String studyType);

    @Select("SELECT r.*,r.end_time-r.begin_time as learn_time,FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') as studyDate,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} AND r.user_id IN (SELECT student_id FROM teacher_student_relation WHERE teacher_id =#{userId}) AND r.study_type=#{studyType} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoByUserIdAlls(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("userId") int userId,@Param("studyType") String studyType);

    @Select("SELECT r.*,r.end_time-r.begin_time as learn_time,FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') as studyDate,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') >=#{beginDate} AND FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') <= #{endDate} AND r.user_id IN (SELECT student_id FROM teacher_student_relation WHERE teacher_id =#{userId}) AND r.task_id=#{taskId} AND r.study_type=#{studyType} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoBytaskIds(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("userId") int userId,@Param("taskId") int taskId,@Param("studyType") String studyType);

    @Select("select *  from user_score_record where user_id=#{userId} and FROM_UNIXTIME(begin_time / 1000,'%Y-%m-%d')=#{beginDate} and task_id=#{taskId} GROUP BY operate_id")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getoperateid(@Param("beginDate") String beginDate,@Param("userId") int userId,@Param("taskId") int taskId);

    @Select("select max(score) as score,begin_time,end_time  from user_score_record where user_id=#{userId} and FROM_UNIXTIME(begin_time / 1000,'%Y-%m-%d')=#{beginDate} and task_id=#{taskId} and operate_id=#{operateId}")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getmaxscore(@Param("beginDate") String beginDate,@Param("userId") int userId,@Param("taskId") int taskId,@Param("operateId") int operateId);

    @Select("select *  from user_score_record where user_id=#{userId} and FROM_UNIXTIME(begin_time / 1000,'%Y-%m-%d')=#{beginDate} and task_id=#{taskId}")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getLearnTime(@Param("beginDate") String beginDate,@Param("userId") int userId,@Param("taskId") int taskId);

    @Select("select id,user_id,task_id,operate_id,score,end_time - begin_time AS learn_time,FROM_UNIXTIME(begin_time / 1000,'%Y-%m-%d %H:%i:%s') AS studyDate,study_type from user_score_record where FROM_UNIXTIME(begin_time / 1000,'%Y-%m-%d') >=#{date} AND FROM_UNIXTIME(begin_time / 1000,'%Y-%m-%d') <=#{date} AND user_id=#{userId} and task_id=#{taskId} and study_type=#{studyType} ORDER BY id ASC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordDetail(@Param("date") String date,@Param("userId") int userId,@Param("taskId") int taskId,@Param("studyType") int studyType);

    @Select("SELECT r.*, r.end_time - r.begin_time AS learn_time,FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') as studyDate,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE user_id in(#{userId}) GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY total_score,begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoforPerson(@Param("userId") String userId);

    @Select("SELECT r.*, r.end_time - r.begin_time AS learn_time,FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') as studyDate,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE user_id in(#{userId}) AND r.task_id=#{taskId} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY total_score,begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoforPersonByTaskId(@Param("userId") String userId,@Param("taskId") int taskId);


    @Select("SELECT r.*, r.end_time - r.begin_time AS learn_time,FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') as studyDate,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE user_id in(#{userId}) AND r.task_id=#{taskId} AND r.study_type=#{studyType} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY total_score,begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoforPersonByTaskIdAndStudyType(@Param("userId") String userId,@Param("taskId") int taskId,@Param("studyType") String studyType);

    @Select("SELECT r.*, r.end_time - r.begin_time AS learn_time,FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d') as studyDate,u.rel_name,u.st_num,u.class_name,u.group_name FROM user_score_record r LEFT JOIN `user` u ON u.id = r.user_id WHERE user_id in(#{userId}) AND r.study_type=#{studyType} GROUP BY FROM_UNIXTIME(r.begin_time / 1000,'%Y-%m-%d'),task_id,user_id ORDER BY total_score,begin_time DESC")
    @ResultMap("BasePOJOResultMap")
    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoforPersonAndStudyType(@Param("userId") String userId,@Param("studyType") String studyType);

    @Insert(" insert into user_score_record (user_id, task_id, \n" +
            "operate_id, score, total_score, \n" +
            "begin_time, end_time, study_type,\n" +
            "mac_address, ip_address,train_id)\n" +
            "values (#{userId,jdbcType=INTEGER}, #{taskId,jdbcType=INTEGER}, \n" +
            "#{operateId,jdbcType=INTEGER}, #{score,jdbcType=DOUBLE}, #{totalScore,jdbcType=DOUBLE}, \n" +
            " #{beginTime,jdbcType=BIGINT}, #{endTime,jdbcType=BIGINT}, #{studyType,jdbcType=INTEGER},\n" +
            " #{macAddress,jdbcType=VARCHAR}, #{ipAddress,jdbcType=VARCHAR},#{trainId,jdbcType=VARCHAR}\n" +
            " )" +
            "ON DUPLICATE KEY UPDATE score=#{score,jdbcType=DOUBLE},total_score=#{totalScore},end_time=#{endTime}")
    int updateUserScoreRecoredByTrainID(UserScoreRecord usrScore);


    //@Select("select * from user_score_record where user_id=#{userId} and task_id =#{taskId} and opreate_id")
}