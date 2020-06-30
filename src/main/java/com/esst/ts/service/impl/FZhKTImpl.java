package com.esst.ts.service.impl;

import com.esst.ts.constants.Constants;
import com.esst.ts.dao.*;
import com.esst.ts.model.*;
import com.esst.ts.service.FZhKTService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.esst.ts.constants.Constants.StudyType;

@Service
@Transactional(rollbackFor = Exception.class)
public class FZhKTImpl implements FZhKTService {


    @Resource
    private com.esst.ts.dao.FZhKTMapper FZhKTMapper;
    @Resource
    public UserLiveMapper userliveScore;
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
    private UserMapper user;
    @Resource
    private ExamMapper examMapper;
    @Resource
    private QuestionsMapper questionsMapper;
    @Resource
    private UserLoginLogMapper userLoginLogMapper;
    @Resource
    private UserTokenDao userTokenDao;
    @Resource
    UserScoreRecordMapper userScoreRecordMapper;

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
        return userScoreRecord.getUserScoreRecore(userid, taskid, operateid);
    }

    public FZhKTImpl() {
        super();
    }

    @Override
    public List<UserLiveData> getUserLiveDataList() {
        return userlivedata.getUserLiveDataDistinct();
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
        UserLiveWithBLOBs ul = new UserLiveWithBLOBs();
        ul = userliveScore.getUserLiveByUserTaskType(userlive.getUserId(), userlive.getTaskId(), userlive.getStudyType());

        if (ul != null) {
            userlive.setId(ul.getId());
            if (userlive.getScoreStatues() == 1 || userlive.getScoreStatues() == 2) {

                double duration = new Date().getTime() - ul.getStartTime();
                userlive.setStudyDuration(duration);
                userlive.setStartTime(ul.getStartTime());
            }
            userliveScore.updateByPrimaryKey(userlive);
        } else {
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
        return userTokenDao.getUserLoginByTeacherID(userId);
    }

    @Override
    public int getUserLoginLogCountByTeacherID(String userId) {
        return userLoginLogMapper.getUserLoginLogCountByTeacherID(userId);
    }

    @Override
    public void userlivedataTorecord(int userId) {
        List<UserLiveDataWithBLOBs> userLiveDataList = userlivedata.getUserlivaByteacherid(userId);
        //先单条插入，以后改成按list批量的 crq
        if (userLiveDataList != null) {
            for (UserLiveDataWithBLOBs uld : userLiveDataList) {
                UserScoreRecord usr = new UserScoreRecord();
                usr.setUserId(uld.getUserId());
                usr.setTaskId(uld.getTaskId());
                usr.setOperateId(uld.getOperateId());
                usr.setScore(uld.getCurrentScore());
                usr.setTotalScore(uld.getTotalScore());
                usr.setBeginTime(uld.getStartTime());
                usr.setEndTime(uld.getStartTime() + uld.getStudyDuration().longValue());
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
    public int deletelivedataTorecord(int userId) {
        int j=userlivedata.deletUserlivaByteacherid(userId);
        int s=userliveScore.deletUserlivaByteacherid(userId);
        return j+s;
    }

    @Override
    public double getTaskTotal_score(UserLiveWithBLOBs ul) {
        //如果uld正在做
        double totalscore = 0;
        if (ul.getScoreStatues() != 2) {
            double maxscore = FZhKTMapper.getOperteMaxScore(ul);
            if (maxscore == -1 || ul.getCurrentScore() > maxscore) {
                totalscore = FZhKTMapper.getTotalcoreWhithooutUserLive(ul) + ul.getCurrentScore();
            } else {
                totalscore = FZhKTMapper.getTotalcoreByUserLive(ul);
            }
        }
        //如果已经做完
        else {
            totalscore = FZhKTMapper.getTotalcoreByUserLive(ul);
        }
        return totalscore;
    }

    @Override
    public List<UserLive> checkIsRecordByTeacherId(String beginDate,String endDate,int userID) {
        return userliveScore.checkIsRecordByTeacherId(beginDate,endDate,userID);
    }

    @Override
    public List<ScoreDetailPOJO> getScoreDetailList(UserLiveWithBLOBs ulwb) {
        List<ScoreDetailPOJO> scoreDetailPOJOArrayList = new ArrayList<>();
        List<Operate> operateList = getOprateList();
        List<Questions> questionsList = getQuestionListAll();
        Map<Integer, Operate> operate_map = operateList.stream().collect(Collectors.toMap(Operate::getId, Function.identity(), (key1, key2) -> key2));
        Map<Integer, Questions> questionsMap = questionsList.stream().collect(Collectors.toMap(Questions::getId, Function.identity(), (key1, key2) -> key2));


        scoreDetailPOJOArrayList = FZhKTMapper.getDetailScoreList(ulwb);
        ScoreDetailPOJO scordetail = new ScoreDetailPOJO();
        scordetail.setOperateId(ulwb.getOperateId());
        scordetail.setLearnTime(String.valueOf(ulwb.getStudyDuration() / 1000));
        //DateFormat df=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        scordetail.setStudyDate(String.valueOf(ulwb.getStartTime()));
        scordetail.setScore(ulwb.getCurrentScore().toString());

        scordetail.setScoreDetail(ulwb.getScoreDetail());
        if (ulwb.getStudyType() == StudyType.TASK.ordinal()) {
            for (ScoreDetailPOJO sd : scoreDetailPOJOArrayList) {
                int keyi = sd.getOperateId();
                Operate op = operate_map.get(sd.getOperateId());
                sd.setTaskName(op.getOperateName());
            }
            scordetail.setTaskName(operate_map.get(scordetail.getOperateId()).getOperateName());
        } else if (ulwb.getStudyType() == StudyType.EXAM.ordinal()) {
            for (ScoreDetailPOJO sd : scoreDetailPOJOArrayList) {
                sd.setTaskName(questionsMap.get(sd.getOperateId()).getQuestionName());
                //sd.setOperateName("空空空");
            }
            scordetail.setTaskName(questionsMap.get(scordetail.getOperateId()).getQuestionName());
            //scordetail.setOperateName("空空空");
        }
        if (ulwb.getScoreStatues() != Constants.ScoreStatues.END.ordinal()) {
            scoreDetailPOJOArrayList.add(scordetail);
        }
        return scoreDetailPOJOArrayList;
    }

    @Override
    public int updateUserScoreRecoredByTrainID(UserScoreRecord usrScore) {
        return userScoreRecord.updateUserScoreRecoredByTrainID(usrScore);
    }

    @Override
    public List<scoreModel> getscoreModelList(String user_id, String template_id) {
        return FZhKTMapper.getscoreModelList(user_id, template_id);
    }
}
