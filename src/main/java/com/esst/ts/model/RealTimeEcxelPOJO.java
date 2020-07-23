package com.esst.ts.model;

import com.esst.ts.constants.Constants;

import java.util.*;

public class RealTimeEcxelPOJO {
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    String taskName;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    String teacherName;

    public Constants.StudyType getStudyType() {
        return studyType;
    }

    public void setStudyType(Constants.StudyType studyType) {
        this.studyType = studyType;
    }

    //学习类型Task任务单，Exam试卷
    public Constants.StudyType studyType;
    public List<String> classList = new ArrayList<>();
    public List<String> operateList = new ArrayList<>();
    public Map<Integer,Integer>operateIndexList= new HashMap<>();
    public Map<Integer, RealTimeExcelItemPOJO> realTimeExcelItemPOJOHashMap = new HashMap<>();
    //老师导入名单外学员
    public Map<Integer, RealTimeExcelItemPOJO> realTimeExcelItemPOJOHashMapWithoutTeacher = new HashMap<>();
    public HashSet<String> ClassNameMap = new HashSet<>();
}
