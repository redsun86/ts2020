package com.esst.ts.service.impl;

import com.esst.ts.model.Style;
import com.esst.ts.service.StyleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 创建标识：梁建磊 2020/6/15 18:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StyleImpl implements StyleService {
    @Resource
    com.esst.ts.dao.StyleMapper StyleMapper;
    @Override
    public List<Style> GetList() {
            return StyleMapper.GetList();
    }
}
