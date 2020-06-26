package com.esst.ts.service;

import com.esst.ts.model.ExamUserRelation;

import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/16 13:28
 */
public interface ExamUserRelationService {
    int insert(ExamUserRelation mod);

    int insertUserIds(String userIds, String exameId);

    int deleteUserIds(String userIds, String exameId);

    List<ExamUserRelation> GetList(int exameId);
}
