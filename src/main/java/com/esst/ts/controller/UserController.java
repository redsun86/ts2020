package com.esst.ts.controller;

import com.esst.ts.constants.Constants;
import com.esst.ts.model.Result;
import com.esst.ts.model.User;
import com.esst.ts.service.UserService;
import com.esst.ts.service.UserTokenService;
import com.esst.ts.model.UserToken;
import com.esst.ts.utils.UniqueKeyGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户模块
 * SHY
 */

@Controller
@RequestMapping("/web/v1/user")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;
    @Resource
    private UserTokenService userTokenService;

    /**
     * 登录接口
     *
     * @param userName 用户名称
     * @param password 用户密码
     */
    @ResponseBody
    @RequestMapping("/userLogin")
    public Result userLogin(@RequestParam(value = "userName") String userName,
                            @RequestParam(value = "password") String password,
                            HttpServletRequest request) {

        RequestContext requestContext = new RequestContext(request);
        User user = userService.getUserByNameAndPassword(userName, password);
        Result r = new Result();
        if (user == null) {
            r.setMsg(requestContext.getMessage("zhmmcw"));
            r.setCode(Constants.OTHER_ERROR_CODE_ERRROR);
            return r;
        }
        if (user.getIsDel() == 1) {
            r.setMsg(requestContext.getMessage("gyhysc"));
            r.setCode(Constants.LOGIN_INVALID_CODE_ERRROR);
            return r;
        }
        if (user.getStatus() == 0) {
            r.setMsg(requestContext.getMessage("ndzhwjh"));
            r.setCode(Constants.LOGIN_INVALID_CODE_ERRROR);
            return r;
        }
        r.setMsg(requestContext.getMessage("qqcg"));
        r.setCode(0);

        // 新增tocken表数据
        String strToken = UniqueKeyGenerator.generateToken();
        UserToken userToken = userTokenService.getUserTokenByUserId(user.getId(), 1);
        if (userToken != null) {//如果当前用户有token更新一个新的token 没有则创建一个新的token
            userToken.setToken(strToken);
            userToken.setUpdateTime(new Date());
            userTokenService.update(userToken);
        } else {
            userToken = new UserToken();
            userToken.setUserId(user.getId());
            userToken.setToken(strToken);
            userToken.setLoginType(1);
            userToken.setUpdateTime(new Date());
            userTokenService.insert(userToken);
        }
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("token", strToken);
        userMap.put("userId", user.getId());
        r.setData(userMap);
        return r;
    }

    /**
     * 登出接口  修改Token 状态
     */
    @ResponseBody
    @RequestMapping("/logout")
    @ApiOperation(value = "登出接口", httpMethod = "POST", response = Result.class, notes = "登出接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "token", value = "用户token", required = true, dataType = "String"),
    })
    public Result logout(@RequestParam(value = "userId") int userId,
                         @RequestParam(value = "token") String token,
                         HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        userTokenService.invalidToken(userId, 1); // 登出成功删除用户的token
        r.setMsg(requestContext.getMessage("qqcg"));
        r.setCode(0);
        return r;
    }


}
