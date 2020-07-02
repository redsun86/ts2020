package com.esst.ts.dao;

import com.esst.ts.model.Operate;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 工况数据访问层接口定义
 */
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

    /**
     * 常规方法——根据指定条件获取数据集合
     *
     * @param mod 请求参数
     * @return 返回数据集合
     */
    List<Operate> GetList(Operate mod);
}