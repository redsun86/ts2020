package com.esst.ts.service;

import com.esst.ts.model.Technology;
import com.esst.ts.model.TechnologyPOJO;

import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/15 18:03
 */
public interface TechnologyService {
    List<TechnologyPOJO> GetPojoAllList();

    List<TechnologyPOJO> GetPojoList(int productId);

    List<Technology> GetList();
}
