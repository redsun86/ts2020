package com.esst.ts.service;

import com.esst.ts.model.QuestionsPOJO;
import com.esst.ts.model.Trouble;

import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/15 18:26
 */
public interface TroubleService {
    List<Trouble> GetList(int technologyId);
}
