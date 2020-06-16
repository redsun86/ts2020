package com.esst.ts.dao;

import com.esst.ts.model.Task;
import com.esst.ts.model.scoreModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FZhKTMapper {

    @Select("select * from task where binary technology_id=#{technology_id} ")
    @ResultMap("BaseResultMap")
    List<Task> getCourseTaskLstBytechId(@Param("technology_id") int technology_id);
    @Select("select * from task")
    @ResultMap("BaseResultMap")
    List<Task> gteTaskListAll();
    List<scoreModel> getscoreModelList(String user_id, String template_id);

}
