package com.esst.ts.dao;

import com.esst.ts.model.courseDocument;

public interface courseDocumentMapper {
    int deleteByPrimaryKey(Integer coursedocumentid);

    int insert(courseDocument record);

    int insertSelective(courseDocument record);

    courseDocument selectByPrimaryKey(Integer coursedocumentid);

    int updateByPrimaryKeySelective(courseDocument record);

    int updateByPrimaryKeyWithBLOBs(courseDocument record);

    int updateByPrimaryKey(courseDocument record);
}