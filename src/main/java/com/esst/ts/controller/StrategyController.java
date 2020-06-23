package com.esst.ts.controller;

import com.esst.ts.model.*;
import com.esst.ts.service.ExamService;
import com.esst.ts.service.TimescaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 策略库；策略==》strategy
 * 策略库中包含：任务库、试卷库。暂时将任务库和试卷库的接口写在“策略controller”中，如果后期有需要可以单独创建相应的Controller
 * 创建标识：梁建磊 2020-06-11
 */
@Controller
@RequestMapping("/web/v1/strategy")
public class StrategyController {
    //<editor-fold desc="Service对象初始化">
    @Resource
    private com.esst.ts.service.ProductService ProductService;
    @Resource
    private com.esst.ts.service.TaskService TaskService;
    @Resource
    private com.esst.ts.service.OperateService OperateService;
    @Resource
    private ExamService ExamService;
    @Resource
    private com.esst.ts.service.QuestionsService QuestionsService;
    @Resource
    private com.esst.ts.service.TechnologyService TechnologyService;
    @Resource
    private com.esst.ts.service.TroubleService TroubleService;
    @Resource
    private com.esst.ts.service.StyleService StyleService;
    @Resource
    private com.esst.ts.service.ExamUserRelationService ExamUserRelationService;
    @Resource
    private com.esst.ts.service.UserTaskRelationService UserTaskRelationService;
    @Resource
    private com.esst.ts.service.TimescaleService TimescaleService;

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
        List<TechnologyTaskOperatePOJO> operPojoLst; //工况

        //<editor-fold desc="从数据库中读取 工艺/单元、任务单、工况">
        if (productId != null && productId != "" && productId != "0") {
            techPojoLst = TechnologyService.GetPojoList(Integer.valueOf(productId));
        } else {
            techPojoLst = TechnologyService.GetPojoAllList();
        }
        taskPojolst = TaskService.GetPojoAllList();
        operPojoLst = OperateService.GetPojoAllList();
        //</editor-fold>

        //<editor-fold desc="任务单、工况 合并">
        Map<String, List<TechnologyTaskOperatePOJO>> mapOperPojoLst = new HashMap<>();
        for (int i = 0; i < operPojoLst.size(); i++) {
            TechnologyTaskOperatePOJO mod = operPojoLst.get(i);
            int pid = mod.getTaskId();
            if (mapOperPojoLst.containsKey(toString().valueOf(pid))) {
                mapOperPojoLst.get(toString().valueOf(pid)).add(mod);
            } else {
                List<TechnologyTaskOperatePOJO> dlist = new ArrayList<>();
                dlist.add(mod);
                mapOperPojoLst.put(toString().valueOf(pid), dlist);
            }
        }
        for (TechnologyTaskPOJO taskPojo : taskPojolst) {
            taskPojo.setOperateList(mapOperPojoLst.get(toString().valueOf(taskPojo.getId())));
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
            if (taskIds != null && taskIds != "") {
                taskIds = taskIds.replaceAll("\\，", "\\,");
                String[] taskIdlst = taskIds.split("\\,");
                for (String tId : taskIdlst) {
                    UserTaskRelation reqMode = new UserTaskRelation();
                    reqMode.setUserId(Integer.valueOf(userId));
                    reqMode.setTaskId(Integer.valueOf(tId));
                    UserTaskRelationService.deleteWithTaskIdUserId(reqMode);
                    UserTaskRelationService.insert(reqMode);
                }
            }
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
            if (taskIds != null && taskIds != "") {
                taskIds = taskIds.replaceAll("\\，", "\\,");
                String[] taskIdlst = taskIds.split("\\,");
                for (String tId : taskIdlst) {
                    UserTaskRelation reqMode = new UserTaskRelation();
                    reqMode.setUserId(Integer.valueOf(userId));
                    reqMode.setTaskId(Integer.valueOf(tId));
                    UserTaskRelationService.deleteWithTaskIdUserId(reqMode);
                }
            }
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
        taskPojolst = TaskService.GetPojoAllList();
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
        List<TechnologyTaskPOJO> taskPojolst; //任务单
        List<TechnologyTaskOperatePOJO> operPojoLst; //工况

        //<editor-fold desc="从数据库中读取 工艺/单元、任务单、工况">
        techPojoLst = TechnologyService.GetPojoAllList();
        taskPojolst = TaskService.GetPojoAllList();
        operPojoLst = OperateService.GetPojoAllList();
        //</editor-fold>

        //<editor-fold desc="工艺/工况、工况 合并">
        Map<String, List<TechnologyTaskOperatePOJO>> mapOperPojoLst = new HashMap<>();
        for (int i = 0; i < operPojoLst.size(); i++) {
            TechnologyTaskOperatePOJO mod = operPojoLst.get(i);
            int pid = mod.getTechnologyId();
            if (mapOperPojoLst.containsKey(toString().valueOf(pid))) {
                mapOperPojoLst.get(toString().valueOf(pid)).add(mod);
            } else {
                List<TechnologyTaskOperatePOJO> dlist = new ArrayList<>();
                dlist.add(mod);
                mapOperPojoLst.put(toString().valueOf(pid), dlist);
            }
        }
        for (TechnologyPOJO techPojo : techPojoLst) {
            techPojo.setOperateList(mapOperPojoLst.get(toString().valueOf(techPojo.getId())));
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
            reqMod.setIsDeleted(0);
            reqMod.setExamName(examName);
            List<ExamPOJO> questLst = ExamService.GetList(reqMod);
            responseDataMap.put("dataList", questLst);
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
            if (exameIds != null && exameIds != "") {
                exameIds = exameIds.replaceAll("\\，", "\\,");
                String[] exameIdlst = exameIds.split("\\,");
                for (String eId : exameIdlst) {
                    ExamService.updateStatus(Integer.valueOf(eId), 1);
                }
            }
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
            if (exameIds != null && exameIds != "") {
                exameIds = exameIds.replaceAll("\\，", "\\,");
                String[] exameIdlst = exameIds.split("\\,");
                for (String eId : exameIdlst) {
                    ExamService.updateStatus(Integer.valueOf(eId), 2);
                }
            }
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
            rowsCount = ExamUserRelationService.deleteWithExameId(Integer.valueOf(exameId));
            if (userIds != null && userIds != "") {
                userIds = userIds.replaceAll("\\，", "\\,");
                String[] userIdlst = userIds.split("\\,");
                for (String uId : userIdlst) {
                    ExamUserRelation reqMode = new ExamUserRelation();
                    reqMode.setExamId(Integer.valueOf(exameId));
                    reqMode.setUserId(Integer.valueOf(uId));
                    ExamUserRelationService.insert(reqMode);
                }
                //responseDataMap.put("userIdlst", userIdlst);
            }
        } catch (Exception e) {
            //            e.printStackTrace();
            responseDataMap.put("respMsg", e.getMessage());
        }
        if (rowsCount > 0) {
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(Result.SUCCESS);
            //responseDataMap.put("token", strToken);
            //responseDataMap.put("respMsg", "执行成功");
        }
        //responseDataMap.put("requestModel", userIds);
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
        List<TechnologyTaskOperatePOJO> operLst = OperateService.GetPojoList(Integer.valueOf(technologyId));
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
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "exameId", required = false) String exameId,
            @RequestParam(value = "studyType", required = false) String studyType,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();

        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        //</editor-fold>
        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();

        StatisticalChartPOJO modMap;
        List<StatisticalChartDataPOJO> dstaLst;
        StatisticalChartDataPOJO mod;

        //<editor-fold desc="成绩达标率">
        modMap = new StatisticalChartPOJO();
        modMap.setDescribe("");
        modMap.setNotes("成绩达标率");

        dstaLst = new ArrayList<>();
        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("60");
        mod.setyAxis("");
        ;
        dstaLst.add(mod);

        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("40");
        mod.setyAxis("");
        ;
        dstaLst.add(mod);

        modMap.setDataList(dstaLst);

        responseDataMap.put("upToStandard", modMap);
        //</editor-fold>
        //<editor-fold desc="报告提交率">
        modMap = new StatisticalChartPOJO();
        modMap.setDescribe("");
        modMap.setNotes("报告提交率");

        dstaLst = new ArrayList<>();
        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("80");
        mod.setyAxis("");
        ;
        dstaLst.add(mod);

        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("20");
        mod.setyAxis("");
        ;
        dstaLst.add(mod);

        modMap.setDataList(dstaLst);

        responseDataMap.put("submit", modMap);
        //</editor-fold>
        //<editor-fold desc="学习时长分布">
        modMap = new StatisticalChartPOJO();
        modMap.setDescribe("建议时长：90min；平均时长：85min");
        modMap.setNotes("学习时长分布");

        dstaLst = new ArrayList<>();
        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("课程1");
        mod.setyAxis("70");
        dstaLst.add(mod);

        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("课程2");
        mod.setyAxis("50");
        dstaLst.add(mod);

        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("课程3");
        mod.setyAxis("40");
        dstaLst.add(mod);

        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("课程4");
        mod.setyAxis("80");
        dstaLst.add(mod);

        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("课程5");
        mod.setyAxis("70");
        dstaLst.add(mod);

        modMap.setDataList(dstaLst);

        responseDataMap.put("learningTime", modMap);
        //</editor-fold>
        //<editor-fold desc="课题成绩分布">
        modMap = new StatisticalChartPOJO();
        modMap.setDescribe("满分：600分；平均分：80分");
        modMap.setNotes("课题成绩分布");

        dstaLst = new ArrayList<>();
        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("1-100");
        mod.setyAxis("70");
        dstaLst.add(mod);

        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("100-200");
        mod.setyAxis("50");
        dstaLst.add(mod);

        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("200-300");
        mod.setyAxis("40");
        dstaLst.add(mod);

        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("300-400");
        mod.setyAxis("80");
        dstaLst.add(mod);

        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("400以上");
        mod.setyAxis("70");
        dstaLst.add(mod);
        modMap.setDataList(dstaLst);

        responseDataMap.put("questionScore", modMap);
        //</editor-fold>
        //<editor-fold desc="各任务平均成绩">
        modMap = new StatisticalChartPOJO();
        modMap.setDescribe("");
        modMap.setNotes("各任务平均成绩");

        dstaLst = new ArrayList<>();
        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("进料");
        mod.setyAxis("70");
        dstaLst.add(mod);

        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("加热升温");
        mod.setyAxis("50");
        dstaLst.add(mod);

        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("建立回流");
        mod.setyAxis("40");
        dstaLst.add(mod);

        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("精馏塔塔顶压力及灵敏度温度调节");
        mod.setyAxis("80");
        dstaLst.add(mod);

        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("出料调节");
        mod.setyAxis("70");
        dstaLst.add(mod);

        mod = new StatisticalChartDataPOJO();
        mod.setxAxis("回流调节至正常");
        mod.setyAxis("70");
        dstaLst.add(mod);

        modMap.setDataList(dstaLst);

        responseDataMap.put("averageScore", modMap);
        //</editor-fold>

        responseDataMap.put("loginUserCount", "2");
        responseDataMap.put("onlineUserCount", "7");
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //测试接口

    /**
     * 测试接口
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getDataList", method = RequestMethod.GET)
    public Result getDataList(
            @RequestParam(value = "token", required = true) String strToken,
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
