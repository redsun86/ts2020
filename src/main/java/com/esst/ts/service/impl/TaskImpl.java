package com.esst.ts.service.impl;

import com.esst.ts.model.Task;
import com.esst.ts.model.TechnologyTaskPOJO;
import com.esst.ts.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建标识：梁建磊 2020/6/16 16:33
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskImpl implements TaskService {
    @Resource
    com.esst.ts.dao.TaskMapper TaskMapper;

    @Override
    public Task selectByPrimaryKey(Integer id) {
        return TaskMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TechnologyTaskPOJO> GetList(String userIds,int status,int IsAdmin) {
        List<TechnologyTaskPOJO> retVal = new ArrayList<>();
        if (userIds != null && userIds != "") {
            userIds = userIds.replaceAll("\\，", "\\,");
            String[] userIdArray = userIds.split("\\,");
            final List idList = new ArrayList<>();
            for (String id : userIdArray) {
                idList.add(id);
            }
            if (idList.size() > 0) {
                Map params = new HashMap();
                params.put("idList", idList);
                params.put("status", status);
                if(IsAdmin==1) {
                    retVal = TaskMapper.GetListWithTeacherIdsAndStatus(params);
                }else{
                    retVal = TaskMapper.GetListWithStudentIdsAndStatus(params);
                }
            }
        }else{
            retVal = TaskMapper.GetList();
        }
        return retVal;
    }
}
