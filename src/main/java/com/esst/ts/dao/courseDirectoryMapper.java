package com.esst.ts.dao;

import com.esst.ts.model.courseDirectory;

public interface courseDirectoryMapper {
    int deleteByPrimaryKey(Integer coursedirectoryid);

    int insert(courseDirectory record);

    int insertSelective(courseDirectory record);

    courseDirectory selectByPrimaryKey(Integer coursedirectoryid);

    int updateByPrimaryKeySelective(courseDirectory record);

    int updateByPrimaryKeyWithBLOBs(courseDirectory record);

    int updateByPrimaryKey(courseDirectory record);
}