package com.esst.ts.service.impl;

import com.esst.ts.dao.TeacherStudentRelationMapper;
import com.esst.ts.dao.UserMapper;
import com.esst.ts.model.TeacherStudentRelation;
import com.esst.ts.model.User;
import com.esst.ts.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private TeacherStudentRelationMapper teacherStudentRelationMapper;

    @Override
    public User getUserById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public User loginByTeacher(String userName, String passWord) {
        return userMapper.loginByTeacher(userName, passWord);
    }

    @Override
    public User loginByStudent(String trueName, String num) {
        return userMapper.loginByStudent(trueName, num);
    }

    @Override
    public User getUserByNum(String num) {
        return userMapper.selectByNum(num);
    }

    @Override
    public int insert(TeacherStudentRelation record) {
        return teacherStudentRelationMapper.insert(record);
    }

    @Override
    public int updatePassword(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int update(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int updateUserPwd(Integer userId, String password) {
        return userMapper.updateUserPwd(userId, password);
    }

    @Override
    public int delete(Integer Id) {
        return userMapper.deleteUserById(Id);
    }

    @Override
    public List<User> getUserListByTeacherId(Integer userID) {
        return userMapper.getUserLst(userID);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

}
