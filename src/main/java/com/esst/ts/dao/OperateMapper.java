package com.esst.ts.dao;

import com.esst.ts.model.Operate;
import com.esst.ts.model.TechnologyTaskOperatePOJO;
import org.apache.ibatis.annotations.Param;
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
     * 自定义方法——获取名称不为空且未删除的工况数据集合
     *
     * @return 返回数据集合
     */
    @Select("select t.id,t.technology_id technologyId,t.operate_code operateCode,t.operate_name operateName" +
            ",ifnull(tor.task_id,0) taskId from operate t " +
            "LEFT JOIN task_oper_relation tor ON t.id=tor.operate_id where LENGTH(t.operate_name)>0 and is_deleted=0")
    List<TechnologyTaskOperatePOJO> GetPojoAllList();

    /**
     * 自定义方法——根据指定条件获取数据集合
     *
     * @param technologyId 工艺id
     * @return 返回数据集合
     */
    @Select("select t.id,t.technology_id technologyId,t.operate_code operateCode,t.operate_name operateName " +
            "from operate t where t.id>0 " +
            "and t.technology_id=#{technologyId}")
    List<TechnologyTaskOperatePOJO> GetPojoList(@Param("technologyId") int technologyId);

    /**
     * 常规方法——根据指定条件获取数据集合
     *
     * @param mod 请求参数
     * @return 返回数据集合
     */
    List<Operate> GetList(Operate mod);
}