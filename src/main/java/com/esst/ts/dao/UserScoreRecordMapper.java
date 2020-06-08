package com.esst.ts.dao;

import com.esst.ts.model.UserScoreRecord;

public interface UserScoreRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserScoreRecord record);

    int insertSelective(UserScoreRecord record);

    UserScoreRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserScoreRecord record);

    int updateByPrimaryKey(UserScoreRecord record);
}