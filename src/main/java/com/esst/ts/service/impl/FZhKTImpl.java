package com.esst.ts.service.impl;

import com.esst.ts.dao.UserLiveDataMapper;
import com.esst.ts.dao.UserLiveMapper;
import com.esst.ts.dao.UserScoreRecordMapper;
import com.esst.ts.model.*;
import com.esst.ts.service.FZhKTService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FZhKTImpl implements FZhKTService {


    @Resource
    private com.esst.ts.dao.FZhKTMapper FZhKTMapper;
    @Resource
    private UserLiveMapper userliveScore;
    @Resource
    private UserLiveDataMapper userlivedata;
    @Resource
    private UserScoreRecordMapper userScoreRecord;
    @Override
    public List<Task> getCourseTaskLst(int courseID) {
        return FZhKTMapper.getCourseTaskLst(courseID);
    }

    @Override
    public List<Task> getCourseTaskLstDemo(int courseID) {
        return FZhKTMapper.getCourseTaskLstDemo(courseID);
    }

    @Override
    public boolean inserUserLiveWithBLOBS(UserLiveWithBLOBs score) {
        userliveScore.insert(score);
        return true;
    }

    @Override
    public boolean insertUserLiveDataWithBLOBS(UserLiveDataWithBLOBs score) {
        userlivedata.insert(score);
        return true;
    }

    @Override
    public boolean insertUserScoreRecore(UserScoreRecord score) {
        userScoreRecord.insert(score);
        return false;
    }
}
