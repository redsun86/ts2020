package com.esst.ts.service.impl;

import com.esst.ts.model.UserTaskRelation;
import com.esst.ts.service.UserTaskRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 创建标识：梁建磊 2020/6/20 15:43
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserTaskRelationImpl implements UserTaskRelationService {

    @Resource
    com.esst.ts.dao.UserTaskRelationMapper UserTaskRelationMapper;

    @Override
    public int insert(UserTaskRelation mod) {
        return UserTaskRelationMapper.insertSelective(mod);
    }

    @Override
    public int deleteWithTaskIdUserId(UserTaskRelation mod) {
        return UserTaskRelationMapper.deleteWithTaskIdUserId(mod.getUserId(),mod.getTaskId());
    }

    @Override
    public int deleteWithUserId(int userId) {
        return UserTaskRelationMapper.deleteWithUserId(userId);
    }
}
