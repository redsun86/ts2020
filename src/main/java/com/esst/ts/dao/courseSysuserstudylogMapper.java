package com.esst.ts.dao;

import com.esst.ts.model.courseSysuserstudylog;

public interface courseSysuserstudylogMapper {
    int deleteByPrimaryKey(Integer sysuserstudylogid);

    int insert(courseSysuserstudylog record);

    int insertSelective(courseSysuserstudylog record);

    courseSysuserstudylog selectByPrimaryKey(Integer sysuserstudylogid);

    int updateByPrimaryKeySelective(courseSysuserstudylog record);

    int updateByPrimaryKey(courseSysuserstudylog record);
}