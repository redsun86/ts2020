package com.esst.ts.service;

import com.esst.ts.model.Task;

import java.util.List;

public interface FZhKTService {

    List<Task> getCourseTaskLst(int courseID);
    List<Task> getCourseTaskLstDemo(int courseID);
    //boolean updatescore(UserLive score);
}
