package com.esst.ts.dao;

import com.esst.ts.model.Trouble;

public interface TroubleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Trouble record);

    int insertSelective(Trouble record);

    Trouble selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Trouble record);

    int updateByPrimaryKeyWithBLOBs(Trouble record);

    int updateByPrimaryKey(Trouble record);
}