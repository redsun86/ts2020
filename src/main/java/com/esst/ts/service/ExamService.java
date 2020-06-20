package com.esst.ts.service;

import com.esst.ts.model.Exam;
import com.esst.ts.model.ExamPOJO;

import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/15 13:54
 */
public interface ExamService {

    int insert(Exam mod);

    int update(Exam mod);

    int deleteWithId(int id);

    int updateStatus(int id, int status);

    Exam getInsertModel(Exam mod);

    List<ExamPOJO> GetList(Exam mod);
}
