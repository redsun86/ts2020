package com.esst.ts.service.impl;

import com.esst.ts.model.Technology;
import com.esst.ts.model.TechnologyPOJO;
import com.esst.ts.service.TechnologyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/15 18:05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TechnologyImpl implements TechnologyService {
    @Resource
    com.esst.ts.dao.TechnologyMapper TechnologyMapper;
    @Override
    public List<Technology> GetList() {
        return TechnologyMapper.GetList();
    }
}
