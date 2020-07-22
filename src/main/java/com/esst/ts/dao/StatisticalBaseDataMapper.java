package com.esst.ts.dao;

import com.esst.ts.model.*;

import java.util.List;

public interface StatisticalBaseDataMapper {
    /**
     * 获取学员成绩记录的基础数据
     *
     * @param mod
     * @return
     */
    List<baseDataResponse> GetBaseList(baseDataRequest mod);
}