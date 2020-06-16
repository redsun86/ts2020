package com.esst.ts.dao;

import com.esst.ts.model.UserLiveData;
import com.esst.ts.model.UserLiveDataWithBLOBs;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserLiveDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLiveDataWithBLOBs record);

    int insertSelective(UserLiveDataWithBLOBs record);

    UserLiveDataWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLiveDataWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UserLiveDataWithBLOBs record);

    int updateByPrimaryKey(UserLiveData record);
    @Select("select ud.* from (SELECT uld.user_id,max(uld.id) id FROM `user_live_data` uld GROUP BY uld.user_id) g \n" +
            "LEFT JOIN   `user_live_data` ud ON ud.id=g.id;\n ")
    @ResultMap("BaseResultMap")
    List<UserLiveData> getUserLiveDataDistinct();
}