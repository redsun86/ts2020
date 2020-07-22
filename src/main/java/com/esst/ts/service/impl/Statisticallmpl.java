package com.esst.ts.service.impl;

import com.esst.ts.model.*;
import com.esst.ts.service.StatisticalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/15 13:54
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class Statisticallmpl implements StatisticalService {

    @Resource
    com.esst.ts.dao.StatisticalMapper StatisticalMapper;
    @Resource
    com.esst.ts.dao.StatisticalBaseDataMapper StatisticalBaseDataMapper;


    @Override
    public List<baseDataResponse> GetBaseList(baseDataRequest mod) {
        return StatisticalBaseDataMapper.GetBaseList(mod);
    }

    @Override
    public List<StatisticalChartDataPOJO> GetListWithDaBiaoLv(StatisticalPOJO mod) {
        if (mod.getIsHistory() == 0)
            return StatisticalMapper.GetListWithDaBiaoLv1(mod);
        else
            return StatisticalMapper.GetListWithDaBiaoLv0(mod);

    }

    @Override
    public List<StatisticalChartDataPOJO> GetListWithBaoGao(StatisticalPOJO mod) {
        if (mod.getIsHistory() == 0)
            return StatisticalMapper.GetListWithBaoGao1(mod);
        else
            return StatisticalMapper.GetListWithBaoGao0(mod);

    }

    @Override
    public List<StatisticalChartDataPOJO> GetListWithShiChang(StatisticalPOJO mod) {
        if (mod.getIsHistory() == 0)
            return StatisticalMapper.GetListWithShiChang1(mod);
        else
            return StatisticalMapper.GetListWithShiChang0(mod);

    }

    @Override
    public List<StatisticalChartDataPOJO> GetListWithChengJi(StatisticalPOJO mod) {
        if (mod.getIsHistory() == 0)
            return StatisticalMapper.GetListWithChengJi1(mod);
        else
            return StatisticalMapper.GetListWithChengJi0(mod);

    }

    @Override
    public List<StatisticalChartDataPOJO> GetListWithPingJun(StatisticalPOJO mod) {
        if (mod.getIsHistory() == 0)
            return StatisticalMapper.GetListWithPingJun1(mod);
        else
            return StatisticalMapper.GetListWithPingJun0(mod);

    }

    @Override
    public StatisticalChartDataPOJO GetDefaultModel(StatisticalPOJO mod) {
        if (mod.getIsHistory() == 0) {
            return StatisticalMapper.GetDefaultModel1(mod);
        } else {
            return StatisticalMapper.GetDefaultModel0(mod);
        }
    }

    @Override
    public StatisticalChartAvgPOJO GetAvgModel(StatisticalPOJO mod) {
        if (mod.getIsHistory() == 0)
            return StatisticalMapper.GetAvgModel1(mod);
        else
            return StatisticalMapper.GetAvgModel0(mod);

    }
}
