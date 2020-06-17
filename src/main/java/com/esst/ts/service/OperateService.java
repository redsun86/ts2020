package com.esst.ts.service;

import com.esst.ts.model.TechnologyTaskOperatePOJO;

import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/16 16:30
 */
public interface OperateService {
    List<TechnologyTaskOperatePOJO> GetPojoAllList();

    List<TechnologyTaskOperatePOJO> GetPojoList(int technologyId);
}
