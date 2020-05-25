package com.esst.ts.service;

import java.util.List;
import java.util.Map;

import com.esst.ts.utils.PagedModelList;
import com.esst.ts.model.User;

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

    /**
     * 根据用户账号查询用户
     *
     * @param userName
     * @return
     */
    User getUserByName(String userName);

	int update(User user);
}
