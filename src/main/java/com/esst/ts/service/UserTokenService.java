package com.esst.ts.service;

import com.esst.ts.model.UserToken;

public interface UserTokenService {
    /**
     * 删除无效的token
     *
     * @param userId
     * @param loginType app 2 web 1
     * @return
     */
    int invalidToken(Integer userId, int loginType);

    /**
     * 创建token
     *
     * @param record
     * @return
     */
    int insert(UserToken record);

    /**
     * 更新
     *
     * @param record
     * @return
     */
    int update(UserToken record);

    /**
     * 更具用户ID 还有token验证当前用户是否存在
     *
     * @param userId
     * @param token
     * @param loginType app 2 web 1
            * @return
            */
    boolean checkToken(Integer userId, String token, int loginType);


    /**
     * 更具用户Id获取token
     *
     * @param userId
     * @param type   APP 2 web 1
     * @return
     */
    UserToken getUserTokenByUserId(Integer userId, int type);

}
