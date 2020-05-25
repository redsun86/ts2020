package com.esst.ts.service.impl;

import com.esst.ts.constants.Constants;
import com.esst.ts.dao.UserTokenDao;
import com.esst.ts.model.UserToken;
import com.esst.ts.service.UserTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserTokenServiceImpl implements UserTokenService {

    @Resource
    private UserTokenDao userTokenDao;

    @Override
    public int invalidToken(Integer userId, int loginType) {
        return userTokenDao.invalidToken(userId, loginType);
    }

    @Override
    public int insert(UserToken record) {
        // TODO Auto-generated method stub
        return userTokenDao.insertSelective(record);
    }

    @Override
    public int update(UserToken record) {
        return userTokenDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public boolean checkToken(Integer userId, String token, int loginType) {
        // 验证用户token是否有效
        UserToken userToken = userTokenDao.getUserToken(userId, loginType);
        if (userToken != null && userToken.getToken().equals(token)) {
            Date date = new Date();
            long tokenUpdateTime = userToken.getUpdateTime().getTime();
            long outtime = date.getTime() - tokenUpdateTime;
            if(loginType==1){ //web
                if (outtime < Constants.WEB_OUT_TIME) {
                    userToken.setUpdateTime(date);
                    userTokenDao.updateByPrimaryKey(userToken);
                    return false;
                }
            }
            if(loginType==2){ //app
                if (outtime < Constants.APP_OUT_TIME) {
                    userToken.setUpdateTime(date);
                    userTokenDao.updateByPrimaryKey(userToken);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public UserToken getUserTokenByUserId(Integer userId, int loginType) {
        return userTokenDao.getUserToken(userId, loginType);
    }

}
