package com.esst.ts.controller;

import com.esst.ts.model.*;
import com.esst.ts.service.StudyRecordService;
import com.esst.ts.service.TaskService;
import com.esst.ts.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private TaskService taskService;

    /**
     * 成绩查询万年历接口
     */
    @ResponseBody
    @RequestMapping("/selectScore")
    public Result selectScore(@RequestParam(value = "date") String date,
                             @RequestParam(value = "token") String token,
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
            List<UserLoginLog> userLoginLogListForUserId=studyRecordService.getUserloginLogForDate(userLoginLogList.get(i).getNewcreatetime(),userLoginLogList.get(i).getNewcreatetime());
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
     * 班级成绩查询接口
     */
    @ResponseBody
    @RequestMapping("/selectClassScore")
    public Result selectClassScore(@RequestParam(value = "begainDate") String begainDate,
                                   @RequestParam(value = "endDate") String endDate,
                                   @RequestParam(value = "userId") Integer userId,
                              @RequestParam(value = "token") String token,
                              HttpServletRequest request) throws ParseException {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<UserScoreRecordPOJO> userScoreRecordPOJO=studyRecordService.getUserStudyRecordAndUserInfo(begainDate,endDate,userId);
        List<UserScoreRecordPOJO> dataList = new ArrayList<UserScoreRecordPOJO>();
        for (UserScoreRecordPOJO newuserScoreRecordPOJO : userScoreRecordPOJO) {
            User u=new User();
            Task t=new Task();
            //taskService.
            u=userService.getUserById(newuserScoreRecordPOJO.getUserId());
            UserScoreRecordPOJO m = new UserScoreRecordPOJO();
            m.setId(newuserScoreRecordPOJO.getId());
            m.setMacAddress(newuserScoreRecordPOJO.getMacAddress());
//            m.setIpAddress(newuserScoreRecordPOJO.getIpAddress());
//            m.setUserId(newuserScoreRecordPOJO.getUserId());
//            m.setUserTrueName(u.getRelName());
//            m.setUserStNum(u.getStNum());
//            m.setTaskId(newuserScoreRecordPOJO.getTaskId());
//            m.setTaskName("任务名称");
//            m.setOperateId(newuserScoreRecordPOJO.getOperateId());
//            m.setStudyType(newuserScoreRecordPOJO.getStudyType());
//            m.setClassName(u.getClassName());
//            m.setTotalScore(newuserScoreRecordPOJO.getTotalScore());
//            m.setLearTime(newuserScoreRecordPOJO.getLearTime());
//            m.settGroupName(u.getGroupName());
            dataList.add(m);
        }
        responseDataMap.put("list", dataList);
        r.setData(responseDataMap);
        return r;
    }
}
