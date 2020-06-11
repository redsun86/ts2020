package com.esst.ts.service;

import com.esst.ts.model.User;

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

    int updateUserPwd(Integer userId,String password);

    int delete(Integer Id);

    List<User> getUserListByTeacherId(Integer userID);

    int insert(User record);

    User loginByTeacher(String userName, String passWord);

    User loginByStudent(String trueName, String num);
}
