package com.esst.ts.service.impl;

import com.esst.ts.dao.FZhKTMapper;
import com.esst.ts.model.courseTaskModel;
import com.esst.ts.service.FZhKTService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FZhKTImpl implements FZhKTService {
    @Resource
    private FZhKTMapper FZhKTMapper;

    @Override
    public List<courseTaskModel> getCourseTaskLstDemo(int courseID) {
        return FZhKTMapper.getCourseTaskLstDemo(courseID);
    }

    @Override
    public List<courseTaskModel> getCourseTaskLst(int courseID) {
        return FZhKTMapper.getCourseTaskLst(courseID);
    }
}
