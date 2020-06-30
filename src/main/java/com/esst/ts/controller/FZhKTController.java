package com.esst.ts.controller;

import com.esst.ts.dao.UserLiveMapper;
import com.esst.ts.dao.UserMapper;
import com.esst.ts.model.*;
import com.esst.ts.service.FZhKTService;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
            @RequestParam(value = "user_id", required = false) String userId,
            @RequestParam(value = "template_id", required = false) String templateId,
            @RequestParam(value = "study_type", required = false) String study_type,
            @RequestParam(value = "token", required = true) String strToken,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(200);
        //</editor-fold>
        int total_num;          //总人数
        int online_num;         //在线人数

        Map<String, String> classnamemap = new HashMap<>();
        List<taskModel> tasklist = new ArrayList<taskModel>();
        //<editor-fold desc="获得属于此老师的学生在线人数">
        List<UserToken> userLoginLogsList = fzhktService.getUserLoginByTeacherID(userId);
        online_num = userLoginLogsList.size();
        total_num = fzhktService.getUserLoginLogCountByTeacherID(userId);
        //</editor-fold>
        List<scoreModel> datalist = new ArrayList();

        List<UserLiveWithBLOBs> userLivelist = fzhktService.getUserLiveByTeacherId(userId);
        List<Task> tasklistsql = fzhktService.getTaskListAll();
        List<Operate> operateList = fzhktService.getOprateList();
        List<User> userList = fzhktService.getUserListAll();
        List<Exam> examList = fzhktService.getExamListAll();
        List<Questions> questionsList = fzhktService.getQuestionListAll();

        Map<Integer, UserToken> userTokenMap = userLoginLogsList.stream().collect(Collectors.toMap(UserToken::getUserId, Function.identity(), (key1, key2) -> key2));
        Map<Integer, UserLiveData> userlivedate_map = new HashMap<Integer, UserLiveData>();
        Map<Integer, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity(), (key1, key2) -> key2));

        Map<Integer, Task> task_map = tasklistsql.stream().collect(Collectors.toMap(Task::getId, Function.identity(), (key1, key2) -> key2));
        Map<Integer, Operate> operate_map = operateList.stream().collect(Collectors.toMap(Operate::getId, Function.identity(), (key1, key2) -> key2));
        Map<Integer, UserLiveWithBLOBs> userLive_map = userLivelist.stream().collect(Collectors.toMap(UserLiveWithBLOBs::getId, Function.identity(), (key1, key2) -> key2));
        Map<Integer, Exam> examMap = examList.stream().collect(Collectors.toMap(Exam::getId, Function.identity(), (key1, key2) -> key2));
        Map<Integer, Questions> questionsMap = questionsList.stream().collect(Collectors.toMap(Questions::getId, Function.identity(), (key1, key2) -> key2));

        for (UserLiveWithBLOBs uld : userLivelist) {
            scoreModel _score = new scoreModel();
            _score.setId(uld.getId().toString());
            _score.setMachine_id(uld.getMacAddress());
            User user = userMap.get(uld.getUserId());
            classnamemap.put(user.getClassName(), "");
            _score.setStudent_num(user.getStNum());
            _score.setUser_name(user.getRelName());
            _score.setScore(uld.getCurrentScore().toString());
            //_score.setTotal_score(uld.getTotalScore().toString());
            double totlscore = fzhktService.getTaskTotal_score(uld);
            _score.setTotal_score(String.format("%.2f", totlscore));

            _score.setLearning_time(String.valueOf(uld.getStudyDuration() / 1000));
            _score.setStudy_type(uld.getStudyType());
            //<editor-fold desc="任务单类型">
            if (uld.getStudyType() == 0) {
                Task t = new Task();
                Task tlist = task_map.get(uld.getTaskId());
                taskModel taskModel = new taskModel();
                taskModel.setTask_id(tlist.getId().toString());
                taskModel.setTask_name(tlist.getTaskName());
                taskModel.setStudy_type(uld.getStudyType().toString());
                tasklist.add(taskModel);
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
                Exam examlist = examMap.get(uld.getTaskId());
                taskModel taskModel = new taskModel();
                taskModel.setTask_id(examlist.getId().toString());
                taskModel.setTask_name(examlist.getExamName());
                taskModel.setStudy_type(uld.getStudyType().toString());
                tasklist.add(taskModel);
                if (StringUtil.isEmpty(study_type) || StringUtil.isEmpty(templateId)) {
                    exam = examMap.get(uld.getTaskId());
                } else if (study_type.equals("1")) {
                    if (uld.getTaskId().toString().equals(templateId)) {
                        exam = examMap.get(uld.getTaskId());
                    } else
                        continue;
                } else
                    continue;
//                if (study_type.equals("1") && uld.getTaskId().toString().equals(templateId)) {
//                    exam = examMap.get(uld.getTaskId());
//                } else if (study_type.equals("")) {
//                    exam = examMap.get(uld.getTaskId());
//                } else {
//                    continue;
//                }
                _score.setTemplate_id(exam.getId().toString());
                _score.setTemplate_name(exam.getExamName());
                Questions questions = questionsMap.get(uld.getOperateId());
                _score.setTask_id(questions.getId().toString());
                _score.setTask_name(questions.getQuestionName());

            }
            if (uld.getScoreStatues() == 0 || uld.getScoreStatues() == 1) {
                _score.setStatus("操作中");
            }
            if (userTokenMap.get(uld.getUserId()) == null) {
                _score.setStatus("离线");
            }
            //</editor-fold>
            datalist.add(_score);
        }
        //</editor-fold>
//        for (Task obj: tasklistsql) {
//            try {
//                if()
//                System.out.println(obj);
//                BeanUtils.copyProperties(userlivedatelist.get(obj.getId()), obj);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        //<editor-fold desc="实时数据列表赋值：datalist">
        //tasklist=tasklist.stream().collect(Collectors.groupingBy(taskModel::getTask_id,taskModel::getStudy_type));
        tasklist = tasklist.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getTask_id() + ";" + o.getStudy_type()))), ArrayList::new));

        //<editor-fold desc="临时数据">
        List<String> stuLst = new ArrayList<String>();
        stuLst.add("李智");
        stuLst.add("梁建磊");
        stuLst.add("曹瑞卿");
        stuLst.add("史红阳");
        stuLst.add("专家");
        //</editor-fold>

        //</editor-fold>


        //</editor-fold>
        //<editor-fold desc="统计数据赋值">
        String class_name;      //班级名称
        String teacher_name;    //教师名称
        class_name = "班级名称";
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
    public Result updatescore(@RequestParam(value = "score_id") String score_id,
                              @RequestParam(value = "user_id") int user_id,
                              @RequestParam(value = "mac_adress") String mac_adress,
                              @RequestParam(value = "ip_adress") String id_adress,
                              @RequestParam(value = "task_id") int task_id,
                              @RequestParam(value = "operate_id") int operate_id,
                              @RequestParam(value = "status") String status,
                              @RequestParam(value = "current_score") double current_score,
                              @RequestParam(value = "total_score") double total_score,
                              @RequestParam(value = "score_detail") String score_detail,
                              @RequestParam(value = "client_status") int client_status,
                              @RequestParam(value = "study_type") int study_type,
                              @RequestParam(value = "train_id") String train_id,
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
        //fzhktService.insertUserLiveDataWithBLOBS(uldscore);

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

        fzhktService.updateUserScoreRecoredByTrainID(usrScore);
        r.setMsg("更新成功");
        return r;
    }

    @ResponseBody
    @RequestMapping(value = "/getTaskdetailscore", method = RequestMethod.GET)
    public Result getTaskdetailscore(
            @RequestParam(value = "Id", required = true) int Id,
            @RequestParam(value = "taskName", required = true) String taskName,
            @RequestParam(value = "token", required = true) String strToken,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();

        UserLiveWithBLOBs ulwb = userliveservice.selectByPrimaryKey(Id);
        List<ScoreDetailPOJO> scoreDetailPOJOSList = fzhktService.getScoreDetailList(ulwb);
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
     * @param userId 老师ID
     * @param taskId 试卷或者任务单ID
     * @return studyType 学习类型
     */
    @ResponseBody
    @RequestMapping(value = "/realtimeExcel", method = RequestMethod.GET)
    public Result realttimeExcel(
            @RequestParam(value = "userId", required = true) int userId,
            @RequestParam(value = "taskId", required = true) int taskId,
            @RequestParam(value = "studyType", required = true) int studyType,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //key operateId,value userlivedata
        Map<String, Object> operaMaxmap = new HashMap<>();

        List<User> userList = userMapper.getUserLst(userId);
        return r;
    }
}
