package com.esst.ts.dao;

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
    @Select("select CAST(IFNULL(sum(bv.current_score),0) AS DECIMAL) current_score from(\n" +
            "SELECT MAX(current_score) current_score FROM `user_live_data` \n" +
            "WHERE user_id=#{userId} AND task_id=#{taskId} AND study_type=#{studyType} AND operate_id <>#{operateId} and score_statues='2' \n" +
            "GROUP BY operate_id) bv ;")
    double getTotalcoreWhithooutUserLive(UserLiveWithBLOBs uld);
    @Select("SELECT CAST(IFNULL(MAX(current_score),-1) AS DECIMAL) FROM `user_live_data` WHERE task_id=#{taskId} AND study_type=#{studyType} AND score_statues='2' AND user_id=#{userId} AND operate_id=#{operateId}")
    Double getOperteMaxScore(UserLiveWithBLOBs ul);
    @Select("select  CAST(IFNULL(sum(bv.current_score),0) AS DECIMAL) current_score from(\n" +
            "SELECT MAX(current_score) current_score FROM `user_live_data` \n" +
            "WHERE user_id=#{userId} AND task_id=#{taskId} AND study_type=#{studyType} and score_statues='2' \n" +
            "GROUP BY operate_id) bv ;")
    double getTotalcoreByUserLive(UserLiveWithBLOBs ul);
}
