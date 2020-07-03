package com.esst.ts.service.impl;

import com.esst.ts.model.Exam;
import com.esst.ts.model.ExamPOJO;
import com.esst.ts.service.ExamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建标识：梁建磊 2020/6/15 13:54
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class ExamImpl implements ExamService {

    @Resource
    com.esst.ts.dao.ExamMapper ExamMapper;

    @Override
    public int insert(Exam mod) {
        return ExamMapper.insert(mod);
    }

    @Override
    public int update(Exam mod) {
        return ExamMapper.updateByPrimaryKeySelective(mod);
    }

    @Override
    public int deleteWithId(int id) {
        return ExamMapper.deleteWithId(id);
    }

    @Override
    public List<ExamPOJO> GetList(Exam mod) {
        return ExamMapper.GetList(mod);
    }
    @Override
    public List<ExamPOJO> GetListWithStudent(Exam mod) {
        return ExamMapper.GetListWithStudent(mod);
    }

    @Override
    public int updateStatus(String ids, int status,int userId) {
        int retVal = 0;
        if (ids != null && ids != "") {
            ids = ids.replaceAll("\\，", "\\,");
            String[] idArray = ids.split("\\,");
            final List idList = new ArrayList<>();
            for (String eId : idArray) {
                idList.add(eId);
            }
            if (idList.size() > 0) {
                Map params = new HashMap();
                params.put("idList", idList);
                params.put("status", status);
                params.put("userId", userId);
                retVal = ExamMapper.updateStatus(params);
            }
        }
        return retVal;
    }

    @Override
    public Exam getInsertModel(Exam mod) {
        return ExamMapper.getInsertModel(mod);
    }
}
