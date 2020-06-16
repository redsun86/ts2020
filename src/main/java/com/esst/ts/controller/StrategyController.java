package com.esst.ts.controller;

import com.esst.ts.model.*;
import com.esst.ts.service.ExamService;
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

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    //  1、策略库数据更新接口

    /**
     * @param strToken
     * @param request
     * @return 执行结果；对应接口文档《TS2020接口文档 ---- 策略库模块》 reloaddata
     */
    @ResponseBody
    @RequestMapping(value = "/reloaddata", method = RequestMethod.GET)
    public Result reloaddata(
            @RequestParam(value = "token", required = true) String strToken,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        responseDataMap.put("respMsg", "授权网站更新产品+工艺；ESOL网站更新工况；事故网站更新事故策略");
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  2、获取产品列表接口；从任务库数据列表中解析

    /**
     * @param strToken
     * @param request
     * @return 执行更新操作后返回产品列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getproductdata
     */
    @ResponseBody
    @RequestMapping(value = "/getproductdata", method = RequestMethod.GET)
    public Result getproductdata(
            @RequestParam(value = "token", required = true) String strToken,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<Product> proLst = new ArrayList<Product>();
        for (int i = 1; i < 3; i++) {
            Product mod = new Product();
            mod.setId(i);
            mod.setProductCode("产品编号" + toString().valueOf(i));
            mod.setProductName("产品名称" + toString().valueOf(i));
            mod.setProductName2("产品显示名称" + toString().valueOf(i));
            proLst.add(mod);
        }
        responseDataMap.put("datalist", proLst);
        responseDataMap.put("productcount", "[产品]数量2");
        responseDataMap.put("technologycount", "[工艺/单元]数量7");
        responseDataMap.put("taskcount", "[任务单]数量7");
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  3、获取任务库列表数据接口；需要与数据库交互

    /**
     * @param technologyName 工艺/单元 名称
     * @param strToken
     * @param request
     * @return 任务库列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 gettechnologyalllist
     */
    @ResponseBody
    @RequestMapping(value = "/gettechnologyalllist", method = RequestMethod.GET)
    public Result gettechnologyalllist(
            @RequestParam(value = "technology_name", required = false) String technologyName,
            @RequestParam(value = "token", required = true) String strToken,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<TechnologyPOJO> techLst = new ArrayList<TechnologyPOJO>();
        for (int i = 1; i < 3; i++) {
            TechnologyPOJO mod = new TechnologyPOJO();
            mod.setId(i);
            mod.setProduct_id(i);
            Product pmod = new Product();
            pmod.setId(i);
            pmod.setProductCode("");
            pmod.setProductName("产品名称");
            pmod.setProductName2("产品显示名称");
            mod.setProduct(pmod);
            mod.setStyle_id("");
            mod.setTechnology_code("");
            mod.setTechnology_en_name(technologyName);
            mod.setTechnology_zh_name("工艺/单元" + toString().valueOf(i));
            List<TechnologyTaskPOJO> tasklst = new ArrayList<TechnologyTaskPOJO>();
            for (int ti = 1; ti < 2; ti++) {
                TechnologyTaskPOJO tmod = new TechnologyTaskPOJO();
                tmod.setId(ti);
                tmod.setTask_code("");
                tmod.setTask_name("任务单" + toString().valueOf(ti));
                tmod.setTechnology_id(ti);
                List<TechnologyTaskOperatePOJO> operLst = new ArrayList<TechnologyTaskOperatePOJO>();
                for (int oi = 1; oi < 3; oi++) {
                    TechnologyTaskOperatePOJO omod = new TechnologyTaskOperatePOJO();
                    omod.setId(oi);
                    omod.setOperate_code("");
                    omod.setOperate_name("工况" + toString().valueOf(oi));
                    omod.setTechnology_id(oi);
                    operLst.add(omod);
                }
                tmod.setOperate_list(operLst);
                tasklst.add(tmod);
            }
            mod.setTask_list(tasklst);
            techLst.add(mod);
        }
        responseDataMap.put("datalist", techLst);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  4、获取已开启的任务单列表数据；同3,根据【是否开启】进行检索

    /**
     * @param strToken
     * @param request
     * @return 已开启的任务库列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getopentechnologyalllist
     */
    @ResponseBody
    @RequestMapping(value = "/getopentechnologyalllist", method = RequestMethod.GET)
    public Result getopentechnologyalllist(
            @RequestParam(value = "token", required = true) String strToken,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<TechnologyPOJO> techLst = new ArrayList<TechnologyPOJO>();
        for (int i = 1; i < 3; i++) {
            TechnologyPOJO mod = new TechnologyPOJO();
            mod.setId(i);
            mod.setProduct_id(i);
            Product pmod = new Product();
            pmod.setId(i);
            pmod.setProductCode("");
            pmod.setProductName("产品名称");
            pmod.setProductName2("产品显示名称");
            mod.setProduct(pmod);
            mod.setStyle_id("");
            mod.setTechnology_code("");
            mod.setTechnology_en_name("");
            mod.setTechnology_zh_name("工艺/单元" + toString().valueOf(i));
            List<TechnologyTaskPOJO> tasklst = new ArrayList<TechnologyTaskPOJO>();
            for (int ti = 1; ti < 2; ti++) {
                TechnologyTaskPOJO tmod = new TechnologyTaskPOJO();
                tmod.setId(ti);
                tmod.setTask_code("");
                tmod.setTask_name("任务单" + toString().valueOf(ti));
                tmod.setTechnology_id(ti);
                List<TechnologyTaskOperatePOJO> operLst = new ArrayList<TechnologyTaskOperatePOJO>();
                for (int oi = 1; oi < 3; oi++) {
                    TechnologyTaskOperatePOJO omod = new TechnologyTaskOperatePOJO();
                    omod.setId(oi);
                    omod.setOperate_code("");
                    omod.setOperate_name("工况" + toString().valueOf(oi));
                    omod.setTechnology_id(oi);
                    operLst.add(omod);
                }
                tmod.setOperate_list(operLst);
                tasklst.add(tmod);
            }
            mod.setTask_list(tasklst);
            techLst.add(mod);
        }
        responseDataMap.put("datalist", techLst);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  5、获取试卷库列表数据接口；需要与数据库交互

    /**
     * @param strToken
     * @param request
     * @return 试卷库列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getexamlst
     */
    @ResponseBody
    @RequestMapping(value = "/getexamlst", method = RequestMethod.GET)
    public Result getexamlst(
            @RequestParam(value = "token", required = true) String strToken,
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
        List<ExamPOJO> questLst = ExamService.GetList(1);
        responseDataMap.put("datalist", questLst);
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  6、新增/编辑试卷数据接口；需要与数据库交互

    /**
     * @param strToken
     * @param request
     * @return 执行结果；对应接口文档《TS2020接口文档 ---- 策略库模块》 editexam
     */
    @ResponseBody
    @RequestMapping(value = "/editexam", method = RequestMethod.POST)
    public Result editexam(
            @RequestParam(value = "token", required = true) String strToken,
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

        int rowsCount= 0;
        try {
            if(reqMod.getId()==null) {
                rowsCount = ExamService.insert(reqMod);
            }else{
                rowsCount = ExamService.update(reqMod);
            }
        } catch (Exception e) {
            //            e.printStackTrace();
            responseDataMap.put("respMsg",e.getMessage());
        }
        if(rowsCount>0) {
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(Result.SUCCESS);
            responseDataMap.put("token", strToken);
            responseDataMap.put("respMsg", "执行成功");
        }
        responseDataMap.put("requestModel", reqMod);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  7、删除试卷数据接口；需要与数据库交互

    /**
     * @param strToken
     * @param request
     * @return 执行结果；对应接口文档《TS2020接口文档 ---- 策略库模块》 deleteexam
     */
    @ResponseBody
    @RequestMapping(value = "/deleteexam", method = RequestMethod.GET)
    public Result deleteexam(
            @RequestParam(value = "id", required = false) Integer Id,
            @RequestParam(value = "token", required = true) String strToken,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("请求失败"));
        r.setCode(Result.ERROR);
        //</editor-fold>


        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();

        int rowsCount= 0;
        try {
            rowsCount = ExamService.deleteWithId(Id);
        } catch (Exception e) {
            //            e.printStackTrace();
        }
        if(rowsCount>0) {
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(Result.SUCCESS);
            responseDataMap.put("respMsg", "执行成功，已删除");
        }else{
            responseDataMap.put("respMsg", "执行失败，请求的数据不存在");
        }
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  8、获取试题列表数据接口；需要与数据库交互

    /**
     * @param strToken
     * @param request
     * @return 试题列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getquestionslist
     */
    @ResponseBody
    @RequestMapping(value = "/getquestionslist", method = RequestMethod.GET)
    public Result getquestionslist(
            @RequestParam(value = "token", required = true) String strToken,
            @RequestParam(value = "exame_id", required = false) Integer exameId,
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
        List<QuestionsPOJO> questLst = QuestionsService.GetList(exameId);
        responseDataMap.put("datalist", questLst);
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  9、获取[工艺/单元]列表

    /**
     * @param strToken
     * @param request
     * @return [工艺/单元]列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 gettechnologylist
     */
    @ResponseBody
    @RequestMapping(value = "/gettechnologylist", method = RequestMethod.GET)
    public Result gettechnologylist(
            @RequestParam(value = "token", required = true) String strToken,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<Technology> techLst =TechnologyService.GetList() ;
        responseDataMap.put("datalist", techLst);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  10、获取[事故策略]列表

    /**
     * @param strToken
     * @param request
     * @return [事故策略]列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 gettroublelist
     */
    @ResponseBody
    @RequestMapping(value = "/gettroublelist", method = RequestMethod.GET)
    public Result gettroublelist(
            @RequestParam(value = "technology_id", required = false) Integer technologyId,
            @RequestParam(value = "token", required = true) String strToken,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<Trouble> techLst = TroubleService.GetList(technologyId) ;
        responseDataMap.put("datalist", techLst);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  11、获取[运行时标]列表

    /**
     * @param strToken
     * @param request
     * @return [事故策略]列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 gettimescalelist
     */
    @ResponseBody
    @RequestMapping(value = "/gettimescalelist", method = RequestMethod.GET)
    public Result gettimescalelist(
            @RequestParam(value = "token", required = true) String strToken,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<TimeScalePOJO> techLst = new ArrayList<TimeScalePOJO>();
        for (int i = 1; i < 3; i++) {
            TimeScalePOJO mod = new TimeScalePOJO();
            mod.setId(i);
            mod.setTimescaleCode(toString().valueOf(i));
            mod.setTimescaleName(toString().valueOf(i*100));
            techLst.add(mod);
        }
        responseDataMap.put("datalist", techLst);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  12、获取[DCS风格]列表

    /**
     * @param strToken
     * @param request
     * @return [事故策略]列表数据；对应接口文档《TS2020接口文档 ---- 策略库模块》 getstylelist
     */
    @ResponseBody
    @RequestMapping(value = "/getstylelist", method = RequestMethod.GET)
    public Result getstylelist(
            @RequestParam(value = "token", required = true) String strToken,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<Style> techLst = StyleService.GetList() ;
        responseDataMap.put("datalist", techLst);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  13、新增/编辑试题数据接口；需要与数据库交互

    /**
     * @param strToken
     * @param request
     * @return 执行结果；对应接口文档《TS2020接口文档 ---- 策略库模块》 editquestions
     */
    @ResponseBody
    @RequestMapping(value = "/editquestions", method = RequestMethod.POST)
    public Result editquestions(
            @RequestParam(value = "token", required = true) String strToken,
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
        int rowsCount= 0;
        try {
            if(reqMod.getId()==null) {
                rowsCount = QuestionsService.insert(reqMod);
            }else{
                rowsCount = QuestionsService.update(reqMod);
            }
        } catch (Exception e) {
            //            e.printStackTrace();
            responseDataMap.put("respMsg",e.getMessage());
        }
        if(rowsCount>0) {
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(Result.SUCCESS);
            responseDataMap.put("token", strToken);
            responseDataMap.put("respMsg", "执行成功");
        }
        responseDataMap.put("requestModel", reqMod);
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }

    //  14、删除试题数据接口；需要与数据库交互

    /**
     * @param strToken
     * @param request
     * @return 执行结果；对应接口文档《TS2020接口文档 ---- 策略库模块》 deletequestions
     */
    @ResponseBody
    @RequestMapping(value = "/deletequestions", method = RequestMethod.GET)
    public Result deletequestions(
            @RequestParam(value = "id", required = true) Integer Id,
            @RequestParam(value = "token", required = true) String strToken,
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
        int rowsCount= 0;
        try {
            rowsCount = QuestionsService.deleteWithId(Id);
        } catch (Exception e) {
            //            e.printStackTrace();
        }
        if(rowsCount>0) {
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(Result.SUCCESS);
            responseDataMap.put("respMsg", "执行成功，已删除");
        }else{
            responseDataMap.put("respMsg", "执行失败，请求的数据不存在");
        }
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }


    /**
     * 测试接口
     *
     * @param strToken
     * @param request
     * @return 返回特殊格式的JSON数据
     */
    @ResponseBody
    @RequestMapping(value = "/getdatalist", method = RequestMethod.GET)
    public Result getdatalist(
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


}
