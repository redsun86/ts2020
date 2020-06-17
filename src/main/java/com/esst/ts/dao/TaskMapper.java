package com.esst.ts.dao;

import com.esst.ts.model.Task;
import com.esst.ts.model.TechnologyTaskPOJO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);

    @Select("select t.id,t.task_code taskCode,t.task_name taskName,t.technology_id technologyId,t.shibiao shiBiao " +
            "from task t where t.id>0")
    List<TechnologyTaskPOJO> GetPojoAllList();

    @Select("select t.id,t.task_code taskCode,t.task_name taskName,t.technology_id technologyId,t.shibiao shiBiao " +
            "from task t where t.id>0 " +
            "and t.technology_id=#{technologyId}")
    List<TechnologyTaskPOJO> GetPojoList(@Param("technologyId") int technologyId);
}