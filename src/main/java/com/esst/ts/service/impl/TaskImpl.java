package com.esst.ts.service.impl;

import com.esst.ts.model.TechnologyTaskPOJO;
import com.esst.ts.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/16 16:33
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskImpl implements TaskService {
    @Resource
    com.esst.ts.dao.TaskMapper TaskMapper;

    @Override
    public List<TechnologyTaskPOJO> GetPojoAllList(int userId) {
        return TaskMapper.GetPojoAllList(userId);
    }
}
