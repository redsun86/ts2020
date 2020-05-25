package com.esst.ts.dao;

import com.esst.ts.model.paper;
import com.esst.ts.model.paperWithBLOBs;

public interface paperMapper {
    int deleteByPrimaryKey(Integer paperid);

    int insert(paperWithBLOBs record);

    int insertSelective(paperWithBLOBs record);

    paperWithBLOBs selectByPrimaryKey(Integer paperid);

    int updateByPrimaryKeySelective(paperWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(paperWithBLOBs record);

    int updateByPrimaryKey(paper record);
}