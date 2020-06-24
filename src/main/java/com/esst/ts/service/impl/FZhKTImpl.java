package com.esst.ts.service.impl;

import com.esst.ts.dao.*;
import com.esst.ts.model.*;
import com.esst.ts.service.FZhKTService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
    @Resource
    private  ExamMapper examMapper;
    @Resource
    private  QuestionsMapper questionsMapper;
    @Resource
    private  UserLoginLogMapper userLoginLogMapper;
    @Resource
    private  UserTokenDao userTokenDao;
    @Resource UserScoreRecordMapper userScoreRecordMapper;
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
    public UserLiveWithBLOBs updateUserLive(UserLiveWithBLOBs userlive) {
        UserLiveWithBLOBs ul=new UserLiveWithBLOBs();
        ul= userliveScore.getUserLiveByUserTaskType(userlive.getUserId(),userlive.getTaskId(),userlive.getStudyType());

        if(ul!=null) {
            userlive.setId(ul.getId());
            if(userlive.getScoreStatues()==1||userlive.getScoreStatues()==2){

                double duration = new Date().getTime() - ul.getStartTime().getTime();
                userlive.setStudyDuration(duration);
                userlive.setStartTime(ul.getStartTime());
            }
            userliveScore.updateByPrimaryKey(userlive);
        }else{
            userliveScore.insert(userlive);
        }
        return userlive;
    }
    //获取所有试题表
    @Override
    public List<Exam> getExamListAll() {
        return examMapper.getExamListAll();
    }

    @Override
    public List<Questions> getQuestionListAll() {
        return questionsMapper.getQuestionAll();
    }

    @Override
    public List<UserLiveWithBLOBs> getUserLiveAll() {
        return userliveScore.getUserLiveAll();
    }

    @Override
    public List<UserLiveWithBLOBs> getUserLiveByTeacherId(String userId) {
        return userliveScore.getUserLiveByTeacherId(userId);
    }

    @Override
    public List<UserToken> getUserLoginByTeacherID(String userId) {
        //return userLoginLogMapper.getUserLoginByTeacherID(userId);
        return  userTokenDao.getUserLoginByTeacherID(userId);
    }

    @Override
    public int getUserLoginLogCountByTeacherID(String userId) {
        return userLoginLogMapper.getUserLoginLogCountByTeacherID(userId);
    }

    @Override
    public void userlivedataTorecord(int userId) {
        List<UserLiveDataWithBLOBs> userLiveDataList = userlivedata.getUserlivaByteacherid(userId);
        //先单条插入，以后改成按list批量的 crq
        if (userLiveDataList!=null) {
            for (UserLiveDataWithBLOBs uld : userLiveDataList) {
                UserScoreRecord usr = new UserScoreRecord();
                usr.setUserId(uld.getUserId());
                usr.setTaskId(uld.getTaskId());
                usr.setOperateId(uld.getOperateId());
                usr.setScore(uld.getCurrentScore());
                usr.setTotalScore(uld.getTotalScore());
                usr.setBeginTime(uld.getStartTime().getTime());
                usr.setEndTime(uld.getStartTime().getTime() + uld.getStudyDuration().longValue());
                usr.setStudyType(uld.getStudyType());
                usr.setMacAddress(uld.getMacAddress());
                usr.setIpAddress(uld.getIdAddress());
                userScoreRecordMapper.insert(usr);
            }
            userlivedata.deletUserlivaByteacherid(userId);
            userliveScore.deletUserlivaByteacherid(userId);
        }
    }

    @Override
    public double getTaskTotal_score(UserLiveWithBLOBs ul) {
        //如果uld正在做
        double totalscore=0;
        if(ul.getScoreStatues()!=2)
        {
            double maxscore=FZhKTMapper.getOperteMaxScore(ul);
            if (maxscore==-1||ul.getCurrentScore()>maxscore){
                totalscore=FZhKTMapper.getTotalcoreWhithooutUserLive(ul)+ul.getCurrentScore();
            }
            else {
                totalscore=FZhKTMapper.getTotalcoreByUserLive(ul);
            }
        }
        //如果已经做完
        else {
            totalscore=FZhKTMapper.getTotalcoreByUserLive(ul);
        }
        return totalscore;
    }

    @Override
    public List<scoreModel> getscoreModelList(String user_id, String template_id) {
        return FZhKTMapper.getscoreModelList(user_id,template_id);
    }
}
