package com.esst.ts.dao;

import com.esst.ts.model.*;

import java.util.List;

public interface StatisticalMapper {

    /**
     * 实时数据统计图表 任务单 达标率
     */
    List<StatisticalChartDataPOJO> GetListWithDaBiaoLv0(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 试卷 达标率
     */
    List<StatisticalChartDataPOJO> GetListWithDaBiaoLv1(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 任务单 提交报告
     */
    List<StatisticalChartDataPOJO> GetListWithBaoGao0(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 任务单 提交报告
     */
    List<StatisticalChartDataPOJO> GetListWithBaoGao1(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 任务单+试卷 学习时长
     */
    List<StatisticalChartDataPOJO> GetListWithShiChang0(StatisticalPOJO mod);
    /**
     * 实时数据统计图表 任务单+试卷 学习时长
     */
    List<StatisticalChartDataPOJO> GetListWithShiChang1(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 任务单+试卷 成绩分布
     */
    List<StatisticalChartDataPOJO> GetListWithChengJi(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 任务单 各任务平均成绩
     */
    List<StatisticalChartDataPOJO> GetListWithPingJun0(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 试卷 各任务平均成绩
     */
    List<StatisticalChartDataPOJO> GetListWithPingJun1(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 默认的操作人数最多的任务单/试卷
     */
    StatisticalChartDataPOJO GetDefaultModel(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 任务单 平均时长 平均成绩 总成绩
     */
    StatisticalChartAvgPOJO GetAvgModel0(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 试卷 平均时长 平均成绩 总成绩
     */
    StatisticalChartAvgPOJO GetAvgModel1(StatisticalPOJO mod);
}