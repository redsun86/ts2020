package com.esst.ts.service.impl;

import com.esst.ts.model.ExamUserRelation;
import com.esst.ts.service.ExamUserRelationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建标识：梁建磊 2020/6/16 13:32
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExamUserRelationImpl implements ExamUserRelationService {
    @Resource
    com.esst.ts.dao.ExamUserRelationMapper ExamUserRelationMapper;

    @Override
    public int insert(ExamUserRelation mod) {
        return ExamUserRelationMapper.insert(mod);
    }

    @Override
    public int insertUserIds(String userIds, String exameId) {
        int retVal = 0;
        if (userIds != null && userIds != "") {
            userIds = userIds.replaceAll("\\，", "\\,");
            String[] userIdArray = userIds.split("\\,");
            final List idList = new ArrayList<>();
            for (String uId : userIdArray) {
                idList.add(uId);
            }
            if (idList.size() > 0) {
                Map params = new HashMap();
                params.put("idList", idList);
                params.put("exameId", exameId);
                retVal = ExamUserRelationMapper.insertUserIds(params);
            }
        }
        return retVal;
    }

    @Override
    public int deleteUserIds(String userIds, String exameId) {
        int retVal = 0;
        if (userIds != null && userIds != "") {
            userIds = userIds.replaceAll("\\，", "\\,");
            String[] userIdArray = userIds.split("\\,");
            final List idList = new ArrayList<>();
            for (String uId : userIdArray) {
                idList.add(uId);
            }
            if (idList.size() > 0) {
                Map params = new HashMap();
                params.put("idList", idList);
                params.put("exameId", exameId);
                retVal = ExamUserRelationMapper.deleteUserIds(params);
            }
        }
        return retVal;
    }

    @Override
    public List<ExamUserRelation> GetList(int exameId) {
        return ExamUserRelationMapper.GetList(exameId);
    }
}
