package com.esst.ts.dao;

import com.esst.ts.model.TimeScale;
import com.esst.ts.model.TimeScalePOJO;
import com.esst.ts.model.Trouble;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/23 15:44
 */
public interface TimeScaleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TimeScale record);

    int insertSelective(TimeScale record);

    TimeScale selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TimeScale record);

    int updateByPrimaryKey(TimeScale record);

    @Select("select t.id,t.timescale_code,t.timescale_name from timescale t where t.id>0")
    @ResultMap("BaseResultMap")
    List<TimeScalePOJO> GetList();
}
