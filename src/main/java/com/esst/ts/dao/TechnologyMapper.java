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

    @Select("select t.id,t.technology_code technologyCode,t.technology_zh_name technologyZhName" +
            ",t.technology_en_name technologyEnName,t.style_id styleId,ptr.product_id productId " +
            "from technology t LEFT JOIN pro_tech_relation ptr ON ptr.technology_id=t.id " +
            "where ptr.id>0")
    List<TechnologyPOJO> GetPojoAllList();

    @Select("select t.id,t.technology_code technologyCode,t.technology_zh_name technologyZhName" +
            ",t.technology_en_name technologyEnName,t.style_id styleId,ptr.product_id productId " +
            "from technology t LEFT JOIN pro_tech_relation ptr ON ptr.technology_id=t.id " +
            "where ptr.id>0 and ptr.product_id=#{productId}")
    List<TechnologyPOJO> GetPojoList(@Param("productId") int productId);

    @Select("select t.id,t.technology_code,t.technology_zh_name,t.technology_en_name,t.style_id " +
            "from technology t " +
            "where t.id>0")
    @ResultMap("BaseResultMap")
    List<Technology> GetList();


    @Select("select style_id from technology t where t.id=#{technologyId}")
    int GetTechnologyName(@Param("technologyId") int technologyId);

}