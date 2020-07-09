package com.esst.ts.dao;

import com.esst.ts.model.StatisticalChartDataPOJO;
import com.esst.ts.model.StatisticalPOJO;

import java.util.List;

public interface StatisticalMapper {
    List<StatisticalChartDataPOJO> GetListWithDaBiaoLv1(StatisticalPOJO mod);

    List<StatisticalChartDataPOJO> GetListWithDaBiaoLv2(StatisticalPOJO mod);

    List<StatisticalChartDataPOJO> GetListWithBaoGao1(StatisticalPOJO mod);

    List<StatisticalChartDataPOJO> GetListWithBaoGao2(StatisticalPOJO mod);

    List<StatisticalChartDataPOJO> GetListWithShiChang(StatisticalPOJO mod);

    List<StatisticalChartDataPOJO> GetListWithChengJi(StatisticalPOJO mod);

    List<StatisticalChartDataPOJO> GetListWithPingJun1(StatisticalPOJO mod);

    List<StatisticalChartDataPOJO> GetListWithPingJun2(StatisticalPOJO mod);

    StatisticalChartDataPOJO GetDefaultModel(StatisticalPOJO mod);
}