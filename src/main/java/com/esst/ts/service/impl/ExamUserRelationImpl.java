package com.esst.ts.service.impl;

import com.esst.ts.model.ExamUserRelation;
import com.esst.ts.service.ExamUserRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/16 13:32
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExamUserRelationImpl implements ExamUserRelationService {
    @Resource
    com.esst.ts.dao.ExamUserRelationMapper ExamUserRelationMapper;

    @Override
    public int insert(ExamUserRelation mod) {
        return ExamUserRelationMapper.insert(mod);
    }

    @Override
    public int deleteWithExameId(int exameId) {
        return ExamUserRelationMapper.deleteWithExameId(exameId);
    }

    @Override
    public List<ExamUserRelation> GetList(int exameId) {
        return ExamUserRelationMapper.GetList(exameId);
    }
}
