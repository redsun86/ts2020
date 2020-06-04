package com.esst.ts.dao;

import com.esst.ts.model.courseTaskModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface FZhKTMapper {
    courseTaskModel getCourseTaskDemo(String courseID);

    @Select("select * from course_task where binary course_id=#{courseID} ")
    @ResultMap("BaseResultMap")
    courseTaskModel getCourseTask(@Param("courseID") String courseID);

}
