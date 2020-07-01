package com.esst.ts.service;

import com.esst.ts.model.Operate;
import com.esst.ts.model.TechnologyTaskOperatePOJO;

import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/16 16:30
 */
public interface OperateService {

    /**
     * 自定义方法——获取名称不为空且未删除的工况数据集合
     *
     * @return 返回数据集合
     */
    List<TechnologyTaskOperatePOJO> GetPojoAllList();

    /**
     * 自定义方法——根据指定条件获取数据集合
     *
     * @param technologyId 工艺id
     * @return 返回数据集合
     */
    List<TechnologyTaskOperatePOJO> GetPojoList(int technologyId);

    /**
     * 常规方法——根据指定条件获取数据集合
     *
     * @param mod 请求参数
     * @return 返回数据集合
     */
    List<Operate> GetList(Operate mod);
}
