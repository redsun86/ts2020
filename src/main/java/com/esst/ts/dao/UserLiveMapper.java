package com.esst.ts.dao;

import com.esst.ts.model.UserLive;
import com.esst.ts.model.UserLiveWithBLOBs;

public interface UserLiveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLiveWithBLOBs record);

    int insertSelective(UserLiveWithBLOBs record);

    UserLiveWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLiveWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UserLiveWithBLOBs record);

    int updateByPrimaryKey(UserLive record);
}