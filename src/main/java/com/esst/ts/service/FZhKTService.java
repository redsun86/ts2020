package com.esst.ts.service;

import com.esst.ts.model.courseTaskModel;

import java.util.List;

public interface FZhKTService {

    List<courseTaskModel> getCourseTaskLst(int courseID);
    List<courseTaskModel> getCourseTaskLstDemo(int courseID);

}
