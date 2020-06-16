package com.esst.ts.service.impl;

import com.esst.ts.dao.*;
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Resource
    private UserScoreRecordMapper userScoreRecord;
    @Resource
    private TaskMapper task;
    @Resource
    private OperateMapper operate;
    @Resource
    private  UserMapper user;
    @Override
    public List<Task> getCourseTaskLst(int courseID) {
        return FZhKTMapper.getCourseTaskLstBytechId(courseID);
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
    public int insertUserScoreRecore(UserScoreRecord score) {
        return userScoreRecord.insert(score);
    }

    @Override
    public int updateUserScoreRecored(UserScoreRecord usr) {

        return userScoreRecord.updateByPrimaryKey(usr);
    }

    @Override
    public List<UserScoreRecord> getUserScoreRecore(int userid, int taskid, int operateid) {
        return  userScoreRecord.getUserScoreRecore( userid,  taskid, operateid);
    }

    public FZhKTImpl() {
        super();
    }

    @Override
    public List<UserLiveData> getUserLiveDataList()
    {
       return  userlivedata.getUserLiveDataDistinct();
    }

    @Override
    public List<UserLive> getUserLiveList() {
        return userliveScore.geUserLiveDistinct();
    }

    @Override
    public List<Task> getTaskListAll() {
        return FZhKTMapper.gteTaskListAll();
    }

    @Override
    public List<Operate> getOprateList() {
        return operate.getOperateListAll();
    }

    @Override
    public List<User> getUserListAll() {
        return user.getUserListAll();
    }

    @Override
    public List<scoreModel> getscoreModelList(String user_id, String template_id) {
        return FZhKTMapper.getscoreModelList(user_id,template_id);
    }
}
