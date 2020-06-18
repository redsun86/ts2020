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

    UserLiveWithBLOBs updateUserLive(UserLiveWithBLOBs userlive,int client_status);

    List<Exam> getExamListAll();
}
