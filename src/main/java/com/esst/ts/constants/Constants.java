package com.esst.ts.constants;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class Constants {

    public static String CUSTOMER;

    @Value("${customer}")
    public void setCUSTOMER(String CUSTOMER) {
        Constants.CUSTOMER = CUSTOMER;
    }

    //超时时间
    public static final Integer WEB_OUT_TIME = 30000000; // token超时时间

    public static final Integer APP_OUT_TIME = 30000000; // token超时时间

    public static final String REQUEST_FAIL = "请求失败";  //
    public static final String LOGIN_SUCCEED = "登录成功";  //用户登录成功信息
    public static final String REQUEST_SUCCEED = "请求成功";
    public static final String TOKEN_INVALID = "请求失败,token无效";  //token失效信息
    public static final int LOGIN_INVALID_CODE_ERRROR = -2;  //登录失败coed
    public static final int TOKEN_INVALID_CODE_ERRROR = -1;  //token失效代码
    public static final int OTHER_ERROR_CODE_ERRROR = -3;  //其他错误
    public static final int PERMISSION_CODE_ERROR = -4; //权限错误

    //系统配置关键字
    public static final String UPLOAD_PIC_URL = "/upload";



    public static AtomicBoolean watchDogLoginFlag =new AtomicBoolean();

    //测试服务器
    //public static final String SERVER_URL = "http://";

    public static String getIpAddress(HttpServletRequest request) {
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String url = "http://" + serverName // 服务器地址
                + ":" + serverPort;// 端口号
        String suffix = request.getRequestURI();
        return url;
    }

}
