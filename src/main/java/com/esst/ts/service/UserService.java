package com.esst.ts.service;

import com.esst.ts.model.User;
import com.esst.ts.model.TeacherStudentRelation;
import com.esst.ts.model.UserLoginLog;
import com.esst.ts.model.UserLoginLogPOJO;

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

    int delete(Integer studentId,Integer teacherId);

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

    User getCheckUserByNum(String num);

    /**
     * 获取刚创建的学员ID
     * @return
     */
    User getUserLastRecord();

    int insert(TeacherStudentRelation record);
    /**
     * 根据教师Id获取教师对应的学员信息
     * @param userId
     * @return
     */
    TeacherStudentRelation getUserByTeacherId(Integer userId);

    TeacherStudentRelation selectByUserAndTeacher(Integer userId,Integer teacherId);

    int updateByPrimaryKey(TeacherStudentRelation record);

    int insert(UserLoginLog record);

    /**
     * 根据学员姓名查询学员信息
     * @param userTrueName
     * @return
     */
    List<User> getUserByTrueName(String userTrueName);

    List<UserLoginLogPOJO> getUserLogByUserId(Integer userId,String BeginDate,String endDate);
}
