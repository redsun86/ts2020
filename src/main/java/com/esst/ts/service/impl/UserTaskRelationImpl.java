package com.esst.ts.service.impl;

import com.esst.ts.model.UserTaskRelation;
import com.esst.ts.service.UserTaskRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建标识：梁建磊 2020/6/20 15:43
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserTaskRelationImpl implements UserTaskRelationService {

    @Resource
    com.esst.ts.dao.UserTaskRelationMapper UserTaskRelationMapper;

    @Override
    public int insert(UserTaskRelation mod) {
        return UserTaskRelationMapper.insertSelective(mod);
    }

    @Override
    public int insertTaskIds(String taskIds, String userId) {
        int retVal = 0;
        if (taskIds != null && taskIds != "") {
            taskIds = taskIds.replaceAll("\\，", "\\,");
            String[] taskIdArray = taskIds.split("\\,");
            final List idList = new ArrayList<>();
            for (String id : taskIdArray) {
                idList.add(id);
            }
            if (idList.size() > 0) {
                Map params = new HashMap();
                params.put("idList", idList);
                params.put("userId", userId);
                retVal = UserTaskRelationMapper.insertTaskIds(params);
            }
        }
        return retVal;
    }

    @Override
    public int deleteTaskIds(String taskIds, String userId) {
        int retVal = 0;
        if (taskIds != null && taskIds != "") {
            taskIds = taskIds.replaceAll("\\，", "\\,");
            String[] taskIdArray = taskIds.split("\\,");
            final List idList = new ArrayList<>();
            for (String id : taskIdArray) {
                idList.add(id);
            }
            if (idList.size() > 0) {
                Map params = new HashMap();
                params.put("idList", idList);
                params.put("userId", userId);
                retVal = UserTaskRelationMapper.deleteTaskIds(params);
            }
        }
        return retVal;
    }
}
