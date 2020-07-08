package com.esst.ts.service;

import com.esst.ts.model.StatisticalChartDataPOJO;
import com.esst.ts.model.StatisticalPOJO;

import java.util.List;

/**
 * 图表统计——业务逻辑层接口定义
 * <p>试卷==》Exam</p>
 * <p> 创建标识：梁建磊 2020/6/15 13:54</p>
 */
public interface StatisticalService {
    /**
     * 成绩达标率
     *
     * @param studyType
     * @param exameId
     * @param userId
     * @param StartTime
     * @param StopTime
     * @return
     */
    List<StatisticalChartDataPOJO> GetListWithDaBiaoLv(StatisticalPOJO mod);

    /**
     * 是否提交报告
     *
     * @param studyType
     * @param exameId
     * @param userId
     * @param StartTime
     * @param StopTime
     * @return
     */
    List<StatisticalChartDataPOJO> GetListWithBaoGao(StatisticalPOJO mod);

    /**
     * 学习时长分布
     *
     * @param studyType
     * @param exameId
     * @param userId
     * @param StartTime
     * @param StopTime
     * @return
     */
    List<StatisticalChartDataPOJO> GetListWithShiChang(StatisticalPOJO mod);

    /**
     * 课题成绩分布
     *
     * @param studyType
     * @param exameId
     * @param userId
     * @param StartTime
     * @param StopTime
     * @return
     */
    List<StatisticalChartDataPOJO> GetListWithChengJi(StatisticalPOJO mod);

    /**
     * 各任务平均分数
     *
     * @param studyType
     * @param exameId
     * @param userId
     * @param StartTime
     * @param StopTime
     * @return
     */
    List<StatisticalChartDataPOJO> GetListWithPingJun(StatisticalPOJO mod);
}
