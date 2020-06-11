package com.esst.ts.controller;

import com.esst.ts.constants.Constants;
import com.esst.ts.model.Result;
import com.esst.ts.model.User;
import com.esst.ts.model.UserToken;
import com.esst.ts.service.UserService;
import com.esst.ts.service.UserTokenService;
import com.esst.ts.utils.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 测试
 * SHY
 */

@Controller
@RequestMapping("/test")
public class TestController {
    private final Logger log = LoggerFactory.getLogger(TestController.class);

    @ResponseBody
    @RequestMapping("/fileTest")
    public Result fileTest(HttpServletRequest request) throws IOException {
        Result r = new Result();
        String num = UUID.randomUUID().toString();  //生成一个随机数用作用户名
        String pathName = Constants.UPLOAD_PIC_URL + "/" + num + ".txt";  //生成文件存储在服务器的完整路径 如 /upload/3355d8d7-51c2-488a-80f4-ea97b2e93a30.jpg
        String path = request.getSession().getServletContext().getRealPath("/") + pathName;  //服务器绝对路径用于生成文件
        FileUtils.makefile(path); //空白文件生成
        // 做写文件操作 。。。。

        String url = Constants.getIpAddress(request);  //获取服务器访问ip和端口 例如：http://127.0.0.1:8080/
        url += pathName;  //文件最终访问路径 如：http://127.0.0.1:8080/upload/3355d8d7-51c2-488a-80f4-ea97b2e93a30.jpg
        r.setCode(Result.ERROR);
        r.setMsg("请求成功");
        r.setData(url); //把文件访问路径返回前端
        return r;
    }
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
    }
}
