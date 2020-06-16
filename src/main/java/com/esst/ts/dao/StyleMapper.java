package com.esst.ts.dao;

import com.esst.ts.model.Style;
import com.esst.ts.model.Trouble;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StyleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Style record);

    int insertSelective(Style record);

    Style selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Style record);

    int updateByPrimaryKey(Style record);

    @Select("select s.id,s.style_code,s.style_name from style s where s.id>0")
    @ResultMap("BaseResultMap")
    List<Style> GetList();
}