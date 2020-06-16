package com.esst.ts.service;

import com.esst.ts.model.Exam;
import com.esst.ts.model.Questions;
import com.esst.ts.model.QuestionsPOJO;

import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/15 16:54
 */
public interface QuestionsService {
    int insert(Questions mod);
    int update(Questions mod);
    int deleteWithId(int id);
    List<QuestionsPOJO> GetList(int exameId);
}
