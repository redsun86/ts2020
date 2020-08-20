package com.esst.ts.dao;

import com.esst.ts.model.Technology;
import com.esst.ts.model.TechnologyPOJO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TechnologyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Technology record);

    int insertSelective(Technology record);

    Technology selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Technology record);

    int updateByPrimaryKey(Technology record);

    List<TechnologyPOJO> GetPojoAllList(TechnologyPOJO mod);

    @Select("select t.id,t.technology_code,t.technology_zh_name,t.technology_en_name,t.style_id " +
            "from technology t " +
            "where t.id>0")
    @ResultMap("BaseResultMap")
    List<Technology> GetList();


    @Select("select style_id from technology t where t.id=#{technologyId}")
    int GetStyleId(@Param("technologyId") int technologyId);

}