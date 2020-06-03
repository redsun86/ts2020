package com.esst.ts.controller;

import com.esst.ts.model.Result;
import com.esst.ts.model.scoreModel;
import com.esst.ts.model.taskModel;
import com.esst.ts.service.UserService;
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
import javax.swing.*;
import java.util.*;

/**
 * 仿真课堂模块对应的接口；仿真课堂 ==> FangZhenKeTang ==> FZhKT
 *
 */
@Controller
@RequestMapping("/web/v1/fang")
public class FZhKTController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    //@Resource
    //private UserService userService;

    /** 实时数据列表接口
     * @param userId
     * @param templateId
     * @param strToken
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/realtimedata",method = RequestMethod.GET)
    public Result realtimedata(
            @RequestParam(value = "user_id",required = false) String userId,
            @RequestParam(value = "template_id",required = false) String templateId,
            @RequestParam(value="token",required = true) String strToken,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(200);
        //</editor-fold>
        List<scoreModel> datalist = new ArrayList<scoreModel>();
        //<editor-fold desc="实时数据列表赋值：datalist">
        for (int i = 0; i < 5; i++) {
            scoreModel m = new scoreModel();
            m.setId(toString().valueOf(i + 1));
            m.setMachine_id("PC01");
            m.setUser_name("张三");
            m.setStudent_num("1100");
            m.setTemplate_id("任务单或试卷id");
            m.setTemplate_name("任务单或试卷名");
            m.setTask_id("任务或试题id");
            m.setTask_name("任务或试题名");
            m.setScore("10.3");
            m.setTotal_score("60.1");
            m.setLearning_time("25.6");
            m.setStatus("1");
            m.setDetailesscore("带排版的详细成绩");
            m.setReport_url("www.esonline.com/report.pdf");
            datalist.add(m);
        }
        //</editor-fold>
        List<taskModel> tasklist = new ArrayList<taskModel>();
        //<editor-fold desc="任务列表赋值：tasklist">
        for (int i = 0; i < 5; i++) {
            taskModel m = new taskModel();
            m.setTask_id(toString().valueOf(i + 1));
            m.setTask_name("任务" + toString().valueOf(i + 1));
            tasklist.add(m);
        }
        //</editor-fold>
        //<editor-fold desc="统计数据赋值">
        int total_num;          //总人数
        int online_num;         //在线人数
        String class_name;      //班级名称
        String teacher_name;    //教师名称
        total_num = 37;
        online_num = 29;
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

}
