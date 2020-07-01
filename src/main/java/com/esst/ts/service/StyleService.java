package com.esst.ts.service;

import com.esst.ts.model.Style;
import com.esst.ts.model.Trouble;

import java.util.List;

/**
 * [DCS风格]——业务逻辑层接口定义
 * <p>风格==》Style</p>
 * 创建标识：梁建磊 2020/6/15 18:35
 */
public interface StyleService {
    /**
     * 获取数据集合
     * @return [DCS风格]数据集合
     */
    List<Style> GetList();
}
