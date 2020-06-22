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

    List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfo(String beginDate, String endDate, Integer userId);

}