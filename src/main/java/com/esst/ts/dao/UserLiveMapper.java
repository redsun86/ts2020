package com.esst.ts.dao;

import com.esst.ts.model.UserLive;
import com.esst.ts.model.UserLiveWithBLOBs;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserLiveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLiveWithBLOBs record);

    int insertSelective(UserLiveWithBLOBs record);

    UserLiveWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLiveWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UserLiveWithBLOBs record);

    int updateByPrimaryKey(UserLive record);

    @Select("select ud.* from (SELECT uld.user_id,max(uld.id) id FROM `user_live` uld GROUP BY uld.user_id) g \n" +
            "LEFT JOIN   `user_live` ud ON ud.id=g.id;\n ")
    @ResultMap("BaseResultMap")
    List<UserLive> geUserLiveDistinct();
}