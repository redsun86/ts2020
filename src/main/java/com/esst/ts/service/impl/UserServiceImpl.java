package com.esst.ts.service.impl;

import com.esst.ts.dao.UserMapper;
import com.esst.ts.model.User;
import com.esst.ts.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public User getUserByNameAndPassword(String userName, String password) {
        return userMapper.getUser(userName, password);
    }

    @Override
    public int updatePassword(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int update(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
