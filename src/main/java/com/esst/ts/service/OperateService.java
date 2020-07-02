package com.esst.ts.service;

import com.esst.ts.model.Operate;

import java.util.List;

/**
 * 工况——业务逻辑层接口定义
 * <p>工况==》Operate</p>
 * <p>创建标识：梁建磊 2020/6/16 15:19</p>
 */
public interface OperateService {
    /**
     * 常规方法——根据指定条件获取数据集合
     *
     * @param mod 请求参数
     * @return 返回数据集合
     */
    List<Operate> GetList(Operate mod);
}
