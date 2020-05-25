package com.esst.ts.dao;

import com.esst.ts.model.examUsersimnetitemscore;

public interface examUsersimnetitemscoreMapper {
    int deleteByPrimaryKey(Integer usersimnetitemscoreid);

    int insert(examUsersimnetitemscore record);

    int insertSelective(examUsersimnetitemscore record);

    examUsersimnetitemscore selectByPrimaryKey(Integer usersimnetitemscoreid);

    int updateByPrimaryKeySelective(examUsersimnetitemscore record);

    int updateByPrimaryKey(examUsersimnetitemscore record);
}