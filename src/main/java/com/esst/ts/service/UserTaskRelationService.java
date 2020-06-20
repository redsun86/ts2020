package com.esst.ts.service;

import com.esst.ts.model.UserTaskRelation;

/**
 * 创建标识：梁建磊 2020/6/20 15:42
 */
public interface UserTaskRelationService {
    int insert(UserTaskRelation mod);

    int deleteWithTaskIdUserId(UserTaskRelation mod);

    int deleteWithUserId(int userId);
}
