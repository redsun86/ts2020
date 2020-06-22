package com.esst.ts.service.impl;

import com.esst.ts.dao.UserLoginLogMapper;
import com.esst.ts.dao.UserScoreRecordMapper;
import com.esst.ts.model.UserLoginLog;
import com.esst.ts.model.UserLoginLogPOJO;
import com.esst.ts.model.UserScoreRecord;
import com.esst.ts.model.UserScoreRecordPOJO;
import com.esst.ts.service.StudyRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class StudyRecordImpl implements StudyRecordService {

    @Resource
    private UserLoginLogMapper userLoginLogMapper;

    @Resource
    private UserScoreRecordMapper userScoreRecordMapper;

    @Override
    public List<UserLoginLog> getUserloginLogForDate(String beginDate, String endDate) {
        return userLoginLogMapper.getUserloginLogForDate(beginDate,endDate);
    }

    @Override
    public List<UserLoginLogPOJO> getDistinctDateForDate(String beginDate, String endDate) {
        return userLoginLogMapper.getDistinctDateForDate(beginDate,endDate);
    }

    @Override
    public List<UserScoreRecord> getUserStudyRecord(String beginDate,String endDate,Integer userId) {
        return userScoreRecordMapper.getUserStudyRecord(beginDate,endDate,userId);
    }

    @Override
    public List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfo(String beginDate, String endDate, Integer userId) {
        return userScoreRecordMapper.getUserStudyRecordAndUserInfo(beginDate,endDate,userId);
    }

}
