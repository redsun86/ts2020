package com.esst.ts.service;

import com.esst.ts.model.UserLoginLog;
import com.esst.ts.model.UserLoginLogPOJO;
import com.esst.ts.model.UserScoreRecord;
import com.esst.ts.model.UserScoreRecordPOJO;

import java.util.List;

public interface StudyRecordService {

    List<UserLoginLog> getUserloginLogForDate(String beginDate, String endDate);

    List<UserLoginLogPOJO> getDistinctDateForDate(String beginDate, String endDate);

    List<UserScoreRecord> getUserStudyRecord(String beginDate,String endDate,Integer userId);

    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfo(String beginDate, String endDate, Integer userId,String studyType,Integer taskId);

    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoforPerson(String userId,Integer taskId,String studyType);

    List<UserScoreRecordPOJO> getoperateid(String beginDate, Integer userId,Integer taskId);

    List<UserScoreRecordPOJO> getmaxscore(String beginDate, Integer userId,Integer taskId,Integer operateId);

    List<UserScoreRecordPOJO> getLearnTime(String beginDate, Integer userId,Integer taskId);

    List<UserScoreRecordPOJO> getUserStudyRecordDetail(String date, Integer userId,Integer taskId,Integer studyType);
}
