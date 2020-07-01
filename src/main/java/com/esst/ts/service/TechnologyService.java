package com.esst.ts.service;

import com.esst.ts.model.Technology;
import com.esst.ts.model.TechnologyPOJO;

import java.util.List;

/**
 * 工艺/单元——业务逻辑层接口定义
 * <p>工艺==》Technology</p>
 * <p>创建标识：梁建磊 2020/6/15 18:03</p>
 */
public interface TechnologyService {
    List<TechnologyPOJO> GetPojoAllList();

    List<TechnologyPOJO> GetPojoList(int productId);

    List<Technology> GetList();
    Integer GetTechnologyName(int technologyId);

}
