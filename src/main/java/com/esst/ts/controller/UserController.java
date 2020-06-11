package com.esst.ts.controller;

import com.esst.ts.constants.Constants;
import com.esst.ts.model.Result;
import com.esst.ts.model.User;
import com.esst.ts.service.UserService;
import com.esst.ts.service.UserTokenService;
import com.esst.ts.model.UserToken;
import com.esst.ts.utils.*;
import io.swagger.annotations.*;
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
            r.setCode(Result.PASSWORD_ERROR);
            return r;
        }
        r.setMsg(requestContext.getMessage("qqcg"));
        r.setCode(Result.SUCCESS);

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

    /**
     * 用户列表接口
     *
     * @param user_id 教师ID
     */
    @ResponseBody
    @RequestMapping("/userList")
    public Result userList(@RequestParam(value = "user_id",required = true) Integer user_id,
                           @RequestParam(value="token",required = true) String strToken,
                           HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        List<User> userList = userService.getUserListByTeacherId(user_id);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(200);
        //</editor-fold>
        List<User> datalist = new ArrayList<User>();
        //<editor-fold desc="实时数据列表赋值：datalist">
        for (User user : userList) {
            User m = new User();
            m.setId(user.getId());
            m.setUserName(user.getUserName());
            m.setStNum(user.getStNum());
            m.setRelName(user.getRelName());
            m.setStatus(user.getStatus());
            m.setCreateTime(user.getCreateTime());
            m.setCreateUser(user.getCreateUser());
            m.setClassName(user.getClassName());
            m.setMobile(user.getMobile());
            m.setGroupName(user.getGroupName());
            m.setRoleName(user.getRoleName());
            datalist.add(m);
        }
        //</editor-fold>
        Map<String, Object> UserListMap = new HashMap<>();
        UserListMap.put("datalist", datalist);
        r.setData(UserListMap);
        return r;
    }

    /**
     * 删除我的学员信息
     *
     * @param id 学员ID
     */
    @ResponseBody
    @RequestMapping("/delstudents")
    public Result delstudents(@RequestParam(value = "id",required = true) String id,
                              @RequestParam(value="token",required = true) String strToken,
                              HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        if(id.contains(",")){
            Result r = new Result();
            int j=0;
            String [] str=id.split(",");
            for(int i=0;i<str.length;i++){
                int result = userService.delete(Integer.valueOf(str[i]));
                if(result>0){
                    j++;
                }
            }
            if(j==str.length){
                r.setMsg(requestContext.getMessage("OK"));
                r.setCode(200);
                r.setData("");
                return r;
            }
            else
            {
                if(j>0){
                    r.setMsg(requestContext.getMessage("OK"));
                    r.setCode(201);
                    r.setData("部分数据尚未删除，请检查数据或联系管理员！");
                }else
                {
                    r.setMsg(requestContext.getMessage("Err"));
                    r.setCode(100);
                    r.setData("删除失败");
                }
                return r;
            }
        }else{
            int result = userService.delete(Integer.valueOf(id));
            Result r = new Result();
            if(result>0){
                r.setMsg(requestContext.getMessage("OK"));
                r.setCode(200);
                r.setData("");
            }else
            {
                r.setMsg(requestContext.getMessage("Err"));
                r.setCode(100);
                r.setData("删除失败");
            }
            return r;
        }
    }

    /**
     * 重置当前选中学员的密码
     *
     * @param id 学员ID
     */
    @ResponseBody
    @RequestMapping("/resetstudentspwd")
    public Result resetstudentspwd(@RequestParam(value = "id",required = true) String id,
                                   @RequestParam(value="token",required = true) String strToken,
                                   HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        int result = userService.updateUserPwd(Integer.parseInt(id), MD5Code.encodeByMD5("000000"));
        Result r = new Result();
        if(result>0){
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(200);
            r.setData("密码重置成功");

        }else
        {
            r.setMsg(requestContext.getMessage("Err"));
            r.setCode(100);
            r.setData("重置密码失败");
        }
        return r;
    }

    /**
     * 导入学员接口
     *
     */
    @RequestMapping("/importstudentsinfo")
    @ResponseBody
    public Result importstudentsinfo(@RequestParam("file") MultipartFile file,
                                     HttpServletRequest request) throws Exception {
        RequestContext requestContext = new RequestContext(request);
        String contents="";
        String UserAccount="";
        Result r = new Result();
        //解析excel文件
        List<ArrayList<String>> row = ExcelUtils.analysis(file);
        if(row.size()>0){
            User m = new User();
            //验证excel是否合法化
            for (int i = 0;i<row.size();i++){
                List<String> cell = row.get(i);
                for (int j = 0;j<cell.size();j++){
                    if(j<=2){
                        if(j==0){
                            //判断当前行的第一列学号是否重名
                            if(UserAccount.indexOf(cell.get(j))!=-1){
                                contents+="第"+i+"行的第"+j+"列已存在相同值";
                            }
                            UserAccount+=cell.get(j)+",";
                        }
                        if(cell.get(j).length()==0){
                            contents+="第"+i+"行的第"+j+"列为空";
                        }
                    }
                }
            }
            if(contents.length()==0){
                //插入数据库数据
                for (int i = 0;i<row.size();i++){
                    List<String> cell = row.get(i);
                    for (int j = 0;j<cell.size();j++){
                        //获取用户实体
                        switch (j){
                            case 0 :
                                m.setStNum(cell.get(j));
                                break;
                            case 1 :
                                m.setRelName(cell.get(j));
                                break;
                            case 2 :
                                m.setClassName(cell.get(j));
                                break;
                            case 3 :
                                m.setUserName(cell.get(j));
                                break;
                            case 4 :
                                m.setMobile(cell.get(j));
                                break;
                            case 5 :
                                m.setGroupName(cell.get(j));
                                break;
                            case 6 :
                                m.setGroupName(cell.get(j));
                                break;
                            case 7 :
                                m.setRoleName(cell.get(j));
                                break;
                        }
                    }
                    int result=userService.insert(m);
                    if(result>0){

                    }
                }
                r.setMsg(requestContext.getMessage("OK"));
                r.setCode(200);
                r.setData("导入学员成功");
            }
            else
            {
                String filepath= WriteLogUtils.writeToResource(contents);
                r.setMsg(requestContext.getMessage("Err"));
                r.setCode(202);
                r.setData("文件内容不符合要求，详情请查看日志"+filepath);
            }
        }
        else {
            r.setMsg(requestContext.getMessage("Err"));
            r.setCode(201);
            r.setData("文件为空");
        }
        return  r;
    }


    @ResponseBody
    @RequestMapping("/test")
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
//    public static void main(String[] args) {
//
//    }
}
