package com.esst.ts.service;

import com.esst.ts.model.User;

import java.util.List;

public interface UserService {

    User getUserById(Integer userId);

    /**
     * 根据用户账号密码查询用户
     *
     * @param userName
     * @param password
     * @return
     */
    User getUserByNameAndPassword(String userName, String password);

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
}
