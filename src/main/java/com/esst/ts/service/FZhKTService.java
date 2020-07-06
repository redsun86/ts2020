package com.esst.ts.service;

import com.esst.ts.model.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FZhKTService {

    List<Task> getCourseTaskLst(int courseID);

    boolean inserUserLiveWithBLOBS(UserLiveWithBLOBs score);

    boolean insertUserLiveDataWithBLOBS(UserLiveDataWithBLOBs score);

    int insertUserScoreRecore(UserScoreRecord score);

    int updateUserScoreRecored(UserScoreRecord usr);

    List<UserScoreRecord> getUserScoreRecore(int userid,int taskid,int operateid);
    List<scoreModel> getscoreModelList(String user_id,String template_id);

    List<UserLiveData> getUserLiveDataList();
    List<UserLive> getUserLiveList();
    List<Task> getTaskListAll();
    List<Operate> getOprateList();
    List<User> getUserListAll();

    UserLiveWithBLOBs updateUserLive(UserLiveWithBLOBs userlive);

    List<Exam> getExamListAll();

    List<Questions> getQuestionListAll();

    List<UserLiveWithBLOBs> getUserLiveAll();

    List<UserLiveWithBLOBs> getUserLiveByTeacherId(String userId);
    //这个函数有待优化20200619
    List<UserToken> getUserLoginByTeacherID(String userId);

    int getUserLoginLogCountByTeacherID(String userId);

    void userlivedataTorecord(int userId);
    int deletelivedataTorecord(int userId);
    double getTaskTotal_score(UserLiveWithBLOBs uld);
    List<UserLive> checkIsRecordByTeacherId(String beginDate,String endDate,int userID);
    List<ScoreDetailPOJO> getScoreDetailList(UserLiveWithBLOBs id);

    int updateUserScoreRecoredByTrainID(UserScoreRecord usrScore);

    List<UserLiveDataWithBLOBs> getOperateMaxScore(int userId, int taskId, int studyType);

    ResponseEntity<byte[]> exportReatimeScore(List<RealTimeEcxelPOJO> scoreexcelList);

    List<UserLoginLog> getUserLoginLogeacherID(int userId);
}
