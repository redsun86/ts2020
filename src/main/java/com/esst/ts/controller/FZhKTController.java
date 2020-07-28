package com.esst.ts.controller;

import com.alibaba.fastjson.JSONArray;
import com.esst.ts.constants.Constants;
import com.esst.ts.dao.UserLiveMapper;
import com.esst.ts.dao.UserMapper;
import com.esst.ts.model.*;
import com.esst.ts.service.FZhKTService;
import com.esst.ts.service.OperateService;
import com.esst.ts.service.QuestionsService;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 仿真课堂模块对应的接口；仿真课堂 ==> FangZhenKeTang ==> FZhKT
 */
@Controller
@RequestMapping("/web/v1/fang")
public class FZhKTController {
    @Resource
    private FZhKTService fzhktService;
    @Resource
    private UserLiveMapper userliveservice;
    @Resource
    private UserMapper userMapper;
    @Resource
    private OperateService operateService;
    @Resource
    private QuestionsService questionsService;
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    //@Resource
    //private UserService userService;

    /**
     * 实时数据列表接口
     *
     * @param userId
     * @param templateId
     * @param strToken
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/realtimedata", method = RequestMethod.GET)
    public Result realtimedata(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "templateId", required = false) String templateId,
            @RequestParam(value = "studyType", required = false) String study_type,
            @RequestParam(value = "token", required = true) String strToken,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(200);
        //</editor-fold>
        int total_num=0;          //总人数
        int online_num;         //在线人数

        Map<String, String> classnamemap = new HashMap<>();
        List<taskModel> tasklist = new ArrayList<taskModel>();
        //<editor-fold desc="获得属于此老师的学生在线人数">
        List<UserToken> userLoginLogsList = fzhktService.getUserLoginList();
        //online_num = userLoginLogsList.size();
        //total_num = fzhktService.getUserLoginLogCountByTeacherID(userId);
        //</editor-fold>
        List<scoreModel> datalist = new ArrayList();

        //List<UserLiveWithBLOBs> userLivelist = fzhktService.getUserLiveByTeacherId(userId);
        List<Task> tasklistsql = fzhktService.getTaskListAll();
        List<Operate> operateList = fzhktService.getOprateList();
        List<User> userList = fzhktService.getUserListAll();
        List<Exam> examList = fzhktService.getExamListAll();
        List<Questions> questionsList = fzhktService.getQuestionListAll();
        List<User> userListTeacher = userMapper.getUserLst(Integer.valueOf(userId));
        Map<Integer, UserToken> userTokenMap = userLoginLogsList.stream().collect(Collectors.toMap(UserToken::getUserId, Function.identity(), (key1, key2) -> key2));
        Map<Integer, UserLiveData> userlivedate_map = new HashMap<Integer, UserLiveData>();
        Map<Integer, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity(), (key1, key2) -> key2));
        Map<Integer, Task> task_map = tasklistsql.stream().collect(Collectors.toMap(Task::getId, Function.identity(), (key1, key2) -> key2));
        Map<Integer, Operate> operate_map = operateList.stream().collect(Collectors.toMap(Operate::getId, Function.identity(), (key1, key2) -> key2));
        //Map<Integer, UserLiveWithBLOBs> userLive_map = userLivelist.stream().collect(Collectors.toMap(UserLiveWithBLOBs::getId, Function.identity(), (key1, key2) -> key2));
        Map<Integer, Exam> examMap = examList.stream().collect(Collectors.toMap(Exam::getId, Function.identity(), (key1, key2) -> key2));
        Map<Integer, Questions> questionsMap = questionsList.stream().collect(Collectors.toMap(Questions::getId, Function.identity(), (key1, key2) -> key2));
        Map<Integer, User> userListTeacherMap = userListTeacher.stream().collect(Collectors.toMap(User::getId, Function.identity(), (key1, key2) -> key2));
        User guest = userMapper.selectTeacher();
        HashSet<Integer> onlineHashSet = new HashSet();//在线人数，有学习记录人数-离线人数
        List<UserLiveDataWithBLOBs> userLiveWithBLOBsList;
        if (guest.getId().toString().equals(userId)) {
            userLiveWithBLOBsList = fzhktService.getRealTimeByTeacherId("", "", "");
        } else {
            userLiveWithBLOBsList = fzhktService.getRealTimeByTeacherId(userId, "", "");
        }
        for (UserLiveDataWithBLOBs uld : userLiveWithBLOBsList) {
            //<editor-fold desc="初始化任务单列表">
            if (uld.getStudyType() == 0) {
                Task t = new Task();
                Task tlist = task_map.get(uld.getTaskId());
                taskModel taskModel = new taskModel();
                taskModel.setTask_id(tlist.getId().toString());
                taskModel.setTask_name(tlist.getTaskName());
                taskModel.setStudy_type(uld.getStudyType().toString());
                tasklist.add(taskModel);
            } else if (uld.getStudyType() == 1) {
                Exam exam = new Exam();
                Exam examlist = examMap.get(uld.getTaskId());
                taskModel taskModel = new taskModel();
                taskModel.setTask_id(examlist.getId().toString());
                taskModel.setTask_name(examlist.getExamName());
                taskModel.setStudy_type(uld.getStudyType().toString());
                tasklist.add(taskModel);
            }
            //</editor-fold>
            if (StringUtil.isEmpty(templateId) || uld.getTaskId().equals(Integer.valueOf(templateId)) && uld.getStudyType().equals(Integer.valueOf(study_type))) {
                //<editor-fold desc="遍历成绩">
                scoreModel _score = new scoreModel();
                _score.setId(uld.getId().toString());
                _score.setUser_id(String.valueOf(uld.getUserId()));
                _score.setTeacher_id(uld.getTeacherId().toString());
                _score.setMachine_id(uld.getMacAddress());
                _score.setTrain_id(uld.getTrainId());
                User user = userMap.get(uld.getUserId());
                if (userListTeacherMap.containsKey(user.getId())) {
                    _score.setUser_name(user.getRelName());
                } else {
                    _score.setUser_name("*" + user.getRelName());
                }
                classnamemap.put(user.getClassName(), "");
                _score.setStudent_num(user.getStNum());
                _score.setScore(uld.getCurrentScore().toString());
                _score.setTotal_score(String.format("%.2f", uld.getTotalScore()));

                _score.setLearning_time(String.valueOf(uld.getStudyDuration() / 1000));
                _score.setStudy_type(uld.getStudyType());
                //<editor-fold desc="任务单类型">
                if (uld.getStudyType() == 0) {
                    Task t = new Task();
                    if (StringUtil.isEmpty(study_type) || StringUtil.isEmpty(templateId)) {
                        t = task_map.get(uld.getTaskId());
                    } else if (study_type.equals("0")) {
                        if (uld.getTaskId().toString().equals(templateId)) {
                            t = task_map.get(uld.getTaskId());
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                    _score.setTemplate_id(t.getId().toString());
                    _score.setTemplate_name(t.getTaskName());
                    Operate operate = operate_map.get(uld.getOperateId());
                    _score.setTask_id(operate.getId().toString());
                    _score.setTask_name(operate.getOperateName());

                }
                //</editor-fold>
                else if (uld.getStudyType() == 1) {
                    Exam exam = new Exam();
                    if (StringUtil.isEmpty(study_type) || StringUtil.isEmpty(templateId)) {
                        exam = examMap.get(uld.getTaskId());
                    } else if (study_type.equals("1")) {
                        if (uld.getTaskId().toString().equals(templateId)) {
                            exam = examMap.get(uld.getTaskId());
                        } else
                            continue;
                    } else
                        continue;

                    _score.setTemplate_id(exam.getId().toString());
                    _score.setTemplate_name(exam.getExamName());
                    Questions questions = questionsMap.get(uld.getOperateId());
                    _score.setTask_id(questions.getId().toString());
                    _score.setTask_name(questions.getQuestionName());

                }
                if (uld.getScoreStatues() == 2) {
                    _score.setStatus("已提交");
                    onlineHashSet.add(uld.getUserId());
                } else if (uld.getScoreStatues() == 0 || uld.getScoreStatues() == 1) {
                    _score.setStatus("操作中");
                    onlineHashSet.add(uld.getUserId());
                }
                if (userTokenMap.get(uld.getUserId()) == null) {
                    _score.setStatus("离线");
                    if (onlineHashSet.contains(uld.getUserId())) {
                        onlineHashSet.remove(uld.getUserId());
                    }
                }
                //</editor-fold>
                datalist.add(_score);
            }
        }
        tasklist = tasklist.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getTask_id() + ";" + o.getStudy_type()))), ArrayList::new));
        online_num = onlineHashSet.size();
        //<editor-fold desc="统计数据赋值">
        String teacher_name;    //教师名称
        teacher_name = "教师名称";
        //</editor-fold>
        //<editor-fold desc="返回参数赋值">
        Map<String, Object> FZhKTMap = new HashMap<>();
        FZhKTMap.put("datalist", datalist);
        FZhKTMap.put("tasklist", tasklist);
        FZhKTMap.put("total_num", total_num);
        FZhKTMap.put("teacher_name", teacher_name);
        FZhKTMap.put("class_name", new ArrayList<>(classnamemap.keySet()));
        FZhKTMap.put("online_num", online_num);
        r.setData(FZhKTMap);
        //</editor-fold>
        return r;
    }

    /**
     * 学员端上传成绩接口
     *
     * @param score_id
     * @param request
     * @return client_status 0开始，中间1，结束2
     */
    @ResponseBody
    @RequestMapping(value = "/updatescore", method = RequestMethod.POST)
    public Result updatescore(@RequestParam(value = "scoreId") String score_id,
                              @RequestParam(value = "userId") int user_id,
                              @RequestParam(value = "macAdress") String mac_adress,
                              @RequestParam(value = "ipAdress") String id_adress,
                              @RequestParam(value = "taskId") int task_id,
                              @RequestParam(value = "operateId") int operate_id,
                              @RequestParam(value = "status") String status,
                              @RequestParam(value = "currentScore") double current_score,
                              @RequestParam(value = "totalScore") double total_score,
                              @RequestParam(value = "scoreDetail") String score_detail,
                              @RequestParam(value = "clientStatus") int client_status,
                              @RequestParam(value = "studyType") int study_type,
                              @RequestParam(value = "trainId") String train_id,
                              @RequestParam(value = "teacherId") int teacher_id,
                              @RequestParam(value = "token", required = true) String token,
                              HttpServletRequest request) {
        RequestContext requestcontext = new RequestContext(request);
        Result r = new Result();
        Date datecurretime = new Date();
        Long curretime = datecurretime.getTime();
        //user_live_data
        UserLiveDataWithBLOBs uldscore = new UserLiveDataWithBLOBs();
        uldscore.setUserId(user_id);
        uldscore.setIdAddress(id_adress);
        uldscore.setMacAddress(mac_adress);
        uldscore.setTaskId(task_id);
        uldscore.setOperateId(operate_id);
        uldscore.setCurrentScore(current_score);
        uldscore.setTotalScore(total_score);
        uldscore.setScoreDetail(score_detail);
        uldscore.setStartTime(curretime);
        uldscore.setScoreStatues(client_status);
        uldscore.setTrainId(train_id);
        uldscore.setStudyDuration(0.00);
        uldscore.setUpdatetime(curretime);
        uldscore.setStudyType(study_type);

        UserLiveWithBLOBs userlive = new UserLiveWithBLOBs();
        userlive.setUserId(user_id);
        userlive.setIdAddress(id_adress);
        userlive.setMacAddress(mac_adress);
        userlive.setTaskId(task_id);
        userlive.setOperateId(operate_id);
        userlive.setCurrentScore(current_score);
        userlive.setTotalScore(total_score);
        userlive.setScoreDetail(score_detail);
        userlive.setStudyType(study_type);
        userlive.setUpdatetime(curretime);
        userlive.setStartTime(curretime);
        userlive.setStudyDuration(0.00);
        userlive.setScoreStatues(client_status);
        userlive.setTrainId(train_id);
        userlive = fzhktService.updateUserLive(userlive);
        uldscore.setStudyDuration(userlive.getStudyDuration());
        uldscore.setStartTime(userlive.getStartTime());
        uldscore.setTeacherId(teacher_id);
        fzhktService.insertUserLiveDataWithBLOBS(uldscore);


        UserScoreRecord usrScore = new UserScoreRecord();
        usrScore.setUserId(user_id);
        usrScore.setTaskId(task_id);
        usrScore.setOperateId(operate_id);
        usrScore.setScore(current_score);
        usrScore.setTotalScore(total_score);
        usrScore.setBeginTime(curretime);
        usrScore.setEndTime(curretime);
        usrScore.setMacAddress(mac_adress);
        usrScore.setIpAddress(id_adress);
        usrScore.setTrainId(train_id);
        usrScore.setStudyType(study_type);
        usrScore.setTeacherId(teacher_id);
        fzhktService.updateUserScoreRecoredByTrainID(usrScore);
        r.setMsg("更新成功");
        return r;
    }
    /**
     * 详细得分
     */
    @ResponseBody
    @RequestMapping(value = "/getTaskdetailscore", method = RequestMethod.GET)
    public Result getTaskdetailscore(
            @RequestParam(value = "userId", required = true) int userId,
            @RequestParam(value = "teacherId", required = true) int teacher_Id,
            @RequestParam(value = "studentId", required = true) int studentId,
            @RequestParam(value = "taskId", required = true) int taskId,
            @RequestParam(value = "studyType", required = true) int studyType,
            @RequestParam(value = "taskName", required = true) String taskName,
            @RequestParam(value = "token", required = true) String strToken,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        List<ScoreDetailPOJO> scoreDetailPOJOSList = fzhktService.getScoreDetailList(teacher_Id, studentId, taskId, studyType);
        //double totlscore = fzhktService.getTaskTotal_score(ulwb);
        //<editor-fold desc="返回参数赋值">
        Map<String, Object> FZhKTMap = new HashMap<>();
        FZhKTMap.put("dataList", scoreDetailPOJOSList);
        //FZhKTMap.put("totlScore", totlscore);
        FZhKTMap.put("taskName", taskName);
        r.setData(FZhKTMap);
        //</editor-fold>
        return r;
    }

    /**
     * 学员端上传成绩接口
     *
     * @param userId   老师ID
     * @param taskList 试卷或者任务单list
     * @return studyType 学习类型
     */
    @ResponseBody
    @RequestMapping(value = "/realtimeExcel", method = RequestMethod.POST)
    public ResponseEntity<byte[]> realttimeExcel(
            @RequestParam(value = "userId", required = true) int userId,
            @RequestParam(value = "taskList", required = true) String taskList,
            @RequestParam(value = "token", required = true) String token,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        User guest = userMapper.selectTeacher();
        List<User> userList = userMapper.getUserLst(userId);
        List<User> userListAll = userMapper.getUserListAll();
        List<taskModel> taskModelList = (List<taskModel>) JSONArray.parseArray(taskList, taskModel.class);//任务单列表
        User teacher = userMapper.selectByPrimaryKey(userId);
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        String begindate = formatter1.format(new Date()) + " 00:00:00";
        String enddate = formatter1.format(new Date()) + " 23:59:59";
        List<UserLoginLog> userLoginLogList = fzhktService.getUserloginLogForDate(begindate, enddate);//.getUserLoginLogeacherID(userId);
        Map<Integer, UserLoginLog> userLoginLogMap = new HashMap<>();
        Map<Integer, User> userMap = userListAll.stream().collect(Collectors.toMap(User::getId, Function.identity(), (key1, key2) -> key2));
        if (userLoginLogList != null) {
            userLoginLogMap = userLoginLogList.stream().collect(Collectors.toMap(UserLoginLog::getUserId, Function.identity(), (key1, key2) -> key2));
        }
        //任务单或试卷报表列表，每个元素是一个sheet
        List<RealTimeEcxelPOJO> scoreexcelList = new ArrayList<>();
        //学号	姓名	机器号	登录时间（年月日，时分秒）	学习时长（min）	课题名称	氮气置换
        if ((taskModelList != null)) {
            for (taskModel tm : taskModelList) {

                List<UserLiveDataWithBLOBs> userLiveDataWithBLOBs = new ArrayList<>();
                if (guest.getId().equals(userId)) {
                    userLiveDataWithBLOBs = fzhktService.getOperateMaxScore("", String.valueOf(tm.getTask_id()), String.valueOf(tm.getStudy_type()));
                } else {
                    userLiveDataWithBLOBs = fzhktService.getOperateMaxScore(String.valueOf(userId), String.valueOf(tm.getTask_id()), String.valueOf(tm.getStudy_type()));
                }
                RealTimeEcxelPOJO realTimeEcxelPOJO = new RealTimeEcxelPOJO();
                realTimeEcxelPOJO.setStudyType(Constants.StudyType.values()[Integer.valueOf(tm.getStudy_type())]);//学习类型
                realTimeEcxelPOJO.setTaskName(tm.getTask_name());//任务单
                realTimeEcxelPOJO.setTeacherName(teacher.getRelName());//老师名称
                Operate reqMod = new Operate();
                reqMod.setIsDeleted(0);
                reqMod.setTaskId(Integer.valueOf(tm.getTask_id()));

                if (Integer.valueOf(tm.getStudy_type()) == Constants.StudyType.TASK.ordinal()) {//任务单
                    List<Operate> operateList = operateService.GetList(reqMod);
                    if (operateList != null) {
                        for (int i = 0; i < operateList.size(); i++) {
                            Operate operate = operateList.get(i);
                            realTimeEcxelPOJO.operateList.add(operate.getOperateName());
                            realTimeEcxelPOJO.operateIndexList.put(operate.getId(), i);

                        }
                    }
                } else {
                    List<QuestionsPOJO> questLST = questionsService.GetList(0, Integer.valueOf(tm.getTask_id()));
                    if (questLST != null) {
                        for (int i = 0; i < questLST.size(); i++) {
                            QuestionsPOJO operate = questLST.get(i);
                            realTimeEcxelPOJO.operateList.add(operate.getOperateName());
                            realTimeEcxelPOJO.operateIndexList.put(operate.getId(), i);

                        }
                    }
                }
                //realTimeEcxelPOJO.operateList=operateList;
                if (userList != null) {
                    for (User user : userList) {
                        RealTimeExcelItemPOJO scoreexcelitem = new RealTimeExcelItemPOJO();
                        //填充数据
                        scoreexcelitem.setNum(user.getStNum());//学号
                        scoreexcelitem.setStudentName(user.getRelName());//姓名
                        //scoreexcelitem.setLoginTime("2020:07:03 0:0:0");//登录时间
                        scoreexcelitem.setTaskName(tm.getTask_name());//任务单
                        realTimeEcxelPOJO.ClassNameMap.add(user.getClassName());
                        if (userLoginLogMap != null) {
                            if (userLoginLogMap.containsKey(user.getId())) {
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String formatStr = formatter.format(userLoginLogMap.get(user.getId()).getCreateTime());
                                scoreexcelitem.setLoginTime(formatStr);
                            }
                        }
                        //scoreexcelitem.operateScoremap
                        realTimeEcxelPOJO.realTimeExcelItemPOJOHashMap.put(user.getId(), scoreexcelitem);

                    }
                }
                if (userLiveDataWithBLOBs != null) {
                    for (UserLiveDataWithBLOBs usld : userLiveDataWithBLOBs) {
                        if (!realTimeEcxelPOJO.realTimeExcelItemPOJOHashMap.containsKey(usld.getUserId())) {
                            // <editor-fold desc="名单外学员">
                            RealTimeExcelItemPOJO scoreexcelitem = new RealTimeExcelItemPOJO();
                            if (realTimeEcxelPOJO.realTimeExcelItemPOJOHashMapWithoutTeacher.containsKey(usld.getUserId())) {
                                scoreexcelitem = realTimeEcxelPOJO.realTimeExcelItemPOJOHashMapWithoutTeacher.get(usld.getUserId());
                            }
                            User user1 = userMap.get(usld.getUserId());
                            //填充数据
                            scoreexcelitem.setNum(user1.getStNum());//学号
                            scoreexcelitem.setStudentName(user1.getRelName());//姓名
                            //scoreexcelitem.setLoginTime("2020:07:03 0:0:0");//登录时间
                            scoreexcelitem.setTaskName(tm.getTask_name());//任务单
                            realTimeEcxelPOJO.ClassNameMap.add(user1.getClassName());
                            if (userLoginLogMap != null) {
                                if (userLoginLogMap.containsKey(user1.getId())) {
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String formatStr = formatter.format(userLoginLogMap.get(user1.getId()).getCreateTime());
                                    scoreexcelitem.setLoginTime(formatStr);
                                }
                            }
                            //scoreexcelitem.operateScoremap
                            String learntime = String.format("%.2f", Double.valueOf(scoreexcelitem.getLearnTime()) + usld.getStudyDuration() / 60000);
                            scoreexcelitem.setLearnTime(learntime);
                            scoreexcelitem.setMachineNum(usld.getMacAddress());
                            scoreexcelitem.operateScoremap.put(usld.getOperateId(), usld.getCurrentScore());
                            double toutalscore = scoreexcelitem.getTotalScore().doubleValue() + usld.getCurrentScore();
                            scoreexcelitem.setTotalScore((double) Math.round(toutalscore * 100) / 100);
                            realTimeEcxelPOJO.realTimeExcelItemPOJOHashMapWithoutTeacher.put(user1.getId(), scoreexcelitem);
                            //</editor-fold>
                        } else {
                            // <editor-fold desc="名单内学员">
                            RealTimeExcelItemPOJO rtitem = realTimeEcxelPOJO.realTimeExcelItemPOJOHashMap.get(usld.getUserId());
                            String learntime = String.format("%.2f", Double.valueOf(rtitem.getLearnTime()) + usld.getStudyDuration() / 60000);
                            rtitem.setLearnTime(learntime);
                            rtitem.setMachineNum(usld.getMacAddress());
                            rtitem.operateScoremap.put(usld.getOperateId(), usld.getCurrentScore());
                            double toutalscore = rtitem.getTotalScore().doubleValue() + usld.getCurrentScore();
                            rtitem.setTotalScore((double) Math.round(toutalscore * 100) / 100);
                            realTimeEcxelPOJO.realTimeExcelItemPOJOHashMap.put(usld.getUserId(), rtitem);
                            //</editor-fold>
                        }
                    }
                }

                scoreexcelList.add(realTimeEcxelPOJO);
            }
        }
        //fzhktService.exportReatimeScore(scoreexcelList);
        return fzhktService.exportReatimeScore(scoreexcelList);
    }

    /**
     * 上传详细评分
     */
    @RequestMapping(value = "/updateDetailScore", method = RequestMethod.POST)
    @ResponseBody
    public Result updateDetailScore(@RequestParam("detailScore") String detailScore,
                                    @RequestParam(value = "userId", required = true) Integer userId,
                                    @RequestParam(value = "trainId", required = true) String trainId,
                                    @RequestParam(value = "token", required = true) String strToken,
                                    HttpServletRequest request) throws IOException {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        DetailScore detailScore1 = new DetailScore();
        detailScore1.setDetailScore(detailScore);
        detailScore1.setTrainId(trainId);
        fzhktService.insertDetailScore(detailScore1);
        r.setMsg("上传成功");
        return r;
    }

    /**
     * 获得详细评分
     */
    @RequestMapping(value = "/getDetailScore", method = RequestMethod.GET)
    @ResponseBody
    public Result getDetailScore(@RequestParam(value = "userId", required = true) Integer userId,
                                 @RequestParam(value = "trainId", required = true) String trainId,
                                 @RequestParam(value = "token", required = true) String strToken,
                                 HttpServletRequest request) throws IOException {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        DetailScore detailScore= fzhktService.getDetailScore(trainId);
        Map<String, Object> FZhKTMap = new HashMap<>();
        FZhKTMap.put("detailScore", detailScore);
        r.setData(FZhKTMap);
        return r;
    }
}
