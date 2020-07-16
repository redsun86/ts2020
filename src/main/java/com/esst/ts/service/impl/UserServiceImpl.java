package com.esst.ts.service.impl;

import com.esst.ts.dao.TeacherStudentRelationMapper;
import com.esst.ts.dao.UserLoginLogMapper;
import com.esst.ts.dao.UserMapper;
import com.esst.ts.model.TeacherStudentRelation;
import com.esst.ts.model.User;
import com.esst.ts.model.UserLoginLog;
import com.esst.ts.model.UserLoginLogPOJO;
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

    @Resource
    private UserLoginLogMapper userLoginLogMapper;

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
        return userMapper.loginByStudents(trueName, num);
    }

    @Override
    public User loginByStudent(String num) {
        return userMapper.loginByStudent(num);
    }

    @Override
    public User getUserByNum(String num,Integer userId) {
        return userMapper.selectByNum(num,userId);
    }

    @Override
    public User getCheckUserByNum(String num) {
        return userMapper.getCheckUserByNum(num);
    }

    @Override
    public User getUserLastRecord() {
        return userMapper.selectLastRecord();
    }

    @Override
    public int insert(TeacherStudentRelation record) {
        return teacherStudentRelationMapper.insert(record);
    }

    @Override
    public TeacherStudentRelation getUserByTeacherId(Integer userId) {
        return teacherStudentRelationMapper.selectByTeacherId(userId);
    }

    @Override
    public TeacherStudentRelation selectByUserAndTeacher(Integer userId,Integer teacherId) {
        return teacherStudentRelationMapper.selectByUserAndTeacher(userId,teacherId);
    }

    @Override
    public int updateByPrimaryKey(TeacherStudentRelation record) {
        return teacherStudentRelationMapper.updateByPrimaryKey(record);
    }

    @Override
    public int insert(UserLoginLog record) {
        return userLoginLogMapper.insert(record);
    }

    @Override
    public List<User> getUserByTrueName(String userTrueName) {
        return userMapper.getUserByTrueName(userTrueName);
    }

    @Override
    public List<UserLoginLogPOJO> getUserLogByUserId(Integer userId,String beginDate,String endDate) {
        return userLoginLogMapper.getUserLogByUserId(userId,beginDate,endDate);
    }


    @Override
    public int updatePassword(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int update(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int updateUserPwd(Integer userId, String password) {
        return userMapper.updateUserPwd(userId, password);
    }

    @Override
    public int delete(Integer studentId,Integer teacherId) {
        return userMapper.deleteUserById(studentId,teacherId);
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
