package com.esst.ts.controller;

import com.esst.ts.model.*;
import com.esst.ts.service.FZhKTService;
import com.esst.ts.service.StudyRecordService;
import com.esst.ts.service.UserService;
import com.esst.ts.utils.DateUtils;
import com.esst.ts.utils.ExcelUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 成绩查询模块
 * SYH
 */

@Controller
@RequestMapping("/web/v1/studyRecord")
public class StudyRecordController {

    @Resource
    private StudyRecordService studyRecordService;

    @Resource
    private UserService userService;

    @Resource
    private FZhKTService fzhktService;

    /**
     * 1：成绩查询万年历接口
     */
    @ResponseBody
    @RequestMapping(value = "/selectScore", method = RequestMethod.POST)
    public Result selectScore(@RequestParam(value = "date",required = true) String date,
                             @RequestParam(value = "token",required = true) String token,
                             HttpServletRequest request) throws ParseException {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();

        Date dates = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        Calendar ca = Calendar.getInstance();
        ca.setTime(dates);
        int month = ca.get(Calendar.MONTH)+1;//第几个月
        int year = ca.get(Calendar.YEAR);//年份数值
        String t="";
        List<UserLoginLogListitem> listTeacher = new ArrayList<UserLoginLogListitem>();
        List<UserLoginLogLists> listss = new ArrayList<UserLoginLogLists>();
        String beginDate=year+"-"+month+"-"+"1";
        String endDate=year+"-"+month+"-"+"31";
        List<UserLoginLogPOJO> userLoginLogList=studyRecordService.getDistinctDateForDate(beginDate,endDate);
        for (int i=0;i<userLoginLogList.size();i++) {
            //根据日期查询当前日期下是否有学员进行学习记录
            int s=0;
            int c=0;
            UserLoginLogLists l=new UserLoginLogLists();
            String beginDates=userLoginLogList.get(i).getNewcreatetime()+" 00:00:00";
            String endDates=userLoginLogList.get(i).getNewcreatetime()+" 23:59:59";
            List<UserLoginLog> userLoginLogListForUserId=studyRecordService.getUserloginLogForDate(beginDates,endDates);
            if(userLoginLogListForUserId.size()>0){
                List<UserLoginLogListitem> listitems = new ArrayList<UserLoginLogListitem>();
                for (int j=0;j<userLoginLogListForUserId.size();j++) {
                    //根据教师Id以及当前的日期获取学员的学习记录
                    List<UserScoreRecord> userScoreRecord=studyRecordService.getUserStudyRecord(userLoginLogList.get(i).getNewcreatetime(),userLoginLogList.get(i).getNewcreatetime(),userLoginLogListForUserId.get(j).getUserId());
                    if(userScoreRecord.size()>0){
                        if(s==0) {
                            l.setDate(userLoginLogList.get(i).getNewcreatetime());
                            s++;
                        }
                        UserLoginLogListitem li=new UserLoginLogListitem();
                        User u=userService.getUserById(userLoginLogListForUserId.get(j).getUserId());
                        li.setuserId(userLoginLogListForUserId.get(j).getUserId());
                        li.setTeacherName(u.getRelName()+"老师");
                        listitems.add(li);
                        l.setListsitem(listitems);
                        c++;
                        if(!t.toString().contains(userLoginLogListForUserId.get(j).getUserId().toString())){
                            t+=userLoginLogListForUserId.get(j).getUserId()+",";
                        }
                    }
                }
            }
            if(c>0) {
                listss.add(l);
            }
        }
        if(t.contains(",")){
            int j = 0;
            String[] str = t.split(",");
            for (String s : str) {
                UserLoginLogListitem li=new UserLoginLogListitem();
                li.setuserId(Integer.valueOf(s));
                User u=userService.getUserById(Integer.valueOf(s));
                li.setTeacherName(u.getRelName());
                listTeacher.add(li);
            }
        }
        responseDataMap.put("list",listss);
        responseDataMap.put("teacherList",listTeacher);
        r.setData(responseDataMap);
        return r;
    }

    /**
     *:2：班级成绩查询接口
     */
    @ResponseBody
    @RequestMapping(value = "/selectClassScore", method = RequestMethod.POST)
    public Result selectClassScore(@RequestParam(value = "beginDate") String beginDate,
                                   @RequestParam(value = "endDate") String endDate,
                                   @RequestParam(value = "userId") Integer userId,
                                   @RequestParam(value = "taskId") Integer taskId,
                                   @RequestParam(value = "studyType") String studyType,
                                   @RequestParam(value = "token") String token,
                              HttpServletRequest request) throws ParseException {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<taskModel> tasklist = new ArrayList<taskModel>();
        List<Task> tasklistsql = fzhktService.getTaskListAll();
        Map<Integer, Task> task_map = tasklistsql.stream().collect(Collectors.toMap(Task::getId, Function.identity(), (key1, key2) -> key2));
        List<Exam> examList=fzhktService.getExamListAll();
        Map<Integer,Exam> examMap=examList.stream().collect(Collectors.toMap(Exam::getId, Function.identity(), (key1, key2) -> key2));
        List<UserScoreRecordPOJO> userScoreRecordPOJO=studyRecordService.getUserStudyRecordAndUserInfo(beginDate,endDate,userId,studyType,taskId);
        List<UserScoreRecordPOJO> dataList = new ArrayList<UserScoreRecordPOJO>();
        for (UserScoreRecordPOJO newuserScoreRecordPOJO : userScoreRecordPOJO) {
            User u=new User();
            //taskService.
            u=userService.getUserById(newuserScoreRecordPOJO.getUserId());
            UserScoreRecordPOJO m = new UserScoreRecordPOJO();
            m.setId(newuserScoreRecordPOJO.getId());
            m.setMacAddress(newuserScoreRecordPOJO.getMacAddress());
            m.setIpAddress(newuserScoreRecordPOJO.getIpAddress());
            m.setUserId(newuserScoreRecordPOJO.getUserId());
            m.setUserTrueName(u.getRelName());
            m.setUserStNum(u.getStNum());
            m.setTaskId(newuserScoreRecordPOJO.getTaskId());
            m.setOperateId(newuserScoreRecordPOJO.getOperateId());
            m.setStudyType(newuserScoreRecordPOJO.getStudyType());
            m.setClassName(u.getClassName());
            String beginTime=Long.toString(newuserScoreRecordPOJO.getBeginTime());
            m.setStudyDate(DateUtils.stampToDates(beginTime));
            double score=0;
            long leartime=0;
            //根据用户id和日期和任务单id进行分组查询对应的任务
            List<UserScoreRecordPOJO> operateid=studyRecordService.getoperateid(DateUtils.stampToDates(beginTime),newuserScoreRecordPOJO.getUserId(),newuserScoreRecordPOJO.getTaskId());
            for (UserScoreRecordPOJO operateidlist : operateid) {
                //根据分组的operateid查询最大的成绩
                List<UserScoreRecordPOJO> maxscore=studyRecordService.getmaxscore(DateUtils.stampToDates(beginTime),newuserScoreRecordPOJO.getUserId(),newuserScoreRecordPOJO.getTaskId(),operateidlist.getOperateId());
                score+=maxscore.get(0).getScore();
            }
            m.setScore(score);
            //根据用户id和日期和任务单id进行查询对应的任务
            List<UserScoreRecordPOJO> learnTime=studyRecordService.getLearnTime(DateUtils.stampToDates(beginTime),newuserScoreRecordPOJO.getUserId(),newuserScoreRecordPOJO.getTaskId());
            for (UserScoreRecordPOJO learnTimes : learnTime) {
                leartime+=learnTimes.getEndTime()-learnTimes.getBeginTime();
            }
            m.setLearTime(leartime/1000);
            m.settGroupName(u.getGroupName());
            if(newuserScoreRecordPOJO.getStudyType()==0){
                //任务
                Task t = task_map.get(newuserScoreRecordPOJO.getTaskId());
                if(t==null) {
                    m.setTaskName("");
                }else{
                    m.setTaskName(t.getTaskName());
                }
                int c=0;
                for(int i=0;i<tasklist.size();i++){
                    String a=tasklist.get(i).getStudy_type();
                    String b=tasklist.get(i).getTask_id();
                    String d=newuserScoreRecordPOJO.getTaskId().toString();
                    if(tasklist.get(i).getStudy_type()=="0" && tasklist.get(i).getTask_id().equals(newuserScoreRecordPOJO.getTaskId().toString())){
                        c++;
                    }
                }
                if(c==0) {
                    taskModel taskModel = new taskModel();
                    taskModel.setTask_id(t.getId().toString());
                    taskModel.setTask_name(t.getTaskName());
                    taskModel.setStudy_type("0");
                    tasklist.add(taskModel);
                }
            }
            else if(newuserScoreRecordPOJO.getStudyType()==1){
                //试卷
                Exam e=examMap.get(newuserScoreRecordPOJO.getTaskId());
                if(e==null){
                    m.setTaskName("");
                }
                else {
                    m.setTaskName(e.getExamName());
                }
                int c=0;
                for(int i=0;i<tasklist.size();i++){
                    if(tasklist.get(i).getStudy_type()=="1" && tasklist.get(i).getTask_id().equals(newuserScoreRecordPOJO.getTaskId().toString())){
                        c++;
                    }
                }
                if(c==0) {
                    taskModel taskModel = new taskModel();
                    taskModel.setTask_id(e.getId().toString());
                    taskModel.setTask_name(e.getExamName());
                    taskModel.setStudy_type("1");
                    tasklist.add(taskModel);
                }
            }
            dataList.add(m);
        }
        responseDataMap.put("list", dataList);
        responseDataMap.put("taskList", tasklist);
        r.setData(responseDataMap);
        return r;
    }

    /**
     *:3：班级成绩查询导出Excel接口
     */
    @ResponseBody
    @RequestMapping(value = "/classScoreExcel", method = RequestMethod.POST)
    public ResponseEntity<byte[]> classScoreExcel(@RequestParam(value = "beginDate") String beginDate,
                                @RequestParam(value = "endDate") String endDate,
                                @RequestParam(value = "userId") Integer userId,
                                @RequestParam(value = "taskId") Integer taskId,
                                @RequestParam(value = "studyType") String studyType,
                                @RequestParam(value = "token") String token,
                                HttpServletRequest request) throws ParseException {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<taskModel> tasklist = new ArrayList<taskModel>();
        List<Task> tasklistsql = fzhktService.getTaskListAll();
        Map<Integer, Task> task_map = tasklistsql.stream().collect(Collectors.toMap(Task::getId, Function.identity(), (key1, key2) -> key2));
        List<Exam> examList=fzhktService.getExamListAll();
        Map<Integer,Exam> examMap=examList.stream().collect(Collectors.toMap(Exam::getId, Function.identity(), (key1, key2) -> key2));
        List<UserScoreRecordPOJO> userScoreRecordPOJO=studyRecordService.getUserStudyRecordAndUserInfo(beginDate,endDate,userId,studyType,taskId);
        List<UserScoreRecordPOJO> dataList = new ArrayList<UserScoreRecordPOJO>();
        for (UserScoreRecordPOJO newuserScoreRecordPOJO : userScoreRecordPOJO) {
            User u=new User();
            u=userService.getUserById(newuserScoreRecordPOJO.getUserId());
            UserScoreRecordPOJO m = new UserScoreRecordPOJO();
            m.setId(newuserScoreRecordPOJO.getId());
            m.setMacAddress(newuserScoreRecordPOJO.getMacAddress());
            m.setIpAddress(newuserScoreRecordPOJO.getIpAddress());
            m.setUserId(newuserScoreRecordPOJO.getUserId());
            m.setUserTrueName(u.getRelName());
            m.setUserStNum(u.getStNum());
            m.setTaskId(newuserScoreRecordPOJO.getTaskId());
            m.setOperateId(newuserScoreRecordPOJO.getOperateId());
            m.setStudyType(newuserScoreRecordPOJO.getStudyType());
            m.setClassName(u.getClassName());
            String beginTime=Long.toString(newuserScoreRecordPOJO.getBeginTime());
            m.setStudyDate(DateUtils.stampToDate(beginTime));

            double score=0;
            long leartime=0;
            //根据用户id和日期和任务单id进行分组查询对应的任务
            List<UserScoreRecordPOJO> operateid=studyRecordService.getoperateid(DateUtils.stampToDates(beginTime),newuserScoreRecordPOJO.getUserId(),newuserScoreRecordPOJO.getTaskId());
            for (UserScoreRecordPOJO operateidlist : operateid) {
                //根据分组的operateid查询最大的成绩
                List<UserScoreRecordPOJO> maxscore=studyRecordService.getmaxscore(DateUtils.stampToDates(beginTime),newuserScoreRecordPOJO.getUserId(),newuserScoreRecordPOJO.getTaskId(),operateidlist.getOperateId());
                score+=maxscore.get(0).getScore();
            }
            m.setScore(score);
            //根据用户id和日期和任务单id进行查询对应的任务
            List<UserScoreRecordPOJO> learnTime=studyRecordService.getLearnTime(DateUtils.stampToDates(beginTime),newuserScoreRecordPOJO.getUserId(),newuserScoreRecordPOJO.getTaskId());
            for (UserScoreRecordPOJO learnTimes : learnTime) {
                leartime+=learnTimes.getEndTime()-learnTimes.getBeginTime();
            }
            m.setLearTime(leartime/1000);

            m.settGroupName(u.getGroupName());
            if(newuserScoreRecordPOJO.getStudyType()==0){
                //任务
                Task t = task_map.get(newuserScoreRecordPOJO.getTaskId());
                if(t==null)
                {
                    m.setTaskName("");
                }
                else {
                    m.setTaskName(t.getTaskName());
                }
            }
            else if(newuserScoreRecordPOJO.getStudyType()==1){
                //试卷
                Exam e=examMap.get(newuserScoreRecordPOJO.getTaskId());
                if(e==null) {
                    m.setTaskName(e.getExamName());
                }
                else{
                    m.setTaskName("");
                }
            }
            dataList.add(m);
        }
        return ExcelUtils.exportEmp(dataList,0);
    }

    /**
     * 班级成绩查询详细成绩接口
     */
    @ResponseBody
    @RequestMapping(value = "/selectClassScoreDetail", method = RequestMethod.POST)
    public Result selectClassScoreDetail(@RequestParam(value = "date") String date,
                                   @RequestParam(value = "userId") Integer userId,
                                   @RequestParam(value = "taskId") Integer taskId,
                                   @RequestParam(value = "token") String token,
                                   HttpServletRequest request) throws ParseException {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<Operate> operatelistsql = fzhktService.getOprateList();
        List<Questions> questionlistsql = fzhktService.getQuestionListAll();
        Map<Integer, Operate> operate_map = operatelistsql.stream().collect(Collectors.toMap(Operate::getId, Function.identity(), (key1, key2) -> key2));
        Map<Integer, Questions> questions_map = questionlistsql.stream().collect(Collectors.toMap(Questions::getId, Function.identity(), (key1, key2) -> key2));
        List<UserScoreRecordPOJO> userScoreRecordPOJO=studyRecordService.getUserStudyRecordDetail(date,userId,taskId);
        List<UserScoreRecordPOJO> dataList = new ArrayList<UserScoreRecordPOJO>();
        for (UserScoreRecordPOJO newuserScoreRecordPOJO : userScoreRecordPOJO) {
            UserScoreRecordPOJO m = new UserScoreRecordPOJO();
            m.setId(newuserScoreRecordPOJO.getId());
            m.setUserId(newuserScoreRecordPOJO.getUserId());
            m.setTaskId(newuserScoreRecordPOJO.getTaskId());
            m.setOperateId(newuserScoreRecordPOJO.getOperateId());
            m.setBeginTime(newuserScoreRecordPOJO.getBeginTime());
            m.setMacAddress(newuserScoreRecordPOJO.getMacAddress());
            m.setIpAddress(newuserScoreRecordPOJO.getIpAddress());
            m.setUserTrueName(newuserScoreRecordPOJO.getUserTrueName());
            m.setClassName(newuserScoreRecordPOJO.getClassName());
            m.settGroupName(newuserScoreRecordPOJO.getGroupName());
            m.setStudyDate(newuserScoreRecordPOJO.getStudyDate());
            m.setStudyType(newuserScoreRecordPOJO.getStudyType());
            if(newuserScoreRecordPOJO.getStudyType()==0) {
                Operate t = operate_map.get(newuserScoreRecordPOJO.getOperateId());
                m.setTaskName(t.getOperateName());
            }
            else if(newuserScoreRecordPOJO.getStudyType()==1){
                Questions q = questions_map.get(newuserScoreRecordPOJO.getOperateId());
                Operate t = operate_map.get(q.getOperateId());
                m.setTaskName(t.getOperateName());
            }

            m.setLearTime(newuserScoreRecordPOJO.getLearTime()/1000);
            m.setTotalScore(newuserScoreRecordPOJO.getTotalScore());
            dataList.add(m);
        }
        responseDataMap.put("list", dataList);
        r.setData(responseDataMap);
        return r;
    }

    /**
     *:4：个人成绩查询接口
     */
    @ResponseBody
    @RequestMapping(value = "/selectPersonScore", method = RequestMethod.POST)
    public Result selectPersonScore(@RequestParam(value = "userTrueName") String userTrueName,
                                         @RequestParam(value = "token") String token,
                                         HttpServletRequest request) throws ParseException {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<Task> tasklistsql = fzhktService.getTaskListAll();
        Map<Integer, Task> task_map = tasklistsql.stream().collect(Collectors.toMap(Task::getId, Function.identity(), (key1, key2) -> key2));
        List<Exam> examList=fzhktService.getExamListAll();
        Map<Integer,Exam> examMap=examList.stream().collect(Collectors.toMap(Exam::getId, Function.identity(), (key1, key2) -> key2));
        //根据学生姓名查询学生id
        String strUserId="";
        List<User> userList=userService.getUserByTrueName(userTrueName);
        for (User user : userList) {
            strUserId+=user.getId();
        }
        List<UserScoreRecordPOJO> userScoreRecordPOJO=studyRecordService.getUserStudyRecordAndUserInfoforPerson(strUserId);
        List<UserScoreRecordPOJO> dataList = new ArrayList<UserScoreRecordPOJO>();
        for (UserScoreRecordPOJO newuserScoreRecordPOJO : userScoreRecordPOJO) {
            UserScoreRecordPOJO m = new UserScoreRecordPOJO();
            m.setId(newuserScoreRecordPOJO.getId());//id
            m.setUserId(newuserScoreRecordPOJO.getUserId());//用户ID
            m.setTaskId(newuserScoreRecordPOJO.getTaskId());
            m.setOperateId(newuserScoreRecordPOJO.getOperateId());
            m.setClassName(newuserScoreRecordPOJO.getClassName());
            m.settGroupName(newuserScoreRecordPOJO.getGroupName());
            m.setStudyDate(newuserScoreRecordPOJO.getStudyDate());//学习时间
            m.setUserStNum(newuserScoreRecordPOJO.getUserStNum());//学号
            m.setUserTrueName(newuserScoreRecordPOJO.getUserTrueName());//姓名
            m.setMacAddress(newuserScoreRecordPOJO.getMacAddress());//机器号
            m.setStudyType(newuserScoreRecordPOJO.getStudyType());//学习模式
            if(newuserScoreRecordPOJO.getStudyType()==0) {
                //任务单
                Task t = task_map.get(newuserScoreRecordPOJO.getTaskId());
                if(t==null) {
                    m.setTaskName("");
                }else{
                    m.setTaskName(t.getTaskName());
                }
            }
            else if(newuserScoreRecordPOJO.getStudyType()==1){
                //试卷
                Exam e=examMap.get(newuserScoreRecordPOJO.getTaskId());
                if(e==null) {
                    m.setTaskName("");//试卷
                }else{
                    m.setTaskName(e.getExamName());//试卷
                }
            }
            m.setIpAddress(newuserScoreRecordPOJO.getIpAddress());//IP地址
            double score=0;
            long leartime=0;
            //根据用户id和日期和任务单id进行分组查询对应的任务
            List<UserScoreRecordPOJO> operateid=studyRecordService.getoperateid(DateUtils.stampToDates(newuserScoreRecordPOJO.getBeginTime().toString()),newuserScoreRecordPOJO.getUserId(),newuserScoreRecordPOJO.getTaskId());
            for (UserScoreRecordPOJO operateidlist : operateid) {
                //根据分组的operateid查询最大的成绩
                List<UserScoreRecordPOJO> maxscore=studyRecordService.getmaxscore(DateUtils.stampToDates(newuserScoreRecordPOJO.getBeginTime().toString()),newuserScoreRecordPOJO.getUserId(),newuserScoreRecordPOJO.getTaskId(),operateidlist.getOperateId());
                score+=maxscore.get(0).getScore();
            }
            m.setScore(score);
            //根据用户id和日期和任务单id进行查询对应的任务
            List<UserScoreRecordPOJO> learnTime=studyRecordService.getLearnTime(DateUtils.stampToDates(newuserScoreRecordPOJO.getBeginTime().toString()),newuserScoreRecordPOJO.getUserId(),newuserScoreRecordPOJO.getTaskId());
            for (UserScoreRecordPOJO learnTimes : learnTime) {
                leartime+=learnTimes.getEndTime()-learnTimes.getBeginTime();
            }
            m.setLearTime(leartime/1000);
            dataList.add(m);
        }
        responseDataMap.put("list", dataList);
        r.setData(responseDataMap);
        return r;
    }

    /**
     *:5：个人成绩查询导出Excel接口
     */
    @ResponseBody
    @RequestMapping(value = "/personScoreExcel", method = RequestMethod.POST)
    public ResponseEntity<byte[]> personScoreExcel(@RequestParam(value = "userTrueName") String userTrueName,
                                                   @RequestParam(value = "token") String token,
                                                  HttpServletRequest request) throws ParseException {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<taskModel> tasklist = new ArrayList<taskModel>();
        List<Task> tasklistsql = fzhktService.getTaskListAll();
        Map<Integer, Task> task_map = tasklistsql.stream().collect(Collectors.toMap(Task::getId, Function.identity(), (key1, key2) -> key2));
        List<Exam> examList=fzhktService.getExamListAll();
        Map<Integer,Exam> examMap=examList.stream().collect(Collectors.toMap(Exam::getId, Function.identity(), (key1, key2) -> key2));
        //根据学生姓名查询学生id
        String strUserId="";
        List<User> userList=userService.getUserByTrueName(userTrueName);
        for (User user : userList) {
            strUserId+=user.getId();
        }
        List<UserScoreRecordPOJO> userScoreRecordPOJO=studyRecordService.getUserStudyRecordAndUserInfoforPerson(strUserId);
        List<UserScoreRecordPOJO> dataList = new ArrayList<UserScoreRecordPOJO>();
        for (UserScoreRecordPOJO newuserScoreRecordPOJO : userScoreRecordPOJO) {
            User u=new User();
            u=userService.getUserById(newuserScoreRecordPOJO.getUserId());
            UserScoreRecordPOJO m = new UserScoreRecordPOJO();
            m.setId(newuserScoreRecordPOJO.getId());
            m.setMacAddress(newuserScoreRecordPOJO.getMacAddress());
            m.setIpAddress(newuserScoreRecordPOJO.getIpAddress());
            m.setUserId(newuserScoreRecordPOJO.getUserId());
            m.setUserTrueName(u.getRelName());
            m.setUserStNum(u.getStNum());
            m.setTaskId(newuserScoreRecordPOJO.getTaskId());
            m.setOperateId(newuserScoreRecordPOJO.getOperateId());
            m.setStudyType(newuserScoreRecordPOJO.getStudyType());
            m.setClassName(u.getClassName());
            m.setStudyDate(newuserScoreRecordPOJO.getStudyDate());

            double score=0;
            long leartime=0;
            //根据用户id和日期和任务单id进行分组查询对应的任务
            List<UserScoreRecordPOJO> operateid=studyRecordService.getoperateid(DateUtils.stampToDates(newuserScoreRecordPOJO.getBeginTime().toString()),newuserScoreRecordPOJO.getUserId(),newuserScoreRecordPOJO.getTaskId());
            for (UserScoreRecordPOJO operateidlist : operateid) {
                //根据分组的operateid查询最大的成绩
                List<UserScoreRecordPOJO> maxscore=studyRecordService.getmaxscore(DateUtils.stampToDates(newuserScoreRecordPOJO.getBeginTime().toString()),newuserScoreRecordPOJO.getUserId(),newuserScoreRecordPOJO.getTaskId(),operateidlist.getOperateId());
                score+=maxscore.get(0).getScore();
            }
            m.setScore(score);
            //根据用户id和日期和任务单id进行查询对应的任务
            List<UserScoreRecordPOJO> learnTime=studyRecordService.getLearnTime(DateUtils.stampToDates(newuserScoreRecordPOJO.getBeginTime().toString()),newuserScoreRecordPOJO.getUserId(),newuserScoreRecordPOJO.getTaskId());
            for (UserScoreRecordPOJO learnTimes : learnTime) {
                leartime+=learnTimes.getEndTime()-learnTimes.getBeginTime();
            }
            m.setLearTime(leartime/1000);
            m.settGroupName(u.getGroupName());
            if(newuserScoreRecordPOJO.getStudyType()==0) {
                //任务单
                Task t = task_map.get(newuserScoreRecordPOJO.getTaskId());
                if(t==null) {
                    m.setTaskName("");
                }else{
                    m.setTaskName(t.getTaskName());
                }
            }
            else if(newuserScoreRecordPOJO.getStudyType()==1){
                //试卷
                Exam e=examMap.get(newuserScoreRecordPOJO.getTaskId());
                if(e==null) {
                    m.setTaskName("");//试卷
                }else{
                    m.setTaskName(e.getExamName());//试卷
                }
            }
            dataList.add(m);
        }
        return ExcelUtils.exportEmp(dataList,1);
    }

    /**
     * 6：个人成绩查询详细成绩接口
     */
    @ResponseBody
    @RequestMapping(value = "/selectPersonScoreDetail", method = RequestMethod.POST)
    public Result selectPersonScoreDetail(@RequestParam(value = "date") String date,
                                         @RequestParam(value = "userId") Integer userId,
                                         @RequestParam(value = "taskId") Integer taskId,
                                         @RequestParam(value = "token") String token,
                                         HttpServletRequest request) throws ParseException {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<Operate> operatelistsql = fzhktService.getOprateList();
        List<Questions> questionlistsql = fzhktService.getQuestionListAll();
        Map<Integer, Operate> operate_map = operatelistsql.stream().collect(Collectors.toMap(Operate::getId, Function.identity(), (key1, key2) -> key2));
        Map<Integer, Questions> questions_map = questionlistsql.stream().collect(Collectors.toMap(Questions::getId, Function.identity(), (key1, key2) -> key2));
        List<UserScoreRecordPOJO> userScoreRecordPOJO=studyRecordService.getUserStudyRecordDetail(date,userId,taskId);
        List<UserScoreRecordPOJO> dataList = new ArrayList<UserScoreRecordPOJO>();
        for (UserScoreRecordPOJO newuserScoreRecordPOJO : userScoreRecordPOJO) {
            UserScoreRecordPOJO m = new UserScoreRecordPOJO();
            m.setId(newuserScoreRecordPOJO.getId());
            m.setUserId(newuserScoreRecordPOJO.getUserId());
            m.setTaskId(newuserScoreRecordPOJO.getTaskId());
            m.setOperateId(newuserScoreRecordPOJO.getOperateId());
            m.setBeginTime(newuserScoreRecordPOJO.getBeginTime());
            m.setMacAddress(newuserScoreRecordPOJO.getMacAddress());
            m.setIpAddress(newuserScoreRecordPOJO.getIpAddress());
            m.setUserTrueName(newuserScoreRecordPOJO.getUserTrueName());
            m.setClassName(newuserScoreRecordPOJO.getClassName());
            m.settGroupName(newuserScoreRecordPOJO.getGroupName());
            m.setStudyDate(newuserScoreRecordPOJO.getStudyDate());
            m.setStudyType(newuserScoreRecordPOJO.getStudyType());
            if(newuserScoreRecordPOJO.getStudyType()==0) {
                Operate t = operate_map.get(newuserScoreRecordPOJO.getOperateId());
                m.setTaskName(t.getOperateName());
            }
            else if(newuserScoreRecordPOJO.getStudyType()==1){
                Questions q = questions_map.get(newuserScoreRecordPOJO.getOperateId());
                Operate t = operate_map.get(q.getOperateId());
                m.setTaskName(t.getOperateName());
            }

            m.setLearTime(newuserScoreRecordPOJO.getLearTime()/1000);
            m.setTotalScore(newuserScoreRecordPOJO.getTotalScore());
            dataList.add(m);
        }
        responseDataMap.put("list", dataList);
        r.setData(responseDataMap);
        return r;
    }
}

