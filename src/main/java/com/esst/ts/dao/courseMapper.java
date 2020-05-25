package com.esst.ts.dao;

import com.esst.ts.model.course;
import com.esst.ts.model.courseWithBLOBs;

public interface courseMapper {
    int deleteByPrimaryKey(Integer courseid);

    int insert(courseWithBLOBs record);

    int insertSelective(courseWithBLOBs record);

    courseWithBLOBs selectByPrimaryKey(Integer courseid);

    int updateByPrimaryKeySelective(courseWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(courseWithBLOBs record);

    int updateByPrimaryKey(course record);
}