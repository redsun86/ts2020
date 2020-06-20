package com.esst.ts.service.impl;

import com.esst.ts.model.Questions;
import com.esst.ts.model.QuestionsPOJO;
import com.esst.ts.service.QuestionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/15 16:56
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class QuestionsImpl implements QuestionsService {
    @Resource
    com.esst.ts.dao.QuestionsMapper QuestionsMapper;

    @Override
    public int insert(Questions mod) {
        return QuestionsMapper.insert(mod);
    }

    @Override
    public int update(Questions mod) {
        return QuestionsMapper.updateByPrimaryKeySelective(mod);
    }

    @Override
    public int deleteWithId(int id) {
        return QuestionsMapper.deleteWithId(id);
    }

    @Override
    public Questions getInsertModel(Questions mod) {
        return QuestionsMapper.getInsertModel(mod);
    }

    @Override
    public List<QuestionsPOJO> GetList(int is_deleted,int exameId) {
        return QuestionsMapper.GetList(is_deleted,exameId);
    }
}
