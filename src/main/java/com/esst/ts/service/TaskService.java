package com.esst.ts.service;

import com.esst.ts.model.Task;
import com.esst.ts.model.TechnologyTaskPOJO;

import java.util.List;

/**
 * 任务单——业务逻辑层接口定义
 * <p>任务==》Task</p>
 * <p>创建标识：梁建磊 2020/6/16 15:19</p>
 */
public interface TaskService {

    Task selectByPrimaryKey(Integer id);
    /**
     * 常规方法——根据指定条件获取数据集合
     * @param userIds 非必填 教师id的集合；空值或0：忽略此条件；逗号分隔
     * @param status 必填 发布状态；0：忽略此条件，1：发布，2：未发布
     * @return 返回数据集合
     */
    List<TechnologyTaskPOJO> GetListWithUserIdsAndStatus(String userIds,int status);
}
