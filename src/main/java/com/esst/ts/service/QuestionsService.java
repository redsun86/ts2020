package com.esst.ts.service;

import com.esst.ts.model.Questions;
import com.esst.ts.model.QuestionsPOJO;

import java.util.List;

/**
 * 试题——业务逻辑层接口定义
 * <p>试题==》Questions</p>
 * <p>创建标识：梁建磊 2020/6/15 16:54</p>
 */
public interface QuestionsService {
    int insert(Questions mod);

    int update(Questions mod);

    int deleteWithId(int id);

    Questions getInsertModel(Questions mod);

    List<QuestionsPOJO> GetList(int is_deleted, int exameId);
}
