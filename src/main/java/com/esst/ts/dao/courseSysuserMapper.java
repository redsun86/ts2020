package com.esst.ts.dao;

import com.esst.ts.model.courseSysuser;

public interface courseSysuserMapper {
    int deleteByPrimaryKey(Integer coursesysuserid);

    int insert(courseSysuser record);

    int insertSelective(courseSysuser record);

    courseSysuser selectByPrimaryKey(Integer coursesysuserid);

    int updateByPrimaryKeySelective(courseSysuser record);

    int updateByPrimaryKey(courseSysuser record);
}