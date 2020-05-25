package com.esst.ts.dao;

import com.esst.ts.model.examPaper;

public interface examPaperMapper {
    int deleteByPrimaryKey(Integer exampaperid);

    int insert(examPaper record);

    int insertSelective(examPaper record);

    examPaper selectByPrimaryKey(Integer exampaperid);

    int updateByPrimaryKeySelective(examPaper record);

    int updateByPrimaryKey(examPaper record);
}