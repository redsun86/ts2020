package com.esst.ts.service;

import com.esst.ts.model.TechnologyTaskPOJO;

import java.util.List;

/**
 * 任务单——业务逻辑层接口定义
 * <p>任务==》Task</p>
 * <p>创建标识：梁建磊 2020/6/16 15:19</p>
 */
public interface TaskService {
    List<TechnologyTaskPOJO> GetPojoAllList(int userId);
}
