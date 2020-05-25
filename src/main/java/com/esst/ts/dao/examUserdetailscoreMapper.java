package com.esst.ts.dao;

import com.esst.ts.model.examUserdetailscore;
import com.esst.ts.model.examUserdetailscoreWithBLOBs;

public interface examUserdetailscoreMapper {
    int deleteByPrimaryKey(Integer userdetailscoreid);

    int insert(examUserdetailscoreWithBLOBs record);

    int insertSelective(examUserdetailscoreWithBLOBs record);

    examUserdetailscoreWithBLOBs selectByPrimaryKey(Integer userdetailscoreid);

    int updateByPrimaryKeySelective(examUserdetailscoreWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(examUserdetailscoreWithBLOBs record);

    int updateByPrimaryKey(examUserdetailscore record);
}