package com.esst.ts.service;

import com.esst.ts.model.User;
import com.esst.ts.model.TeacherStudentRelation;

import java.util.List;

public interface UserService {

    User getUserById(Integer userId);

    /**
     * 更新密码
     *
     * @param record
     * @return
     */
    int updatePassword(User record);

    int update(User user);

    int updateUserPwd(Integer userId, String password);

    int delete(Integer Id,Integer userId);

    List<User> getUserListByTeacherId(Integer userID);

    int insert(User record);

    User loginByTeacher(String userName, String passWord);

    User loginByStudent(String trueName, String num);

    /**
     * 根据学号判断当前教师是否已经导入该学号
     * @param num
     * @param userId
     * @return
     */
    User getUserByNum(String num,Integer userId);

    /**
     * 获取刚创建的学员ID
     * @return
     */
    User getUserLastRecord();

    int insert(TeacherStudentRelation record);
}
