package com.esst.ts.dao;

import com.esst.ts.model.UserLiveData;
import com.esst.ts.model.UserLiveDataWithBLOBs;

public interface UserLiveDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLiveDataWithBLOBs record);

    int insertSelective(UserLiveDataWithBLOBs record);

    UserLiveDataWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLiveDataWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UserLiveDataWithBLOBs record);

    int updateByPrimaryKey(UserLiveData record);
}