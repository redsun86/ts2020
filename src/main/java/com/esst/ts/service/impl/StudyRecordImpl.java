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
    public List<UserLoginLogPOJO> getDistinctDateForDate(Integer year, Integer month) {
        return userLoginLogMapper.getDistinctDateForDate(year,month);
    }

    @Override
    public List<UserScoreRecord> getUserStudyRecord(String beginDate,String endDate,Integer userId) {
        return userScoreRecordMapper.getUserStudyRecord(beginDate,endDate,userId);
    }

    @Override
    public List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoTask(String beginDate, String endDate,Integer userId) {
        if(userId==0){
            return userScoreRecordMapper.getUserStudyRecordAndUserInfoAll(beginDate, endDate);
        }
        else{
            return userScoreRecordMapper.getUserStudyRecordAndUserInfoByUserAndDate(beginDate, endDate,userId);
        }
    }
    @Override
    public List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfo(String beginDate, String endDate, Integer userId,String studyType,Integer taskId) {
        if(studyType== null || studyType.length() == 0){
            if(userId==0){
                if(taskId==0){
                    return userScoreRecordMapper.getUserStudyRecordAndUserInfoAll(beginDate, endDate);
                }
                else {
                    return userScoreRecordMapper.getUserStudyRecordAndUserInfo(beginDate, endDate,taskId);
                }
            }
            else{
                if(taskId==0){
                    return userScoreRecordMapper.getUserStudyRecordAndUserInfoByUserIdAll(beginDate, endDate, userId);
                }
                else {
                    return userScoreRecordMapper.getUserStudyRecordAndUserInfoBytaskId(beginDate, endDate, userId,taskId);
                }
            }
        }else
        {
            if(userId==0){
                if(taskId==0){
                    return userScoreRecordMapper.getUserStudyRecordAndUserInfoAlls(beginDate, endDate,studyType);
                }
                else {
                    return userScoreRecordMapper.getUserStudyRecordAndUserInfos(beginDate, endDate,taskId,studyType);
                }
            }
            else{
                if(taskId==0){
                    return userScoreRecordMapper.getUserStudyRecordAndUserInfoByUserIdAlls(beginDate, endDate, userId,studyType);
                }
                else {
                    return userScoreRecordMapper.getUserStudyRecordAndUserInfoBytaskIds(beginDate, endDate, userId,taskId,studyType);
                }
            }
        }
    }

    @Override
    public List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoforPerson(String userId,Integer taskId,String studyType) {
        if(studyType==null || studyType.length()==0){
            if(taskId==0){
                return userScoreRecordMapper.getUserStudyRecordAndUserInfoforPerson(userId);
            }
            else{
                return userScoreRecordMapper.getUserStudyRecordAndUserInfoforPersonByTaskId(userId,taskId);
            }
        }
        else{
            if(taskId==0){
                return userScoreRecordMapper.getUserStudyRecordAndUserInfoforPersonAndStudyType(userId,studyType);
            }
            else{
                return userScoreRecordMapper.getUserStudyRecordAndUserInfoforPersonByTaskIdAndStudyType(userId,taskId,studyType);
            }
        }
    }

    @Override
    public List<UserScoreRecordPOJO> getUserStudyRecordAndUserInfoforPersonTask(String userId) {
        return userScoreRecordMapper.getUserStudyRecordAndUserInfoforPerson(userId);
    }

    @Override
    public List<UserScoreRecordPOJO> getoperateid(String beginDate, Integer userId, Integer taskId) {
        return userScoreRecordMapper.getoperateid(beginDate, userId, taskId);
    }

    @Override
    public List<UserScoreRecordPOJO> getexamscore(String beginDate, Integer userId, Integer taskId) {
        return userScoreRecordMapper.getexamscore(beginDate, userId, taskId);
    }

    @Override
    public List<UserScoreRecordPOJO> getmaxscore(String beginDate, Integer userId, Integer taskId, Integer operateId) {
        return userScoreRecordMapper.getmaxscore(beginDate, userId, taskId,operateId);
    }

    @Override
    public List<UserScoreRecordPOJO> getLearnTime(String beginDate, Integer userId, Integer taskId) {
        return userScoreRecordMapper.getLearnTime(beginDate, userId, taskId);
    }

    @Override
    public List<UserScoreRecordPOJO> getUserStudyRecordDetail(String date, Integer userId, Integer taskId,Integer studyType,Integer teacherId) {
        if(teacherId==0){
            return userScoreRecordMapper.getPersonUserStudyRecordDetail(date, userId, taskId, studyType);
        }else {
            return userScoreRecordMapper.getUserStudyRecordDetail(date, userId, taskId, studyType, teacherId);
        }
    }
}
