package com.esst.ts.service;

import com.esst.ts.model.TimeScale;
import com.esst.ts.model.TimeScalePOJO;
import com.esst.ts.model.Trouble;

import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/23 15:40
 */
public interface TimescaleService {
    List<TimeScalePOJO> GetList();
}
