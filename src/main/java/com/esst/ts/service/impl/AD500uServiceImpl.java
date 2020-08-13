package com.esst.ts.service.impl;

import com.esst.ts.model.*;
import com.esst.ts.service.AD500uService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 创建标识：梁建磊 2020/8/4 10:13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AD500uServiceImpl implements AD500uService {

    @Resource
    com.esst.ts.dao.AD500uMapper AD500uMapper;

    @Override
    public int truncatetable() {
        return AD500uMapper.truncatetable();
    }

    @Override
    public int insertProduct(Product mod) {
        return AD500uMapper.insertProduct(mod);
    }

    @Override
    public Product getInsertProductModel(Product mod) {
        return AD500uMapper.getInsertProductModel(mod);
    }

    @Override
    public int insertTechnology(Technology mod) {
        return AD500uMapper.insertTechnology(mod);
    }

    @Override
    public Technology getInsertTechnologyModel(Technology mod) {
        return AD500uMapper.getInsertTechnologyModel(mod);
    }

    @Override
    public int insertProTechRelation(ProTechRelation mod) {
        return AD500uMapper.insertProTechRelation(mod);
    }

    @Override
    public int insertTask(Task mod) {
        return AD500uMapper.insertTask(mod);
    }

    @Override
    public Task getInsertTaskModel(Task mod) {
        return AD500uMapper.getInsertTaskModel(mod);
    }

    @Override
    public int insertUserTaskRelation(UserTaskRelation mod) {
        return AD500uMapper.insertUserTaskRelation(mod);
    }

    @Override
    public int insertTaskOperRelation(TaskOperRelation mod) {
        return AD500uMapper.insertTaskOperRelation(mod);
    }

    @Override
    public int insertOperate(Operate mod) {
        return AD500uMapper.insertOperate(mod);
    }

    @Override
    public Operate getInsertOperateModel(Operate mod) {
        return AD500uMapper.getInsertOperateModel(mod);
    }

    @Override
    public int insertDefoultSetting() {
        return AD500uMapper.insertDefoultSetting();
    }
}
