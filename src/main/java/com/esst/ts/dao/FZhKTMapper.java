package com.esst.ts.dao;

import com.esst.ts.model.courseTaskModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FZhKTMapper {
    List<courseTaskModel> getCourseTaskLstDemo(int courseID);

    @Select("select * from course_task where binary course_id=#{courseID} ")
    @ResultMap("BaseResultMap")
    List<courseTaskModel> getCourseTaskLst(@Param("courseID") int courseID);

}
