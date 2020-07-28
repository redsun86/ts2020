package com.esst.ts.dao;

import com.esst.ts.model.ScoreDetailPOJO;
import com.esst.ts.model.Task;
import com.esst.ts.model.UserLiveWithBLOBs;
import com.esst.ts.model.scoreModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FZhKTMapper {

    @Select("select * from task where binary technology_id=#{technology_id} ")
    @ResultMap("BaseResultMap")
    List<Task> getCourseTaskLstBytechId(@Param("technology_id") int technology_id);
    @Select("select * from task")
    @ResultMap("BaseResultMap")
    List<Task> gteTaskListAll();
    List<scoreModel> getscoreModelList(String user_id, String template_id);
    @Select("select CAST(IFNULL(sum(bv.current_score),0) AS DECIMAL(8,2)) current_score from(\n" +
            "SELECT MAX(current_score) current_score FROM `user_live_data` \n" +
            "WHERE user_id=#{userId} AND task_id=#{taskId} AND study_type=#{studyType} AND operate_id <>#{operateId} and score_statues='2' \n" +
            "GROUP BY operate_id) bv ;")
    double getTotalcoreWhithooutUserLive(UserLiveWithBLOBs uld);
    @Select("SELECT CAST(IFNULL(MAX(current_score),-1) AS DECIMAL(8,2)) FROM `user_live_data` WHERE task_id=#{taskId} AND study_type=#{studyType} AND score_statues='2' AND user_id=#{userId} AND operate_id=#{operateId}")
    Double getOperteMaxScore(UserLiveWithBLOBs ul);
    @Select("select  CAST(IFNULL(sum(bv.current_score),0) AS DECIMAL(8,2)) current_score from(\n" +
            "SELECT MAX(current_score) current_score FROM `user_live_data` \n" +
            "WHERE user_id=#{userId} AND task_id=#{taskId} AND study_type=#{studyType} and score_statues='2' \n" +
            "GROUP BY operate_id) bv ;")
    double getTotalcoreByUserLive(UserLiveWithBLOBs ul);

    @Select("SELECT date_format(FROM_UNIXTIME(start_time/1000),'%Y.%m.%d %H:%i:%s') as studyDate,operate_id operateId,ROUND(study_duration/1000,2) learnTime,current_score score,train_id trainId FROM `user_live_data` WHERE id in(SELECT MAX(u.id) id from user_live_data u WHERE teacher_id=#{teacherId} and user_id=#{userId} and task_id=#{taskId} AND study_type=#{studyType} GROUP BY train_id)")
    List<ScoreDetailPOJO> getDetailScoreList(@Param("teacherId") int teacherId,@Param("userId") int userId, @Param("taskId") int taskId,@Param("studyType") int studyType);
}
