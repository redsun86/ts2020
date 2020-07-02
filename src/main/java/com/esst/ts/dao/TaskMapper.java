package com.esst.ts.dao;

import com.esst.ts.model.Task;
import com.esst.ts.model.TechnologyTaskPOJO;

import java.util.List;
import java.util.Map;

public interface TaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);

    /**
     * 常规方法——根据指定条件获取数据集合
     *
     * @param mapData 请求参数
     * @return 返回数据集合
     */
    List<TechnologyTaskPOJO> GetListWithUserIdsAndStatus(Map mapData);
}