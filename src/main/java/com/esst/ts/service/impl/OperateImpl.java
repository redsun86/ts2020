package com.esst.ts.service.impl;

import com.esst.ts.model.Operate;
import com.esst.ts.model.TechnologyTaskOperatePOJO;
import com.esst.ts.service.OperateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/16 16:33
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OperateImpl implements OperateService {
    @Resource
    com.esst.ts.dao.OperateMapper OperateMapper;

    @Override
    public List<TechnologyTaskOperatePOJO> GetPojoAllList() {
        return OperateMapper.GetPojoAllList();
    }

    @Override
    public List<TechnologyTaskOperatePOJO> GetPojoList(int technologyId) {
        return OperateMapper.GetPojoList(technologyId);
    }

    @Override
    public List<Operate> GetList(Operate mod) {
        return OperateMapper.GetList(mod);
    }

}
