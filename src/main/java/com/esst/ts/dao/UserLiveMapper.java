package com.esst.ts.dao;

import com.esst.ts.model.UserLive;
import com.esst.ts.model.UserLiveWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserLiveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLiveWithBLOBs record);

    int insertSelective(UserLiveWithBLOBs record);

    UserLiveWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLiveWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UserLiveWithBLOBs record);

    int updateByPrimaryKey(UserLive record);

    @Select("select ud.* from (SELECT uld.user_id,max(uld.id) id FROM `user_live` uld  GROUP BY uld.user_id) g \n" +
            "LEFT JOIN   `user_live` ud ON ud.id=g.id;\n ")
    @ResultMap("BaseResultMap")
    List<UserLive> geUserLiveDistinct();
    @Select("select ud.* from (SELECT uld.user_id,max(uld.id) id FROM `user_live` uld Where GROUP BY uld.user_id) g \n" +
            "LEFT JOIN   `user_live` ud ON ud.id=g.id;\n ")
    @ResultMap("BaseResultMap")
    List<UserLive> getUserLiveListExcludeTaskID(String taskid,String studytype);

    @Select("select * from user_live Where user_id=#{userId} and task_id=#{taskId} and study_type=#{studyType}")
    @ResultMap("ResultMapWithBLOBs")
    UserLiveWithBLOBs getUserLiveByUserTaskType(@Param("userId") Integer userId, @Param("taskId") Integer taskId,@Param("studyType") Integer studyType);
    @Select("select * from user_live")
    @ResultMap("ResultMapWithBLOBs")
    List<UserLiveWithBLOBs> getUserLiveAll();
    @Select("SELECT u.* FROM user_live u LEFT JOIN teacher_student_relation t ON u.user_id=t.student_id WHERE t.teacher_id=#{teacherId}")
    @ResultMap("ResultMapWithBLOBs")
    List<UserLiveWithBLOBs> getUserLiveByTeacherId(@Param("teacherId") String userId);
}