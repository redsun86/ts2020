package com.esst.ts.service;

import com.esst.ts.model.UserTaskRelation;

/**
 * 教师任务关系——业务逻辑层接口定义
 * <p>关系==》Relation</p>
 * 创建标识：梁建磊 2020/6/20 15:42
 */
public interface UserTaskRelationService {
    int insert(UserTaskRelation mod);

    int insertTaskIds(String taskIds, int userId);

    int deleteTaskIds(String taskIds, int userId);
}
