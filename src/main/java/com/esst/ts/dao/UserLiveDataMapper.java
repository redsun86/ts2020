package com.esst.ts.dao;

import com.esst.ts.model.UserLiveData;
import com.esst.ts.model.UserLiveDataWithBLOBs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserLiveDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLiveDataWithBLOBs record);

    int insertSelective(UserLiveDataWithBLOBs record);

    UserLiveDataWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLiveDataWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UserLiveDataWithBLOBs record);

    int updateByPrimaryKey(UserLiveData record);

    @Select("select ud.* from (SELECT uld.user_id,max(uld.id) id FROM `user_live_data` uld GROUP BY uld.user_id) g \n" +
            "LEFT JOIN   `user_live_data` ud ON ud.id=g.id;\n ")
    @ResultMap("BaseResultMap")
    List<UserLiveData> getUserLiveDataDistinct();

    @Select("select u.* from user_live_data u LEFT JOIN teacher_student_relation t on u.user_id=t.student_id WHERE t.teacher_id=#{userId} AND u.score_statues=2")
    @ResultMap("ResultMapWithBLOBs")
    List<UserLiveDataWithBLOBs> getUserlivaByteacherid(@Param("userId") int userId);
//    @Select("select u.* from user_live_data u LEFT JOIN teacher_student_relation t on u.user_id=t.student_id WHERE t.teacher_id=#{userId} AND u.score_statues=2")
//    @ResultMap("ResultMapWithBLOBs")
    @Delete("delete from user_live_data where user_id in (select student_id from teacher_student_relation WHERE teacher_id=#{userId})")
    int deletUserliveByteacherid(@Param("userId") int userId);
    @Select("SELECT SUM(uld.study_duration) study_duration ,MAX(current_score) current_score,uld.* from user_live_data uld RIGHT JOIN \n" +
            "(SELECT MAX(u.id) id from user_live_data u LEFT JOIN teacher_student_relation t on u.user_id=t.student_id WHERE t.teacher_id=#{userId} and u.study_type=#{studyType} and u.task_id=#{taskId} GROUP BY train_id) e ON uld.id=e.id GROUP BY task_id,operate_id ")
    @ResultMap("ResultMapWithBLOBs")
    List<UserLiveDataWithBLOBs> getOperateMaxScore(@Param("userId") int userId,@Param("taskId") int taskId, @Param("studyType") int studyType);

    @Select("SELECT SUM(x.study_duration1) as study_duration,SUM(x.total_score1) as total_score,x.* FROM \n" +
            "(SELECT SUM(uld.study_duration) study_duration1 ,(case when uld.study_type = 0 THEN MAX(current_score) WHEN uld.study_type=1 THEN total_score END) total_score1,uld.* from user_live_data uld" +
            " RIGHT JOIN  (SELECT MAX(u.id) id from user_live_data u WHERE IF(#{userId}='',0=0,teacher_id=#{userId} OR teacher_id =#{guestId}) and IF(#{taskId}='',0=0,task_id=#{taskId} AND study_type=#{studyType}) GROUP BY train_id) e" +
            " ON uld.id=e.id GROUP BY task_id,operate_id,user_id) x \n" +
            "GROUP BY task_id,user_id")
    @ResultMap("ResultMapWithBLOBs")
    List<UserLiveDataWithBLOBs> getRealTimeByTeacherId(@Param("userId") String userId,@Param("guestId") String guestId,@Param("taskId") String taskId,@Param("studyType") String studyType);
}