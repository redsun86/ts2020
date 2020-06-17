package com.esst.ts.dao;

import com.esst.ts.model.Operate;
import com.esst.ts.model.TechnologyTaskOperatePOJO;
import org.apache.ibatis.annotations.Param;
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

    @Select("select t.id,t.technology_id technologyId,t.operate_code operateCode,t.operate_name operateName" +
            ",ifnull(tor.task_id,0) taskId from operate t " +
            "LEFT JOIN task_oper_relation tor ON t.id=tor.operate_id where t.id>0")
    List<TechnologyTaskOperatePOJO> GetPojoAllList();

    @Select("select t.id,t.technology_id technologyId,t.operate_code operateCode,t.operate_name operateName " +
            "from operate t where t.id>0 " +
            "and t.technology_id=#{technologyId}")
    List<TechnologyTaskOperatePOJO> GetPojoList(@Param("technologyId") int technologyId);

}