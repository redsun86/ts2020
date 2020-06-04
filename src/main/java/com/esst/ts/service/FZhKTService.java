package com.esst.ts.service;

import com.esst.ts.model.courseTaskModel;

public interface FZhKTService {

    courseTaskModel getCourseTask(String courseID);
    courseTaskModel getCourseTaskDemo(String courseID);

}
