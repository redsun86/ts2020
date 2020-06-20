package com.esst.ts.service.impl;

import com.esst.ts.model.Exam;
import com.esst.ts.model.ExamPOJO;
import com.esst.ts.service.ExamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/15 13:54
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ExamImpl implements ExamService {

    @Resource
    com.esst.ts.dao.ExamMapper ExamMapper;

    @Override
    public int insert(Exam mod) {
        return ExamMapper.insert(mod);
    }

    @Override
    public int update(Exam mod) {
        return ExamMapper.updateByPrimaryKeySelective(mod);
    }

    @Override
    public int deleteWithId(int id) {
        return ExamMapper.deleteWithId(id);
    }

    @Override
    public List<ExamPOJO> GetList(Exam mod) {
        return ExamMapper.GetList(mod);
    }

    @Override
    public int updateStatus(int id, int status) {
        return ExamMapper.updateStatus(id, status);
    }

    @Override
    public Exam getInsertModel(Exam mod) {
        return ExamMapper.getInsertModel(mod);
    }
}
