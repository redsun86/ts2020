package com.esst.ts.service;

import com.esst.ts.model.*;

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
}
