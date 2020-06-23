package com.esst.ts.service.impl;

import com.esst.ts.model.TimeScale;
import com.esst.ts.model.TimeScalePOJO;
import com.esst.ts.service.TimescaleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/23 15:41
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TimescaleImpl implements TimescaleService {
    @Resource
    com.esst.ts.dao.TimeScaleMapper TimeScaleMapper;
    @Override
    public List<TimeScalePOJO> GetList() {
        return TimeScaleMapper.GetList();
    }
}
