package com.esst.ts.service.impl;

import com.esst.ts.model.TeacherStudentRelation;
import com.esst.ts.service.TeacherStudentRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建标识：梁建磊 2020/7/2 11:25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeacherStudentRelationImpl implements TeacherStudentRelationService {
    @Resource
    com.esst.ts.dao.TeacherStudentRelationMapper TeacherStudentRelationMapper;

    @Override
    public List<TeacherStudentRelation> GetList(TeacherStudentRelation mod) {
        return TeacherStudentRelationMapper.GetList(mod);
    }
}
