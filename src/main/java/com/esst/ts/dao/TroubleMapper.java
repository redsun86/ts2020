package com.esst.ts.dao;

import com.esst.ts.model.QuestionsPOJO;
import com.esst.ts.model.Trouble;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TroubleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Trouble record);

    int insertSelective(Trouble record);

    Trouble selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Trouble record);

    int updateByPrimaryKeyWithBLOBs(Trouble record);

    int updateByPrimaryKey(Trouble record);

    @Select("select t.id,t.trouble_code,t.trouble_name,t.technology from trouble t where t.technology=#{technologyId}")
    @ResultMap("BaseResultMap")
    List<Trouble> GetList(@Param("technologyId") int technologyId);
}