package com.esst.ts.controller;

import com.esst.ts.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <b>策略库</b>；策略==》strategy
 * <p></p>
 * <p>策略库中包含：任务库、试卷库。暂时将任务库和试卷库的接口写在“策略controller”中，如果后期有需要可以单独创建相应的Controller</p>
 * <p></p>
 * <p>创建标识：梁建磊 2020-06-11</p>
 */
@Controller
@RequestMapping("/web/v1/strategy")
public class StrategyController {
    //<editor-fold desc="Service对象初始化">
    /**
     * 产品包——业务逻辑层接口服务
     */
    @Resource
    private com.esst.ts.service.ProductService ProductService;
    /**
     * 工艺/单元——业务逻辑层接口服务
     */
    @Resource
    private com.esst.ts.service.TechnologyService TechnologyService;
    /**
     * 任务单——业务逻辑层接口服务
     */
    @Resource
    private com.esst.ts.service.TaskService TaskService;
    /**
     * 工况——业务逻辑层接口服务
     */
    @Resource
    private com.esst.ts.service.OperateService OperateService;
    /**
     * 教师任务关系——业务逻辑层接口服务
     */
    @Resource
    private com.esst.ts.service.UserTaskRelationService UserTaskRelationService;
    /**
     * 试卷——业务逻辑层接口服务
     */
    @Resource
    private com.esst.ts.service.ExamService ExamService;
    /**
     * 试题——业务逻辑层接口服务
     */
    @Resource
    private com.esst.ts.service.QuestionsService QuestionsService;
    /**
     * 事故策略——业务逻辑层接口服务
     */
    @Resource
    private com.esst.ts.service.TroubleService TroubleService;
    /**
     * DCS风格——业务逻辑层接口服务
     */
    @Resource
    private com.esst.ts.service.StyleService StyleService;
    /**
     * 运行时标——业务逻辑层接口服务
     */
    @Resource
    private com.esst.ts.service.TimescaleService TimescaleService;
    /**
     * 试卷用户关系——业务逻辑层接口服务
     */
    @Resource
    private com.esst.ts.service.ExamUserRelationService ExamUserRelationService;

    /**
     * 用户——业务逻辑层接口服务
     */
    @Resource
    private com.esst.ts.service.UserService UserService;
    /**
     * 图表统计——业务逻辑层接口服务
     */
    @Resource
    private com.esst.ts.service.StatisticalService StatisticalService;
    /**
     * 教师学生关系——业务逻辑层接口服务
     */
    @Resource
    private com.esst.ts.service.TeacherStudentRelationService TeacherStudentRelationService;

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    //</editor-fold>

    //  1、策略库数据更新接口

    /**
     * @param strToken
     * @param request
     * @return 执行结果；对应接口文档《TS2020接口文档 ---- 策略库模块》 reloadData
     */
    @ResponseBody
    @RequestMapping(value = "/reloadData", method = RequestMethod.GET)
    public Result reloadData(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        //responseDataMap.put("respMsg", "授权网站更新产品+工艺；ESOL网站更新工况；事故网站更新事故策略");
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  2、获取产品列表接口；从任务库数据列表中解析

    /**
     * @param strToken
     * @param request
     * @return 执行更新操作后返回产品列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getProductData
     */
    @ResponseBody
    @RequestMapping(value = "/getProductData", method = RequestMethod.GET)
    public Result getProductData(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        //</editor-fold>
        Map<String, Object> responseDataMap = new HashMap<>();
        List<Product> proLst = null;
        try {
            proLst = ProductService.GetList();
            responseDataMap.put("dataList", proLst);
            ProductCountPOJO pcPojo = ProductService.GetModel();
            responseDataMap.put("productCount", pcPojo.getProductCount());
            responseDataMap.put("technologyCount", pcPojo.getTechnologyCount());
            responseDataMap.put("taskCount", pcPojo.getTaskCount());
            responseDataMap.put("operateCount", pcPojo.getOperateCount());
        } catch (Exception e) {
            //e.printStackTrace();
            responseDataMap.put("respMsg", e.getMessage());
        }
        r.setData(responseDataMap);
        return r;
    }

    //  3、获取任务库列表数据接口；需要与数据库交互

    /**
     * @param technologyName 工艺/单元 名称
     * @param strToken
     * @param request
     * @return 任务库列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getTechnologyAllList
     */
    @ResponseBody
    @RequestMapping(value = "/getTechnologyAllList", method = RequestMethod.GET)
    public Result getTechnologyAllList(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "productId", required = false) String productId,
            @RequestParam(value = "technologyName", required = false) String technologyName,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        //</editor-fold>
        Map<String, Object> responseDataMap = new HashMap<>();

        List<TechnologyPOJO> techPojoLst; //工艺/单元
        List<TechnologyTaskPOJO> taskPojolst; //任务单
        List<Operate> operLst; //工况

        //<editor-fold desc="从数据库中读取 工艺/单元、任务单、工况">
        TechnologyPOJO reqMod = new TechnologyPOJO();
        if (productId != null && productId != "" && productId != "0") {
            reqMod.setProductId(Integer.valueOf(productId));
        }
        techPojoLst = TechnologyService.GetPojoAllList(reqMod);

        String reqUserIds = "";
        User umod = UserService.getUserById(Integer.valueOf(userId));
        if (null != umod && umod.getIsAdmin() == 1) {
            reqUserIds = userId;
            taskPojolst = TaskService.GetList(reqUserIds, 0, 1);
        } else {
            TeacherStudentRelation reqTeacherMod = new TeacherStudentRelation();
            reqTeacherMod.setIsDel(0);
            reqTeacherMod.setStudentId(Integer.valueOf(userId));
            List<TeacherStudentRelation> teacherList = TeacherStudentRelationService.GetList(reqTeacherMod);
            if (null != teacherList && teacherList.size() > 0) {
                List<String> idList = new ArrayList<>();
                for (TeacherStudentRelation tmod : teacherList) {
                    idList.add(String.valueOf(tmod.getTeacherId()));
                }
                if (idList.size() > 0) reqUserIds = String.join(",", idList);
            }
            taskPojolst = TaskService.GetList(reqUserIds, 0, 0);
        }

        Operate operateReqMod = new Operate();
        operLst = OperateService.GetList(operateReqMod);
        //</editor-fold>

        //<editor-fold desc="任务单、工况 合并">
        Map<String, List<Operate>> mapOperLst = new HashMap<>();
        for (int i = 0; i < operLst.size(); i++) {
            Operate mod = operLst.get(i);
            int pid = mod.getTaskId();
            if (mapOperLst.containsKey(toString().valueOf(pid))) {
                mapOperLst.get(toString().valueOf(pid)).add(mod);
            } else {
                List<Operate> dlist = new ArrayList<>();
                dlist.add(mod);
                mapOperLst.put(toString().valueOf(pid), dlist);
            }
        }
        for (TechnologyTaskPOJO taskPojo : taskPojolst) {
            taskPojo.setOperateList(mapOperLst.get(toString().valueOf(taskPojo.getId())));
        }
        //</editor-fold>

        //<editor-fold desc="工艺/单元、任务单 合并">
        Map<String, List<TechnologyTaskPOJO>> mapTaskPojolst = new HashMap<>();
        for (int i = 0; i < taskPojolst.size(); i++) {
            TechnologyTaskPOJO mod = taskPojolst.get(i);
            int pid = mod.getTechnologyId();
            if (mapTaskPojolst.containsKey(toString().valueOf(pid))) {
                mapTaskPojolst.get(toString().valueOf(pid)).add(mod);
            } else {
                List<TechnologyTaskPOJO> dlist = new ArrayList<>();
                dlist.add(mod);
                mapTaskPojolst.put(toString().valueOf(pid), dlist);
            }
        }
        for (TechnologyPOJO techPojo : techPojoLst) {
            techPojo.setTaskList(mapTaskPojolst.get(toString().valueOf(techPojo.getId())));
        }
        //</editor-fold>

        responseDataMap.put("dataList", techPojoLst);
        r.setData(responseDataMap);
        return r;
    }

    //  4、发布任务

    /**
     * @param strToken
     * @param request
     * @return 执行结果；对应接口文档《TS2020接口文档 ---- 策略库模块》 releaseTask
     */
    @ResponseBody
    @RequestMapping(value = "/releaseTask", method = RequestMethod.GET)
    public Result releaseTask(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "taskIds", required = false) String taskIds,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("请求失败"));
        r.setCode(Result.ERROR);
        //</editor-fold>

        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();
        int rowsCount = 0;
        try {
            UserTaskRelationService.insertTaskIds(taskIds, Integer.valueOf(userId));
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(Result.SUCCESS);
        } catch (Exception e) {
            //e.printStackTrace();
            responseDataMap.put("respMsg", e.getMessage());
        }
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  5、取消发布任务

    /**
     * @param strToken
     * @param request
     * @return 执行结果；对应接口文档《TS2020接口文档 ---- 策略库模块》 unReleaseTask
     */
    @ResponseBody
    @RequestMapping(value = "/unReleaseTask", method = RequestMethod.GET)
    public Result unReleaseTask(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "taskIds", required = false) String taskIds,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("请求失败"));
        r.setCode(Result.ERROR);
        //</editor-fold>

        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();
        int rowsCount = 0;
        try {
            UserTaskRelationService.deleteTaskIds(taskIds, Integer.valueOf(userId));
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(Result.SUCCESS);
        } catch (Exception e) {
            //e.printStackTrace();
            //responseDataMap.put("respMsg", e.getMessage());
        }
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  6、获取已开启的任务单列表数据；同3,根据【是否开启】进行检索

    /**
     * @param strToken
     * @param request
     * @return 已开启的任务库列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getOpenTaskList
     */
    @ResponseBody
    @RequestMapping(value = "/getOpenTaskList", method = RequestMethod.GET)
    public Result getOpenTaskList(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<TechnologyTaskPOJO> taskPojolst; //任务单

        // 老师身份的用户id的集合。格式：1,2,3,4
        String reqUserIds = "";
        // 获取指定用户信息
        User umod = UserService.getUserById(Integer.valueOf(userId));
        // 判断指定用户身份：老师、学生
        if (null != umod && umod.getIsAdmin() == 1) {
            // 老师身份
            reqUserIds = userId;
            taskPojolst = TaskService.GetList(reqUserIds, 1, 1);
        } else {
            // 学生身份 通过学生id获取所属老师的集合
            TeacherStudentRelation reqTeacherMod = new TeacherStudentRelation();
            // 检索条件-未删除
            reqTeacherMod.setIsDel(0);
            // 检索条件-指定学生id
            reqTeacherMod.setStudentId(Integer.valueOf(userId));
            // 检索条件-在线
            reqTeacherMod.setIsOnline(1);
            List<TeacherStudentRelation> teacherList = TeacherStudentRelationService.GetList(reqTeacherMod);
            if (null != teacherList && teacherList.size() > 0) {
                List<String> idList = new ArrayList<>();
                for (TeacherStudentRelation tmod : teacherList) {
                    idList.add(String.valueOf(tmod.getTeacherId()));
                }
                if (idList.size() > 0) reqUserIds = String.join(",", idList);
            }
            taskPojolst = TaskService.GetList(reqUserIds, 1, 0);
        }
        responseDataMap.put("dataList", taskPojolst);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  7、获取已开启的工况数据

    /**
     * @param strToken
     * @param request
     * @return 已开启的工况数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getOpenTaskOperateList
     */
    @ResponseBody
    @RequestMapping(value = "/getOpenTaskOperateList", method = RequestMethod.GET)
    public Result getOpenTaskOperateList(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "taskId", required = false) String taskId,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();

        List<TechnologyPOJO> techPojoLst; //工艺/单元
        Task taskMod;//任务单实体
        List<Operate> operLst; //工况

        //<editor-fold desc="从数据库中读取 工艺/单元、任务单、工况">
        taskMod = TaskService.selectByPrimaryKey(Integer.valueOf(taskId));

        TechnologyPOJO reqMod = new TechnologyPOJO();
        reqMod.setId(taskMod.getTechnologyId());
        techPojoLst = TechnologyService.GetPojoAllList(reqMod);
        Operate operateReqMod = new Operate();
        operateReqMod.setTaskId(Integer.valueOf(taskId));
        operLst = OperateService.GetList(operateReqMod);
        //</editor-fold>

        //<editor-fold desc="工艺/工况、工况 合并">
        Map<String, List<Operate>> mapOperLst = new HashMap<>();
        for (int i = 0; i < operLst.size(); i++) {
            Operate mod = operLst.get(i);
            int pid = mod.getTechnologyId();
            if (mapOperLst.containsKey(toString().valueOf(pid))) {
                mapOperLst.get(toString().valueOf(pid)).add(mod);
            } else {
                List<Operate> dlist = new ArrayList<>();
                dlist.add(mod);
                mapOperLst.put(toString().valueOf(pid), dlist);
            }
        }
        for (TechnologyPOJO techPojo : techPojoLst) {
            techPojo.setOperateList(mapOperLst.get(toString().valueOf(techPojo.getId())));
        }
        //</editor-fold>

        responseDataMap.put("dataList", techPojoLst);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }


    //  8、获取试卷库列表数据接口；需要与数据库交互

    /**
     * @param strToken
     * @param request
     * @return 试卷库列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getExamList
     */
    @ResponseBody
    @RequestMapping(value = "/getExamList", method = RequestMethod.GET)
    public Result getExamList(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "examName", required = false) String examName,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("请求失败"));
        r.setCode(Result.ERROR);
        //</editor-fold>
        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();
        //List<ExamPOJO> questLst = new ArrayList<ExamPOJO>();
        try {
            Exam reqMod = new Exam();
            reqMod.setCreateUser(Integer.valueOf((userId)));
            reqMod.setIsDeleted(0);
            reqMod.setExamName(examName);
            List<ExamPOJO> examsLst = new ArrayList<>();
            User umod = UserService.getUserById(Integer.valueOf(userId));
            if (null != umod && umod.getIsAdmin() == 1) {
                examsLst = ExamService.GetList(reqMod);
            } else {
                reqMod.setStatus(1);
                examsLst = ExamService.GetListWithStudent(reqMod);
                if (null != examsLst && examsLst.size() > 0) {
                } else {
                    examsLst = ExamService.GetListWithDefault(reqMod);
                }
            }
            int questionsCount = 0;
            for (ExamPOJO mod : examsLst) {
                questionsCount += Integer.valueOf(mod.getQuestionsCount());
            }
            responseDataMap.put("examsCount", examsLst.size());
            responseDataMap.put("questionsCount", questionsCount);
            responseDataMap.put("dataList", examsLst);
        } catch (Exception e) {
            responseDataMap.put("respMsg", e.getMessage());
        }
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  9、新增/编辑试卷数据接口；需要与数据库交互

    /**
     * @param strToken
     * @param request
     * @return 执行结果；对应接口文档《TS2020接口文档 ---- 策略库模块》 editExam
     */
    @ResponseBody
    @RequestMapping(value = "/editExam", method = RequestMethod.POST)
    public Result editExam(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @ModelAttribute("reqMod") Exam reqMod,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("请求失败"));
        r.setCode(Result.ERROR);
        //</editor-fold>

        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();

        int rowsCount = 0;
        try {
            int allowContinue = 1;
            Exam reqCheckMod = new Exam();
            reqCheckMod.setExamName(reqMod.getExamName());
            reqCheckMod.setCreateUser(reqMod.getCreateUser());
            reqCheckMod.setIsDeleted(0);
            List<ExamPOJO> questLst = ExamService.GetList(reqCheckMod);
            if (reqMod.getId() == null || reqMod.getId() == 0 || reqMod.getId() == -1) {
                allowContinue = questLst.size() == 0 ? 1 : 0;
            } else {
                for (ExamPOJO mod : questLst) {
                    if (!mod.getId().equals(reqMod.getId())) allowContinue = 0;
                }
            }
            if (allowContinue == 0) throw new Exception("试卷名称已存在，请更改后重试");
            if (reqMod.getId() == null || reqMod.getId() == 0 || reqMod.getId() == -1) {
                reqMod.setId(null);
                Exam respObj = ExamService.getInsertModel(reqMod);
                responseDataMap.put("reqMod", respObj);
                rowsCount = respObj.getId();
            } else {
                rowsCount = ExamService.update(reqMod);
                responseDataMap.put("reqMod", reqMod);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            r.setMsg(requestContext.getMessage(e.getMessage()));
        }
        if (rowsCount > 0) {
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(Result.SUCCESS);
        }

        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  10、删除试卷数据接口；需要与数据库交互

    /**
     * @param strToken
     * @param request
     * @return 执行结果；对应接口文档《TS2020接口文档 ---- 策略库模块》 deleteExam
     */
    @ResponseBody
    @RequestMapping(value = "/deleteExam", method = RequestMethod.GET)
    public Result deleteExam(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "id", required = false) String Id,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("请求失败"));
        r.setCode(Result.ERROR);
        //</editor-fold>


        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();

        int rowsCount = 0;
        try {
            rowsCount = ExamService.deleteWithId(Integer.valueOf(Id));
        } catch (Exception e) {
        }
        if (rowsCount > 0) {
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(Result.SUCCESS);
        }
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }


    //  11、发布试卷

    /**
     * @param strToken
     * @param request
     * @return 执行结果；对应接口文档《TS2020接口文档 ---- 策略库模块》 releaseExam
     */
    @ResponseBody
    @RequestMapping(value = "/releaseExam", method = RequestMethod.GET)
    public Result releaseExam(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "exameIds", required = false) String exameIds,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("请求失败"));
        r.setCode(Result.ERROR);
        //</editor-fold>

        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();
        try {
            ExamService.updateStatus(exameIds, 1, Integer.valueOf(userId));
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(Result.SUCCESS);
        } catch (Exception e) {
            //e.printStackTrace();
            responseDataMap.put("respMsg", e.getMessage());
        }
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  12、取消发布试卷

    /**
     * @param strToken
     * @param request
     * @return 执行结果；对应接口文档《TS2020接口文档 ---- 策略库模块》 unReleaseExam
     */
    @ResponseBody
    @RequestMapping(value = "/unReleaseExam", method = RequestMethod.GET)
    public Result unReleaseExam(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "exameIds", required = false) String exameIds,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("请求失败"));
        r.setCode(Result.ERROR);
        //</editor-fold>

        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();
        try {
            ExamService.updateStatus(exameIds, 0, Integer.valueOf(userId));
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(Result.SUCCESS);
        } catch (Exception e) {
            //e.printStackTrace();
            responseDataMap.put("respMsg", e.getMessage());
        }
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  13、获取指定试卷的考生列表

    /**
     * @param strToken
     * @param request
     * @return 获取试卷的考生列表；对应接口文档《TS2020接口文档 ---- 策略库模块》 getExamuserList
     */
    @ResponseBody
    @RequestMapping(value = "/getExamuserList", method = RequestMethod.GET)
    public Result getExamuserList(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "exameId", required = false) String exameId,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("请求失败"));
        r.setCode(Result.ERROR);
        //</editor-fold>

        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();
        List<ExamUserRelation> questLst = ExamUserRelationService.GetList(Integer.valueOf(exameId));
        responseDataMap.put("dataList", questLst);
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  14、增加/删除试卷的考生列表

    /**
     * @param strToken
     * @param request
     * @return 执行结果；对应接口文档《TS2020接口文档 ---- 策略库模块》 editExamUser
     */
    @ResponseBody
    @RequestMapping(value = "/editExamUser", method = RequestMethod.POST)
    public Result editExamUser(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "exameId", required = false) String exameId,
            @RequestParam(value = "userIds", required = false) String userIds,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("请求失败"));
        r.setCode(Result.ERROR);
        //</editor-fold>

        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();
        int rowsCount = 0;
        try {
            ExamUserRelationService.deleteUserIds(userIds, exameId);
            ExamUserRelationService.insertUserIds(userIds, exameId);
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(Result.SUCCESS);
        } catch (Exception e) {
            //            e.printStackTrace();
            responseDataMap.put("respMsg", e.getMessage());
        }
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }


    //  15、获取试题列表数据接口；需要与数据库交互

    /**
     * @param strToken
     * @param request
     * @return 试题列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getQuestionsList
     */
    @ResponseBody
    @RequestMapping(value = "/getQuestionsList", method = RequestMethod.GET)
    public Result getQuestionsList(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "exameId", required = false) String exameId,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("请求失败"));
        r.setCode(Result.ERROR);
        //</editor-fold>

        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();
        //List<QuestionsPOJO> questLst = new ArrayList<QuestionsPOJO>();
        try {
            List<QuestionsPOJO> questLst = QuestionsService.GetList(0, Integer.valueOf(exameId));
            responseDataMap.put("dataList", questLst);
        } catch (Exception e) {
            //e.printStackTrace();
            responseDataMap.put("respMsg", e.getMessage());
        }
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  16、新增/编辑试题数据接口；需要与数据库交互

    /**
     * @param strToken
     * @param request
     * @return 执行结果；对应接口文档《TS2020接口文档 ---- 策略库模块》 editQuestions
     */
    @ResponseBody
    @RequestMapping(value = "/editQuestions", method = RequestMethod.POST)
    public Result editQuestions(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @ModelAttribute("reqMod") Questions reqMod,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("请求失败"));
        r.setCode(Result.ERROR);
        //</editor-fold>

        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();
        int rowsCount = 0;
        try {
            if (reqMod.getQuestionName() == null || reqMod.getQuestionName() == "") {
                Operate operateReqMod = new Operate();
                operateReqMod.setId(reqMod.getOperateId());
                List<Operate> operLst = OperateService.GetList(operateReqMod);
                if (null != operLst && operLst.size() > 0) {
                    reqMod.setQuestionName(operLst.get(0).getOperateName());
                }
            }

            if (reqMod.getId() == null || reqMod.getId() == 0 || reqMod.getId() == -1) {
                reqMod.setId(null);

                Questions respObj = QuestionsService.getInsertModel(reqMod);
                responseDataMap.put("reqMod", respObj);
                rowsCount = respObj.getId();
            } else {
                rowsCount = QuestionsService.update(reqMod);
                responseDataMap.put("reqMod", reqMod);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            responseDataMap.put("respMsg", e.getMessage());
        }
        if (rowsCount > 0) {
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(Result.SUCCESS);
        }
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  17、删除试题数据接口；需要与数据库交互

    /**
     * @param strToken
     * @param request
     * @return 执行结果；对应接口文档《TS2020接口文档 ---- 策略库模块》 deleteQuestions
     */
    @ResponseBody
    @RequestMapping(value = "/deleteQuestions", method = RequestMethod.GET)
    public Result deleteQuestions(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "id", required = false) String Id,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        //返回参数
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("请求失败"));
        r.setCode(Result.ERROR);
        //</editor-fold>


        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();
        int rowsCount = 0;
        try {
            rowsCount = QuestionsService.deleteWithId(Integer.valueOf(Id));
        } catch (Exception e) {
            //            e.printStackTrace();
        }
        if (rowsCount > 0) {
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(Result.SUCCESS);
            //responseDataMap.put("respMsg", "执行成功，已删除");
        } else {
            //responseDataMap.put("respMsg", "执行失败，请求的数据不存在");
        }
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  18、获取[工艺/单元]列表

    /**
     * @param strToken
     * @param request
     * @return [工艺/单元]列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getTechnologyList
     */
    @ResponseBody
    @RequestMapping(value = "/getTechnologyList", method = RequestMethod.GET)
    public Result getTechnologyList(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<Technology> techLst = TechnologyService.GetList();
        Integer strRespMsg = TechnologyService.GetTechnologyName(1);
        responseDataMap.put("dataList", techLst);
        responseDataMap.put("respMsg", strRespMsg);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  19、获取[工况]列表

    /**
     * @param strToken
     * @param request
     * @return [工况]列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getOperateList
     */
    @ResponseBody
    @RequestMapping(value = "/getOperateList", method = RequestMethod.GET)
    public Result getOperateList(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "technologyId", required = false) String technologyId,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();

        Operate operateReqMod = new Operate();
        operateReqMod.setTechnologyId(Integer.valueOf(technologyId));
        List<Operate> operLst = OperateService.GetList(operateReqMod);
        responseDataMap.put("dataList", operLst);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  20、获取[事故策略]列表

    /**
     * @param strToken
     * @param request
     * @return [事故策略]列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getTroubleList
     */
    @ResponseBody
    @RequestMapping(value = "/getTroubleList", method = RequestMethod.GET)
    public Result getTroubleList(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "technologyId", required = false) String technologyId,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<Trouble> techLst = TroubleService.GetList(Integer.valueOf(technologyId));
        responseDataMap.put("dataList", techLst);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  21、获取[运行时标]列表

    /**
     * @param strToken
     * @param request
     * @return [事故策略]列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getTimescaleList
     */
    @ResponseBody
    @RequestMapping(value = "/getTimescaleList", method = RequestMethod.GET)
    public Result getTimescaleList(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<TimeScalePOJO> techLst = TimescaleService.GetList();
        responseDataMap.put("dataList", techLst);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  22、获取[DCS风格]列表

    /**
     * @param strToken
     * @param request
     * @return [事故策略]列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getStyleList
     */
    @ResponseBody
    @RequestMapping(value = "/getStyleList", method = RequestMethod.GET)
    public Result getStyleList(
            @RequestParam(value = "token", required = false) String strToken,
            @RequestParam(value = "userId", required = false) String userId,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<Style> techLst = StyleService.GetList();
        responseDataMap.put("dataList", techLst);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }


    //统计图表接口

    /**
     * 统计图表
     *
     * @return 获取实时数据监控中的图表数据；对应接口文档《TS2020接口文档 ---- 仿真课堂模块》 getStatisticalChartDataList
     */
    @ResponseBody
    @RequestMapping(value = "/getStatisticalChartDataList", method = RequestMethod.GET)
    public Result getStatisticalChartDataList(
            @RequestParam(value = "token", required = false) String strToken,
            @ModelAttribute("reqMod") StatisticalPOJO reqMod,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();

        //        Date StartTime = new Date();


        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        //</editor-fold>
        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();

        StatisticalChartPOJO modMap;
        List<StatisticalChartDataPOJO> dstaLst;
        StatisticalChartDataPOJO mod;
        //<editor-fold desc="是否历史数据默认值">
        if (null == reqMod.getIsHistory()) {
            reqMod.setIsHistory(0);
        }
        //</editor-fold>
        //<editor-fold desc="日期默认值">
        if (null == reqMod.getStartTime() || reqMod.getStartTime() == "") {
            SimpleDateFormat dfStart = new SimpleDateFormat("yyyy-MM-01");
            SimpleDateFormat dfStop = new SimpleDateFormat("yyyy-MM-dd");
            reqMod.setStartTime(dfStop.format(new Date()));
            reqMod.setStopTime(dfStop.format(new Date()));
        }
        if (null == reqMod.getStopTime() || reqMod.getStopTime() == "") {
            reqMod.setStopTime(reqMod.getStartTime());
        }
        //</editor-fold>
        //<editor-fold desc="图表数据展示默认值">
        int currentExameId = 0;
        int currentStudyType = 0;
        if (null != reqMod && null != reqMod.getExameId() && reqMod.getExameId() > 0) {
            currentExameId = 0;
        } else {
            StatisticalChartDataPOJO defaultMod = StatisticalService.GetDefaultModel(reqMod);
            if (null != defaultMod) {
                reqMod.setExameId(Integer.valueOf(defaultMod.getxAxis()));
                reqMod.setStudyType(Integer.valueOf(defaultMod.getyAxis()));
                currentExameId = Integer.valueOf(defaultMod.getxAxis());
                currentStudyType = Integer.valueOf(defaultMod.getyAxis());
            } else {
                reqMod.setExameId(0);
                reqMod.setStudyType(1);
                currentExameId = 0;
                currentStudyType = 1;
            }
        }
        //</editor-fold>

        //<editor-fold desc="平均时长 平均成绩 总成绩">

        StatisticalChartAvgPOJO avgMod = StatisticalService.GetAvgModel(reqMod);

        //</editor-fold>

        //<editor-fold desc="成绩达标率">
        modMap = new StatisticalChartPOJO();
        modMap.setDescribe("");
        modMap.setNotes("成绩达标率");

        dstaLst = new ArrayList<>();
        try {
            List<StatisticalChartDataPOJO> dblist = StatisticalService.GetListWithDaBiaoLv(reqMod);
            if (dblist.size() > 0) {
                StatisticalChartDataPOJO moddb = dblist.get(0);

                mod = new StatisticalChartDataPOJO();
                mod.setxAxis(String.valueOf(Integer.valueOf(moddb.getyAxis()) - Integer.valueOf(moddb.getxAxis())));
                mod.setyAxis("已达标");
                dstaLst.add(mod);
                mod = new StatisticalChartDataPOJO();
                mod.setxAxis(moddb.getxAxis());
                mod.setyAxis("未达标");
                dstaLst.add(mod);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            responseDataMap.put("respMsg", e.getMessage());
        }
        modMap.setDataList(dstaLst);

        responseDataMap.put("upToStandard", modMap);
        //</editor-fold>
        //<editor-fold desc="报告提交率">
        modMap = new StatisticalChartPOJO();
        modMap.setDescribe("");
        modMap.setNotes("报告提交率");

        dstaLst = new ArrayList<>();
        try {
            List<StatisticalChartDataPOJO> dblist = StatisticalService.GetListWithBaoGao(reqMod);
            if (dblist.size() > 0) {
                StatisticalChartDataPOJO moddb = dblist.get(0);

                mod = new StatisticalChartDataPOJO();
                mod.setxAxis(String.valueOf(Integer.valueOf(moddb.getyAxis()) - Integer.valueOf(moddb.getxAxis())));
                mod.setyAxis("已提交");
                dstaLst.add(mod);
                mod = new StatisticalChartDataPOJO();
                mod.setxAxis(moddb.getxAxis());
                mod.setyAxis("未提交");
                dstaLst.add(mod);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            responseDataMap.put("respMsg", e.getMessage());
        }

        modMap.setDataList(dstaLst);

        responseDataMap.put("submit", modMap);
        //</editor-fold>

        //<editor-fold desc="学习时长分布">
        modMap = new StatisticalChartPOJO();
        modMap.setDescribe("平均时长：" + avgMod.getAvgDuration() + "min");
        modMap.setNotes("学习时长分布");

        dstaLst = StatisticalService.GetListWithShiChang(reqMod);

        modMap.setDataList(dstaLst);

        responseDataMap.put("learningTime", modMap);
        //</editor-fold>
        //<editor-fold desc="任务单/试卷成绩分布">
        modMap = new StatisticalChartPOJO();
        modMap.setDescribe("满分：" + avgMod.getSumScore() + "分；平均分：" + avgMod.getAvgScore() + "分");
        modMap.setNotes("任务单/试卷成绩分布");

        dstaLst = StatisticalService.GetListWithChengJi(reqMod);

        modMap.setDataList(dstaLst);

        responseDataMap.put("questionScore", modMap);
        //</editor-fold>
        //<editor-fold desc="各任务/试题平均成绩分布">
        modMap = new StatisticalChartPOJO();
        modMap.setDescribe("");
        modMap.setNotes("各任务/试题平均成绩分布");

        dstaLst = StatisticalService.GetListWithPingJun(reqMod);

        modMap.setDataList(dstaLst);

        responseDataMap.put("averageScore", modMap);
        //</editor-fold>
        //<editor-fold desc="附件参数赋值">
        responseDataMap.put("loginUserCount", "2");
        responseDataMap.put("onlineUserCount", "7");
        if (currentExameId > 0) {
            responseDataMap.put("currentExameId", currentExameId);
            responseDataMap.put("currentStudyType", currentStudyType);
        } else {
            responseDataMap.put("currentExameId", null);
            responseDataMap.put("currentStudyType", null);
        }
        r.setData(responseDataMap);
        //</editor-fold>
        //</editor-fold>


        //        Date StopTime = new Date();
        //        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        //        double millisecond = StopTime.getTime() - StartTime.getTime();
        //        System.out.println("getStatisticalChartDataList");
        //        System.out.println("StartTime：" + simpleDateFormat.format(StartTime));
        //        System.out.println("StopTime：" + simpleDateFormat.format(StopTime));
        //        System.out.println("millisecond：" + millisecond + "\n");
        return r;
    }

    //测试接口

    /**
     * 测试接口
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDataList", method = RequestMethod.POST)
    public Result getDataList(
            @RequestBody List<Exam> reqModList,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();

        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        //</editor-fold>

        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();

        Map<String, Object> productlistMap = new HashMap<>();
        Map<String, Object> teclistMap = new HashMap<>();
        Map<String, Object> tasklistMap = new HashMap<>();

        List<Product> proLst = new ArrayList<Product>();
        for (int i = 1; i < 3; i++) {
            Product mod = new Product();
            mod.setId(i);
            mod.setProductCode("产品编号" + toString().valueOf(i));
            mod.setProductName("产品名称" + toString().valueOf(i));
            mod.setProductName2("产品显示名称" + toString().valueOf(i));
            proLst.add(mod);
        }
        for (Product pmod : proLst) {
            List<String> opmod = new ArrayList<>();
            opmod.add("工况1");
            opmod.add("工况2");
            tasklistMap.put("任务单1", opmod);
            tasklistMap.put("任务单2", opmod);
            teclistMap.put("工艺单元", tasklistMap);
            productlistMap.put(pmod.getProductName(), teclistMap);
        }


        //<editor-fold desc="demo">
        List<Task> taskList = new ArrayList<>(); //数据库查出的list
        List<Technology> tList = new ArrayList<>(); //数据库查出的list
        Map<String, List<Task>> map = new HashMap<>();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            int id = task.getTechnologyId();
            if (map.containsKey(id)) {
                map.get(id).add(task);
            } else {
                List<Task> dataList = new ArrayList<>();
                dataList.add(task);
                //id为key,相同id的person的List为value
                map.put(id + "", dataList);
            }
        }
        for (Technology t : tList) {
            map.get(t.getId());
            //t.setTaskList();
        }
        //</editor-fold>
        responseDataMap.put("datalist", proLst);
        responseDataMap.put("productcount", "[产品]数量2");
        responseDataMap.put("technologycount", "[工艺/单元]数量7");
        responseDataMap.put("taskcount", "[任务单]数量7");
        Product pm = firstOrDefault(proLst, n -> n.getId().equals(1));
        List<Product> newLst = where(proLst, n -> n.getId() == 1);
        productlistMap.put("测试pm", pm);
        productlistMap.put("测试newLst", newLst);
        r.setData(productlistMap);
        //</editor-fold>

        int expression;
        expression = 1;
        switch (expression) {
            case 1:
                break;
            case 2:
                break;
        }


        Operate reqMod = new Operate();
        //只查询未删除的工况
        reqMod.setIsDeleted(0);
        //查询任务单ID是1的工况
        reqMod.setTaskId(1);
        List<Operate> operateList = OperateService.GetList(reqMod);
        return r;
    }

    //<editor-fold desc="关于List的公用方法">

    /**
     * 返回符合条件的第一条数据
     * 示例：
     * Student stu=null;
     * stu=ListHelper.firstOrDefault(students,n->n.getId().equals(1));
     */
    //example ListHelper.firstOrDefault(students, n -> n.getAge()==12)
    public static <T> T firstOrDefault(List<T> list, Predicate<? super T> predicate) {
        if (list != null) {
            Optional<T> first = list.stream().filter(predicate).findFirst();
            if (first.isPresent()) {
                return first.get();
            }
        }
        return null;
    }

    /**
     * 返回符合条件的集合
     * 示例：
     * List<Student> stus=new ArrayList<>();
     * stus=ListHelper.where(students,n->n.getId()>1);
     */
    //example ListHelper.where(students, n -> n.getAge() >12)
    public static <T> List<T> where(List<T> list, Predicate<? super T> predicate) {
        if (list != null) {
            return list.stream().filter(predicate).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 返回指定列的集合
     * 示例：
     * List<Integer> ids=new ArrayList<>();
     * ids=ListHelper.select(students,n->n.getId());
     */
    //example ListHelper.select(students, n -> n.getAge())
    public static <T, E> List<E> select(List<T> list, Function<? super T, E> express) {
        List<E> eList = new ArrayList<>();
        for (T t : list) {
            E e = express.apply(t);
            eList.add(e);
        }
        return eList;
    }

    //</editor-fold>

}
