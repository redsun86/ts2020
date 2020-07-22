package com.esst.ts.controller;

import com.esst.ts.model.Result;
import com.esst.ts.model.baseDataRequest;
import com.esst.ts.model.baseDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 创建标识：梁建磊 2020/7/20 16:43
 */
@Controller
@RequestMapping("/web/v1/statistical")
public class StatisticalController {

    /**
     * 图表统计——业务逻辑层接口服务
     */
    @Resource
    private com.esst.ts.service.StatisticalService StatisticalService;

    private final Logger log = LoggerFactory.getLogger(UserController.class);


    //统计图表接口

    /**
     * 统计图表
     *
     * @return getChartDataList
     */
    @ResponseBody
    @RequestMapping(value = "/getChartDataList", method = RequestMethod.GET)
    public Result getChartDataList(
            @ModelAttribute("reqMod") baseDataRequest reqMod,
            HttpServletRequest request) {
        //        计时开始
        Date StartTime = new Date();

        //        初始化
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(Result.SUCCESS);
        Map<String, Object> responseDataMap = new HashMap<>();

        //        取出基础数据
        List<baseDataResponse> baseDataList = StatisticalService.GetBaseList(reqMod);

        for (baseDataResponse bdmod : baseDataList) {
            if (null == bdmod.getTeacherId()) {
                bdmod.setTeacherId(1);
            }
        }
//        获取userId单列数据
        List<Integer> userIdList = baseDataList.stream().map(baseDataResponse -> baseDataResponse.getUserId()).collect(Collectors.toList());
//        去除重复值
        HashSet hsUserIdList = new HashSet(userIdList);
        userIdList.clear();
        userIdList.addAll(hsUserIdList);

        List<Float> studyDurationList = baseDataList.stream().map(baseDataResponse -> baseDataResponse.getPropScroe()).collect(Collectors.toList());
        DoubleSummaryStatistics stats = studyDurationList.stream().mapToDouble((x)-> x).summaryStatistics();
        double sum=stats.getSum();
        double avg= sum/userIdList.size();

        //        取出试卷的权重及试题总数
        //        取出任务的权重及任务总数

        //        赋值
        r.setData(responseDataMap);

        //        计时结束
        Date StopTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        double millisecond = StopTime.getTime() - StartTime.getTime();
        System.out.println("getChartDataList");
        System.out.println("StartTime：" + simpleDateFormat.format(StartTime));
        System.out.println("StopTime：" + simpleDateFormat.format(StopTime));
        System.out.println("millisecond：" + millisecond + "\n");
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
