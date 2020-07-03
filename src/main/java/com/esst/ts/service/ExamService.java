package com.esst.ts.service;

import com.esst.ts.model.Exam;
import com.esst.ts.model.ExamPOJO;

import java.util.List;

/**
 * 试卷——业务逻辑层接口定义
 * <p>试卷==》Exam</p>
 *<p> 创建标识：梁建磊 2020/6/15 13:54</p>
 */
public interface ExamService {

    int insert(Exam mod);

    int update(Exam mod);

    int deleteWithId(int id);

    int updateStatus(String ids, int status,int userId);

    Exam getInsertModel(Exam mod);

    List<ExamPOJO> GetList(Exam mod);
    List<ExamPOJO> GetListWithStudent(Exam mod);
}
