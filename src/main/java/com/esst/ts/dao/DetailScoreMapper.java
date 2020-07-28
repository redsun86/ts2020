package com.esst.ts.dao;

import com.esst.ts.model.DetailScore;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

public interface DetailScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DetailScore record);

    int insertSelective(DetailScore record);

    DetailScore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DetailScore record);

    int updateByPrimaryKeyWithBLOBs(DetailScore record);

    int updateByPrimaryKey(DetailScore record);

    @Select("Select * FROM detail_score WHERE id in (SELECT MAX(id) from detail_score WHERE train_id=#{trainId})")
    @ResultMap("ResultMapWithBLOBs")
    DetailScore getDetailScoreByTrainID(@Param("trainId") String trainId);
}