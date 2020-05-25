package com.esst.ts.service.impl;

import com.esst.ts.dao.UserDao;
import com.esst.ts.model.User;
import com.esst.ts.service.UserService;
import com.esst.ts.utils.PagedLimitUtils;
import com.esst.ts.utils.PagedModelList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    @Override
    public User getUserByNameAndPassword(String userName, String password) {
        return userDao.getUser(userName, password);
    }

    @Override
    public int updatePassword(User record) {
        return userDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public User getUserByName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    @Override
    public int update(User user) {
        return userDao.updateByPrimaryKeySelective(user);
    }
}
