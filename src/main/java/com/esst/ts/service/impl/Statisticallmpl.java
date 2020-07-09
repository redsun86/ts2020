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
    @Resource
    com.esst.ts.dao.StatisticalHistoryMapper StatisticalHistoryMapper;

    @Override
    public List<StatisticalChartDataPOJO> GetListWithDaBiaoLv(StatisticalPOJO mod) {
        if (mod.getIsHistory() == 0) {
            if (mod.getStudyType() == 1)
                return StatisticalMapper.GetListWithDaBiaoLv1(mod);
            else
                return StatisticalMapper.GetListWithDaBiaoLv2(mod);
        } else {
            if (mod.getStudyType() == 1)
                return StatisticalHistoryMapper.GetListWithDaBiaoLv1(mod);
            else
                return StatisticalHistoryMapper.GetListWithDaBiaoLv2(mod);
        }
    }

    @Override
    public List<StatisticalChartDataPOJO> GetListWithBaoGao(StatisticalPOJO mod) {
        if (mod.getIsHistory() == 0) {
            if (mod.getStudyType() == 1)
                return StatisticalMapper.GetListWithBaoGao1(mod);
            else
                return StatisticalMapper.GetListWithBaoGao2(mod);
        } else {
            if (mod.getStudyType() == 1)
                return StatisticalHistoryMapper.GetListWithBaoGao1(mod);
            else
                return StatisticalHistoryMapper.GetListWithBaoGao2(mod);
        }
    }

    @Override
    public List<StatisticalChartDataPOJO> GetListWithShiChang(StatisticalPOJO mod) {
        if (mod.getIsHistory() == 0) {
            return StatisticalMapper.GetListWithShiChang(mod);
        } else {
            return StatisticalHistoryMapper.GetListWithShiChang(mod);
        }
    }

    @Override
    public List<StatisticalChartDataPOJO> GetListWithChengJi(StatisticalPOJO mod) {
        if (mod.getIsHistory() == 0) {
            return StatisticalMapper.GetListWithChengJi(mod);
        } else {
            return StatisticalHistoryMapper.GetListWithChengJi(mod);
        }
    }

    @Override
    public List<StatisticalChartDataPOJO> GetListWithPingJun(StatisticalPOJO mod) {
        if (mod.getIsHistory() == 0) {
            if (mod.getStudyType() == 1)
                return StatisticalMapper.GetListWithPingJun1(mod);
            else
                return StatisticalMapper.GetListWithPingJun2(mod);
        } else {
            if (mod.getStudyType() == 1)
                return StatisticalHistoryMapper.GetListWithPingJun1(mod);
            else
                return StatisticalHistoryMapper.GetListWithPingJun2(mod);
        }
    }

    @Override
    public StatisticalChartDataPOJO GetDefaultModel(StatisticalPOJO mod) {
        if (mod.getIsHistory() == 0) {
            return StatisticalMapper.GetDefaultModel(mod);
        } else {
            return StatisticalHistoryMapper.GetDefaultModel(mod);
        }
    }
}
