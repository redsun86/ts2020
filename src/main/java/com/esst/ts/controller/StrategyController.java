package com.esst.ts.controller;

import com.esst.ts.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 策略库；策略==》strategy
 * 策略库中包含：任务库、试卷库。暂时将任务库和试卷库的接口写在“策略controller”中，如果后期有需要可以单独创建相应的Controller
 * 创建标识：梁建磊 2020-06-11
 */
@Controller
@RequestMapping("/web/v1/strategy")
public class StrategyController {
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
        List<ExamPOJO> questLst = new ArrayList<ExamPOJO>();
        for (int i = 1; i < 3; i++) {
            ExamPOJO mod = new ExamPOJO();
            mod.setId(i);
            mod.setExamName("试卷名称" + toString().valueOf(i));
            mod.setCreateUser(i);
            mod.setStatus(i);
            mod.setUserCount(20);
            questLst.add(mod);
        }
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
            @ModelAttribute("reqMod") ExamPOJO reqMod,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("请求失败"));
        r.setCode(Result.ERROR);
        //</editor-fold>

        //<editor-fold desc="业务操作并赋值">
        Map<String, Object> responseDataMap = new HashMap<>();
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        responseDataMap.put("token", strToken);
        responseDataMap.put("respMsg", "执行成功");
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

        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        responseDataMap.put("respMsg", "执行成功，已删除");
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
        List<QuestionsPOJO> questLst = new ArrayList<QuestionsPOJO>();
        for (int i = 1; i < 3; i++) {
            QuestionsPOJO mod = new QuestionsPOJO();
            mod.setId(i);
            mod.setExameId(i);
            mod.setExameName("试卷名称" + toString().valueOf(i));
            mod.setOperateId(i);
            mod.setOperateName("[工艺/单元]名称" + toString().valueOf(i));
            mod.setTroubleId(i);
            mod.setTroubleName("事故策略" + toString().valueOf(i));
            mod.setStyleId(i);
            mod.setStyleName("DCS风格" + toString().valueOf(i));
            mod.setProportion(20);
            mod.setTimeLimit(20);
            mod.setTimeScale(100);
            questLst.add(mod);
        }
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
        List<TechnologyPOJO> techLst = new ArrayList<TechnologyPOJO>();
        for (int i = 1; i < 3; i++) {
            TechnologyPOJO mod = new TechnologyPOJO();
            mod.setId(i);
            mod.setProduct_id(i);
            mod.setStyle_id("");
            mod.setTechnology_code("");
            mod.setTechnology_en_name("");
            mod.setTechnology_zh_name("工艺/单元" + toString().valueOf(i));
            techLst.add(mod);
        }
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
            @RequestParam(value = "technology_id", required = false) String technologyId,
            @RequestParam(value = "token", required = true) String strToken,
            HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();
        List<Trouble> techLst = new ArrayList<Trouble>();
        for (int i = 1; i < 3; i++) {
            Trouble mod = new Trouble();
            mod.setId(i);
//            mod.setTroubleCode("");
            mod.setTroubleName("事故策略" + toString().valueOf(i));
            mod.setTechnology(i);
            techLst.add(mod);
        }
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
            mod.setTimescaleCode("运行时标编号" + toString().valueOf(i));
            mod.setTimescaleName("运行时标" + toString().valueOf(i));
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
        List<Style> techLst = new ArrayList<Style>();
        for (int i = 1; i < 3; i++) {
            Style mod = new Style();
            mod.setId(i);
            //  mod.setTroubleCode("");
            mod.setStyleCode("DCS风格编号" + toString().valueOf(i));
            mod.setStyleName("DCS风格" + toString().valueOf(i));
            techLst.add(mod);
        }
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
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        responseDataMap.put("token", strToken);
        responseDataMap.put("respMsg", "执行成功");
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
            @RequestParam(value = "id", required = true) String Id,
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

        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        responseDataMap.put("respMsg", "执行成功，已删除");
        r.setData(responseDataMap);
        //</editor-fold>
        return r;
    }
}
