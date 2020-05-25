package com.esst.ts.dao;

import com.esst.ts.model.courseType;

public interface courseTypeMapper {
    int insert(courseType record);

    int insertSelective(courseType record);
}