package com.esst.ts.dao;

import com.esst.ts.model.Product;
import com.esst.ts.model.ProductCountPOJO;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    @Select("select productcount,technologycount,taskcount,operatecount " +
            "from (select count(distinct id) productcount from product) p " +
            "LEFT JOIN (select count(distinct technology_id) technologycount from pro_tech_relation) t ON 1=1 " +
            "LEFT JOIN (select count(distinct id) taskcount from task) t2 ON 1=1 " +
            "LEFT JOIN (select count(distinct id) operatecount from operate) o ON 1=1")
    ProductCountPOJO GetModel();

    @Select("select p.id,p.product_code,p.product_name,p.product_name2 from product p")
    @ResultMap("BaseResultMap")
    List<Product> GetList();
}