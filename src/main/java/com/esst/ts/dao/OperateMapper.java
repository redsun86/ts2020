package com.esst.ts.dao;

import com.esst.ts.model.Operate;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OperateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Operate record);

    int insertSelective(Operate record);

    Operate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Operate record);

    int updateByPrimaryKey(Operate record);

    @Select("select * from operate")
    @ResultMap("BaseResultMap")
    List<Operate> getOperateListAll();
}