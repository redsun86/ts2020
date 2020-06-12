package com.esst.ts.service;

import com.esst.ts.model.*;

import java.util.List;

public interface FZhKTService {

    List<Task> getCourseTaskLst(int courseID);
    List<Task> getCourseTaskLstDemo(int courseID);
    boolean inserUserLiveWithBLOBS(UserLiveWithBLOBs score);
    boolean insertUserLiveDataWithBLOBS(UserLiveDataWithBLOBs score);
    boolean insertUserScoreRecore(UserScoreRecord score);
}
