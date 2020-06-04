package com.esst.ts.service.impl;

import com.esst.ts.dao.FZhKTMapper;
import com.esst.ts.model.courseTaskModel;
import com.esst.ts.service.FZhKTService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class FZhKTImpl implements FZhKTService {
    @Resource
    private FZhKTMapper FZhKTMapper;

    @Override
    public courseTaskModel getCourseTaskDemo(String courseID) {
        return FZhKTMapper.getCourseTaskDemo(courseID);
    }

    @Override
    public courseTaskModel getCourseTask(String courseID) {
        return FZhKTMapper.getCourseTask(courseID);
    }
}
