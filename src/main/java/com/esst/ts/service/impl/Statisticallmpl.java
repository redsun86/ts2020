package com.esst.ts.service.impl;

import com.esst.ts.model.StatisticalChartDataPOJO;
import com.esst.ts.model.StatisticalPOJO;
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

    @Override
    public List<StatisticalChartDataPOJO> GetListWithDaBiaoLv(StatisticalPOJO mod) {
        if (mod.getStudyType() == 1)
            return StatisticalMapper.GetListWithDaBiaoLv1(mod);
        else
            return StatisticalMapper.GetListWithDaBiaoLv2(mod);

    }

    @Override
    public List<StatisticalChartDataPOJO> GetListWithBaoGao(StatisticalPOJO mod) {
        if (mod.getStudyType() == 1)
            return StatisticalMapper.GetListWithBaoGao1(mod);
        else
            return StatisticalMapper.GetListWithBaoGao2(mod);
    }

    @Override
    public List<StatisticalChartDataPOJO> GetListWithShiChang(StatisticalPOJO mod) {
        return StatisticalMapper.GetListWithShiChang(mod);
    }

    @Override
    public List<StatisticalChartDataPOJO> GetListWithChengJi(StatisticalPOJO mod) {
        return StatisticalMapper.GetListWithChengJi(mod);
    }

    @Override
    public List<StatisticalChartDataPOJO> GetListWithPingJun(StatisticalPOJO mod) {
        if (mod.getStudyType() == 1)
            return StatisticalMapper.GetListWithPingJun1(mod);
        else
            return StatisticalMapper.GetListWithPingJun2(mod);
    }
    @Override
    public StatisticalChartDataPOJO GetDefaultModel(StatisticalPOJO mod) {
        return StatisticalMapper.GetDefaultModel(mod);
    }
}
