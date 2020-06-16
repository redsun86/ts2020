package com.esst.ts.service.impl;

import com.esst.ts.model.Trouble;
import com.esst.ts.service.TroubleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/15 18:27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TroubleImpl implements TroubleService {
    @Resource
    com.esst.ts.dao.TroubleMapper TroubleMapper;
    @Override
    public List<Trouble> GetList(int technologyId) {
        return TroubleMapper.GetList(technologyId);
    }
}
