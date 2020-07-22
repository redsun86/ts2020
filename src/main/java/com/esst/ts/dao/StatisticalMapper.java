package com.esst.ts.dao;

import com.esst.ts.model.*;

import java.util.List;

public interface StatisticalMapper {

    /**
     * 实时数据统计图表 历史 达标率
     */
    List<StatisticalChartDataPOJO> GetListWithDaBiaoLv0(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 实时 达标率
     */
    List<StatisticalChartDataPOJO> GetListWithDaBiaoLv1(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 历史 提交报告
     */
    List<StatisticalChartDataPOJO> GetListWithBaoGao0(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 实时 提交报告
     */
    List<StatisticalChartDataPOJO> GetListWithBaoGao1(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 历史 学习时长
     */
    List<StatisticalChartDataPOJO> GetListWithShiChang0(StatisticalPOJO mod);
    /**
     * 实时数据统计图表 实时 学习时长
     */
    List<StatisticalChartDataPOJO> GetListWithShiChang1(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 历史 成绩分布
     */
    List<StatisticalChartDataPOJO> GetListWithChengJi0(StatisticalPOJO mod);
    /**
     * 实时数据统计图表 实时 成绩分布
     */
    List<StatisticalChartDataPOJO> GetListWithChengJi1(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 历史 各任务平均成绩
     */
    List<StatisticalChartDataPOJO> GetListWithPingJun0(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 实时 各任务平均成绩
     */
    List<StatisticalChartDataPOJO> GetListWithPingJun1(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 历史 默认的操作人数最多的任务单/试卷
     */
    StatisticalChartDataPOJO GetDefaultModel0(StatisticalPOJO mod);
    /**
     * 实时数据统计图表 实时 默认的操作人数最多的任务单/试卷
     */
    StatisticalChartDataPOJO GetDefaultModel1(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 历史 平均时长 平均成绩 总成绩
     */
    StatisticalChartAvgPOJO GetAvgModel0(StatisticalPOJO mod);

    /**
     * 实时数据统计图表 实时 平均时长 平均成绩 总成绩
     */
    StatisticalChartAvgPOJO GetAvgModel1(StatisticalPOJO mod);
}