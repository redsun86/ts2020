package com.esst.ts.controller;

import com.esst.ts.constants.Constants;
import com.esst.ts.model.*;
import com.esst.ts.service.FZhKTService;
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
        //List<scoreModel> datalist = new ArrayList<scoreModel>();

        List<scoreModel> datalist = new ArrayList();
        List<UserLiveData> userlivedatelist=fzhktService.getUserLiveDataList();
        List<UserLive> userLivelist=fzhktService.getUserLiveList();
        List<Task> tasklistsql=fzhktService.getTaskListAll();
        List<Operate> operateList=fzhktService.getOprateList();
        List<User> userList=fzhktService.getUserListAll();
        Map<Integer,UserLiveData> userlivedate_map=new HashMap<Integer,UserLiveData>();
        Map<Integer,User> userMap=userList.stream().collect(Collectors.toMap(User::getId, Function.identity(), (key1, key2) -> key2));

        Map<Integer,Task> task_map=tasklistsql.stream().collect(Collectors.toMap(Task::getId, Function.identity(), (key1, key2) -> key2));
        Map<Integer,Operate>operate_map=operateList.stream().collect(Collectors.toMap(Operate::getId, Function.identity(), (key1, key2) -> key2));
        Map<Integer,UserLive>userLive_map=userLivelist.stream().collect(Collectors.toMap(UserLive::getId, Function.identity(), (key1, key2) -> key2));
        for(UserLiveData uld:userlivedatelist) {
            userlivedate_map.put(uld.getId(),uld);
        }
        for (UserLiveData uld:userlivedatelist)
        {
            scoreModel _score=new scoreModel();
            _score.setId(uld.getId().toString());
            _score.setMachine_id(uld.getMacAddress());
            User user=userMap.get(uld.getUserId());
            _score.setStudent_num(user.getStNum());
            _score.setUser_name(user.getUserName());
            //if (uld.getstudyType()==0)
            {
                Task t=task_map.get(uld.getTaskId());
                _score.setTemplate_id(t.getId().toString());
                _score.setTemplate_name(t.getTaskName());
                Operate operate=operate_map.get(uld.getTaskId());
                _score.setTask_id(operate.getId().toString());
                _score.setTask_name(operate.getOperateName());
            }
            //else if(uld.getstudyType()==1)
            {

            }
            _score.setScore(uld.getCurrentScore().toString());
            _score.setTotal_score(uld.getTotalScore().toString());
            _score.setLearning_time(uld.getStudyDuration().toString());
            datalist.add(_score);
        }
        for (UserLive uld:userLivelist)
        {
            scoreModel _score=new scoreModel();
            _score.setId(uld.getId().toString());
            _score.setMachine_id(uld.getMacAddress());
            User user=userMap.get(uld.getUserId());
            _score.setStudent_num(user.getStNum());
            _score.setUser_name(user.getUserName());
            //if (uld.getstudyType()==0)
            {
                Task t=task_map.get(uld.getTaskId());
                _score.setTemplate_id(t.getId().toString());
                _score.setTemplate_name(t.getTaskName());
                Operate operate=operate_map.get(uld.getTaskId());
                _score.setTask_id(operate.getId().toString());
                _score.setTask_name(operate.getOperateName());
            }
            //else if(uld.getstudyType()==1)
            {

            }
            _score.setScore(uld.getCurrentScore().toString());
            _score.setTotal_score(uld.getTotalScore().toString());
            _score.setLearning_time(uld.getStudyDuration().toString());
            datalist.add(_score);
        }
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

        //<editor-fold desc="临时数据">
        List<String> stuLst = new ArrayList<String>();
        stuLst.add("李智");
        stuLst.add("梁建磊");
        stuLst.add("曹瑞卿");
        stuLst.add("史红阳");
        stuLst.add("专家");
        //</editor-fold>
        online_num = 0;

        //</editor-fold>
        List<taskModel> tasklist = new ArrayList<taskModel>();
        //<editor-fold desc="任务列表赋值：tasklist">
        List<Task> m1lst = fzhktService.getCourseTaskLst(1);
        for (int i = 0; i < m1lst.size(); i++) {
            taskModel m = new taskModel();
            m.setTask_id(toString().valueOf(m1lst.get(i).getId()));
            m.setTask_name(m1lst.get(i).getTaskName());
            tasklist.add(m);
        }
        //</editor-fold>
        //<editor-fold desc="统计数据赋值">
        String class_name;      //班级名称
        String teacher_name;    //教师名称
        total_num = datalist.size();
        class_name = "班级名称";
        teacher_name = "教师名称";
        //</editor-fold>
        //<editor-fold desc="返回参数赋值">
        Map<String, Object> FZhKTMap = new HashMap<>();
        FZhKTMap.put("datalist", datalist);
        FZhKTMap.put("tasklist", tasklist);
        FZhKTMap.put("total_num", total_num);
        FZhKTMap.put("teacher_name", teacher_name);
        FZhKTMap.put("class_name", class_name);
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
                              @RequestParam(value = "ip_adress") String id_adress,
                              @RequestParam(value = "task_id") int task_id,
                              @RequestParam(value = "operate_id") int operate_id,
                              @RequestParam(value = "status") String status,
                              @RequestParam(value = "current_score") double current_score,
                              @RequestParam(value = "total_score") double total_score,
                              @RequestParam(value = "score_detail") String score_detail,
                              @RequestParam(value = "client_status") int client_status,
                              @RequestParam(value = "study_type") int study_type,
                              @RequestParam(value = "token", required = true) String token,
                              HttpServletRequest request) {
        RequestContext requestcontext = new RequestContext(request);
        Result r = new Result();
        if (client_status == 0) {//第一次传成绩
            //user_score_recored插入数据
            UserScoreRecord usrScore = new UserScoreRecord();
            usrScore.setUserId(user_id);
            usrScore.setTaskId(task_id);
            usrScore.setOperateId(operate_id);
            usrScore.setScore(current_score);
            usrScore.setTotalScore(total_score);
            Date date = new Date();
            usrScore.setBeginTime(date.getTime());
            fzhktService.insertUserScoreRecore(usrScore);
            //user_live_data
            UserLiveDataWithBLOBs uldscore = new UserLiveDataWithBLOBs();
            uldscore.setUserId(user_id);
            uldscore.setIdAddress(id_adress);
            uldscore.setTaskId(task_id);
            uldscore.setOperateId(operate_id);
            uldscore.setCurrentScore(current_score);
            uldscore.setTotalScore(total_score);
            uldscore.setScoreDetail(score_detail);
            uldscore.setStudyDuration(0.00);
            uldscore.setUpdatetime(new Date());
            fzhktService.insertUserLiveDataWithBLOBS(uldscore);

            Constants.userscorerecord_map.put(user_id, usrScore);

        } else if (client_status == 1) {

            UserScoreRecord usrecord = Constants.userscorerecord_map.get(user_id);
            if (usrecord==null)
            {
                //List<UserScoreRecord>=fzhktService.getUserScoreRecore(user_id,task_id,operate_id);
            }
            //user_live_data
            UserLiveDataWithBLOBs uldscore = new UserLiveDataWithBLOBs();
            uldscore.setUserId(user_id);
            uldscore.setIdAddress(id_adress);
            uldscore.setTaskId(task_id);
            uldscore.setOperateId(operate_id);
            uldscore.setCurrentScore(current_score);
            uldscore.setTotalScore(total_score);
            uldscore.setScoreDetail(score_detail);
            uldscore.setUpdatetime(new Date());
            //计算培训时长
            double duration = new Date().getTime() - usrecord.getBeginTime();
            uldscore.setStudyDuration(duration);
            fzhktService.insertUserLiveDataWithBLOBS(uldscore);
        } else if (client_status == 2) {
            UserScoreRecord usrecord = Constants.userscorerecord_map.get(user_id);
            usrecord.setEndTime(new Date().getTime());
            fzhktService.updateUserScoreRecored(usrecord);

            UserLiveWithBLOBs ulscore = new UserLiveWithBLOBs();
            ulscore.setUserId(user_id);
            ulscore.setIdAddress(id_adress);
            ulscore.setTaskId(task_id);
            ulscore.setOperateId(operate_id);
            ulscore.setCurrentScore(current_score);
            ulscore.setTotalScore(total_score);
            ulscore.setScoreDetail(score_detail);
            ulscore.setUpdatetime(new Date());
            //计算培训时长
            double duration = new Date().getTime() - usrecord.getBeginTime();
            ulscore.setStudyDuration(duration);
            fzhktService.inserUserLiveWithBLOBS(ulscore);
            Constants.userscorerecord_map.remove(111);
        }
        r.setMsg("更新成功");
        return r;
    }
}
