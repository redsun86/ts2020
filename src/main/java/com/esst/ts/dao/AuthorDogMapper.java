package com.esst.ts.dao;

import com.esst.ts.model.AuthorDog;

public interface AuthorDogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AuthorDog record);

    int insertSelective(AuthorDog record);

    AuthorDog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AuthorDog record);

    int updateByPrimaryKey(AuthorDog record);
}