package com.esst.ts.service.impl;

import com.esst.ts.model.Task;
import com.esst.ts.service.FZhKTService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class FZhKTImpl implements FZhKTService {


    @Resource
    private com.esst.ts.dao.FZhKTMapper FZhKTMapper;
//    @Resource
//    private UserLiveMapper userliveScore;

    @Override
    public List<Task> getCourseTaskLst(int courseID) {
        return FZhKTMapper.getCourseTaskLst(courseID);
    }

    @Override
    public List<Task> getCourseTaskLstDemo(int courseID) {
        return FZhKTMapper.getCourseTaskLstDemo(courseID);
    }

//    @Override
//    public boolean updatescore(UserLive score) {
//        userliveScore.updatescore(score);
//        return false;
//    }
}
