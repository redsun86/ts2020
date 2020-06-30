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

    @Select("select t.id,t.task_code taskCode,t.task_name taskName,t.technology_id technologyId,t.shibiao shiBiao" +
            ",(case when ut.task_id is null then 0 else 1 end) `status` from task t left join (select task_id from " +
            "user_task_relation where user_id=#{userId} group by task_id) ut on t.id=ut.task_id where t.id>0")
    List<TechnologyTaskPOJO> GetPojoAllList(@Param("userId") int userId);
}