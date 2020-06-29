package com.esst.ts.controller;

import com.esst.ts.constants.Constants;
import com.esst.ts.model.*;
import com.esst.ts.service.FZhKTService;
import com.esst.ts.service.UserService;
import com.esst.ts.service.UserTokenService;
import com.esst.ts.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
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
    @Resource
    private FZhKTService fZhKTService;
    /**
     * 判断当前学员是否在线
     *
     * @param userId 用户ID
     */
    @ResponseBody
    @RequestMapping("/checkLogin")
    public Result checkLogin(@RequestParam(value = "userId") Integer userId,
                              @RequestParam(value = "token") String token,
                            HttpServletRequest request){
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        UserToken userToken = userTokenService.checkUserTokenIsLogin(userId,token);
        if (userToken != null) {
            r.setMsg("OK");
            r.setCode(0);
            r.setData("当前用户在线");
        } else {
            r.setMsg("OK");
            r.setCode(1);
            r.setData("当前用户离线");
        }
        return r;
    }

    /**
     * 登录接口
     *
     * @param userName 用户名称
     * @param passWord 用户密码
     */
    @ResponseBody
    @RequestMapping("/userLogin")
    public Result userLogin(@RequestParam(value = "userName") String userName,
                            @RequestParam(value = "passWord") String passWord,
                            @RequestParam(value = "type") Integer type,
                            @RequestParam(value = "ipAddress",required = false) String ipAddress,
                            @RequestParam(value = "macAddress",required = false) String macAddress,
                            HttpServletRequest request) throws ParseException {

        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        User user;
        if (type == 0) {
            //教师登录
            user = userService.loginByTeacher(userName, MD5Code.encodeByMD5(passWord));
            if (user == null) {
                r.setMsg("Err");
                r.setCode(201);
                r.setData("用户名或密码错误");
                return r;
            } else {
                //记录教师登录日志
                UserLoginLog userLoginLogModel = new UserLoginLog();
                userLoginLogModel.setUserId(user.getId());
                userLoginLogModel.setCreateTime(DateUtils.stringToDate());
                userLoginLogModel.setStatus(1);
                userLoginLogModel.setisAdmin(1);
                userLoginLogModel.setMacAddress(macAddress);
                userLoginLogModel.setIpAddress(ipAddress);
                int j = userService.insert(userLoginLogModel);
                if (j > 0) {
                    r.setMsg("OK");
                    r.setCode(200);
                } else {
                    r.setMsg("Err");
                    r.setCode(201);
                    r.setData("登录失败");
                }
            }
        } else {
            //学员登录
            user = userService.loginByStudent(userName, passWord);
            if (user == null) {
                r.setMsg("Err");
                r.setCode(201);
                r.setData("登录失败");
                return r;
            } else {
                //记录学员登录日志
                UserLoginLog userLoginLogModel = new UserLoginLog();
                userLoginLogModel.setUserId(user.getId());
                userLoginLogModel.setCreateTime(DateUtils.stringToDate());
                userLoginLogModel.setStatus(1);
                userLoginLogModel.setisAdmin(0);
                userLoginLogModel.setMacAddress(macAddress);
                userLoginLogModel.setIpAddress(ipAddress);
                int j = userService.insert(userLoginLogModel);
                if (j > 0) {
                    r.setMsg("OK");
                    r.setCode(200);
                } else {
                    r.setMsg("Err");
                    r.setCode(201);
                    r.setData("登录失败");
                }
            }
        }
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
        userMap.put("userName", user.getUserName());
        userMap.put("relName", user.getRelName());
        userMap.put("stnum", user.getStNum());
        userMap.put("className", user.getClassName());
        userMap.put("mobile", user.getMobile());
        userMap.put("groupName", user.getGroupName());
        userMap.put("roleName", user.getRoleName());
        userMap.put("operateMode", user.getOperateMode());
        r.setData(userMap);
        return r;
    }

    /**
     * 登出接口  修改Token 状态
     */
    @ResponseBody
    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
    public Result logOut(@RequestParam(value = "userId") int userId,
                         @RequestParam(value = "token") String token,
                         @RequestParam(value = "ipAddress",required = false) String ipAddress,
                         @RequestParam(value = "macAddress",required = false) String macAddress,
                         HttpServletRequest request) throws ParseException {
        RequestContext requestContext = new RequestContext(request);
        Result r = new Result();
        Integer result=userTokenService.invalidToken(userId, 1); // 登出成功删除用户的token
        if(result>0){
            //查询当前用户是教师还是学员
            User u=userService.getUserById(userId);
            //记录登出日志
            UserLoginLog userLoginLogModel = new UserLoginLog();
            userLoginLogModel.setUserId(userId);
            userLoginLogModel.setCreateTime(DateUtils.stringToDate());
            userLoginLogModel.setStatus(2);
            userLoginLogModel.setMacAddress(macAddress);
            userLoginLogModel.setIpAddress(ipAddress);
            if(u.getIsAdmin()==1){
                //教师
                userLoginLogModel.setisAdmin(1);
                fZhKTService.userlivedataTorecord(userId);
            }else
            {
                userLoginLogModel.setisAdmin(0);
            }
            int j = userService.insert(userLoginLogModel);
            if (j > 0) {
                r.setMsg("OK");
                r.setCode(0);
                r.setData("退出成功");
            } else {
                r.setMsg("Err");
                r.setCode(201);
                r.setData("退出失败");
            }
        }
        return r;
    }

    /**
     * 用户列表接口
     *
     * @param userId 教师ID
     */
    @ResponseBody
    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    public Result userList(@RequestParam(value = "userId", required = true) Integer userId,
                           @RequestParam(value = "token", required = true) String strToken,
                           HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        List<User> userList = userService.getUserListByTeacherId(userId);
        Result r = new Result();
        //<editor-fold desc="返回参数初始化">
        r.setMsg(requestContext.getMessage("OK"));
        r.setCode(200);
        //</editor-fold>
        List<User> dataList = new ArrayList<User>();
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
            m.setOperateMode(user.getOperateMode());
            dataList.add(m);
        }
        //</editor-fold>
        Map<String, Object> UserListMap = new HashMap<>();
        UserListMap.put("dataList", dataList);
        r.setData(UserListMap);
        return r;
    }

    /**
     * 删除我的学员信息
     *
     * @param id 学员ID
     * @param userId 教师ID
     */
    @ResponseBody
    @RequestMapping(value = "/delStudents", method = RequestMethod.POST)
    public Result delStudents(@RequestParam(value = "id", required = true) String id,
                              @RequestParam(value = "userId", required = true) Integer userId,
                              @RequestParam(value = "token", required = true) String strToken,
                              HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        if (id.contains(",")) {
            Result r = new Result();
            int j = 0;
            String[] str = id.split(",");
            for (String s : str) {
                int result = userService.delete(Integer.valueOf(s), userId);
                if (result > 0) {
                    j++;
                }
            }
            if (j == str.length) {
                r.setMsg(requestContext.getMessage("OK"));
                r.setCode(200);
                r.setData("删除成功");
                return r;
            } else {
                if (j > 0) {
                    r.setMsg(requestContext.getMessage("OK"));
                    r.setCode(201);
                    r.setData("部分数据尚未删除，请检查数据或联系管理员！");
                } else {
                    r.setMsg(requestContext.getMessage("Err"));
                    r.setCode(100);
                    r.setData("删除失败");
                }
                return r;
            }
        } else {
            int result = userService.delete(Integer.valueOf(id),userId);
            Result r = new Result();
            if (result > 0) {
                r.setMsg(requestContext.getMessage("OK"));
                r.setCode(200);
                r.setData("删除成功");
            } else {
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
    @RequestMapping(value = "/resetStudentsPwd", method = RequestMethod.POST)
    public Result resetStudentsPwd(@RequestParam(value = "id", required = true) String id,
                                   @RequestParam(value = "token", required = true) String strToken,
                                   HttpServletRequest request) {
        RequestContext requestContext = new RequestContext(request);
        int result = userService.updateUserPwd(Integer.parseInt(id), MD5Code.encodeByMD5("000000"));
        Result r = new Result();
        if (result > 0) {
            r.setMsg(requestContext.getMessage("OK"));
            r.setCode(200);
            r.setData("密码重置成功");

        } else {
            r.setMsg(requestContext.getMessage("Err"));
            r.setCode(100);
            r.setData("重置密码失败");
        }
        return r;
    }

    /**
     * 导入学员接口
     */
    @RequestMapping(value = "/importStudentsInfo", method = RequestMethod.POST)
    @ResponseBody
    public Result importStudentsInfo(@RequestParam("file") MultipartFile file,
                                     @RequestParam(value = "userId", required = true) Integer userId,
                                     @RequestParam(value = "token", required = true) String strToken,
                                     HttpServletRequest request) throws Exception {
        RequestContext requestContext = new RequestContext(request);
        StringBuilder contents = new StringBuilder();
        StringBuilder NumAccount = new StringBuilder();
        StringBuilder LoginAccount = new StringBuilder();
        Result r = new Result();
        //解析excel文件
        List<ArrayList<String>> row = ExcelUtils.analysis(file);
        if (row.size() > 0) {
            User m = new User();
            //验证excel是否合法化
            for (int i = 0; i < row.size(); i++) {
                List<String> cell = row.get(i);
                for (int j = 0; j < cell.size(); j++) {
                    int h = i + 1;
                    int l = j + 1;
                    if (j <= 3) {
                        if (j == 0) {
                            //判断当前行的第一列学号是否重名
                            if (NumAccount.toString().contains(cell.get(j))) {
                                contents.append("第").append(h).append("行的第").append(l).append("列已存在相同值\r\n");
                            }
                            NumAccount.append(cell.get(j)).append(",");
                        }
                        if (cell.get(j).length() == 0) {
                            contents.append("第").append(h).append("行的第").append(l).append("列为空\r\n");
                        }
                        if (j == 3) {
                            //判断当前行的第四列账号是否重名
                            if (LoginAccount.toString().contains(cell.get(j))) {
                                contents.append("第").append(h).append("行的第").append(l).append("列已存在相同值\r\n");
                            }
                            LoginAccount.append(cell.get(j)).append(",");
                        }
                    }
                }
            }
            if (contents.length() == 0) {
                //插入数据库数据
                for (List<String> cell : row) {
                    for (int j = 0; j < cell.size(); j++) {
                        //获取用户实体
                        switch (j) {
                            case 0:
                                m.setStNum(cell.get(j));
                                break;
                            case 1:
                                m.setRelName(cell.get(j));
                                break;
                            case 2:
                                m.setClassName(cell.get(j));
                                break;
                            case 3:
                                m.setUserName(cell.get(j));
                                break;
                            case 4:
                                m.setMobile(cell.get(j));
                                break;
                            case 5:
                                m.setGroupName(cell.get(j));
                                break;
                            case 6:
                                m.setOperateMode(cell.get(j));
                                break;
                            case 7:
                                m.setRoleName(cell.get(j));
                                break;
                        }
                    }
                    m.setPassword(MD5Code.encodeByMD5("000000"));
                    m.setStatus(Short.parseShort("0"));
                    m.setCreateTime(DateUtils.stringToDate());
                    m.setCreateUser(userId);
                    m.setIsDel(Short.parseShort("0"));
                    m.setIsAdmin(Short.parseShort("0"));
                    //判断当前学员是否存在
                    User newUser = userService.getCheckUserByNum(m.getStNum());
                    if(newUser!=null){
                        //存在 更新用户信息
                        newUser.setUserName(m.getUserName());
                        newUser.setRelName(m.getRelName());
                        newUser.setClassName(m.getClassName());
                        newUser.setMobile(m.getMobile());
                        newUser.setGroupName(m.getGroupName());
                        newUser.setOperateMode(m.getOperateMode());
                        newUser.setRoleName(m.getRoleName());
                        userService.update(newUser);
                        //查询当前教师是否已导入该学号
                        User newUsers = userService.getUserByNum(m.getStNum(),userId);
                        if (newUsers == null) {
                            TeacherStudentRelation teacherStudentRelation = new TeacherStudentRelation();
                            teacherStudentRelation.setStudentId(newUser.getId());
                            teacherStudentRelation.setTeacherId(userId);
                            teacherStudentRelation.setIsDel(0);
                            userService.insert(teacherStudentRelation);
                        }
                        else
                        {
                            //更新
                            TeacherStudentRelation teacherStudentRelation=userService.selectByUserAndTeacher(newUser.getId(),userId);
                            if(teacherStudentRelation.getIsDel()==1) {
                                teacherStudentRelation.setIsDel(0);
                                userService.updateByPrimaryKey(teacherStudentRelation);
                            }
                        }
                    }
                    else{
                        //不存在
                        int result = userService.insert(m);
                        if (result > 0) {
                            //查询刚刚添加的学员ID
                            User user = userService.getUserLastRecord();
                            TeacherStudentRelation teacherStudentRelation = new TeacherStudentRelation();
                            teacherStudentRelation.setStudentId(user.getId());
                            teacherStudentRelation.setTeacherId(userId);
                            teacherStudentRelation.setIsDel(0);
                            userService.insert(teacherStudentRelation);
                        }
                    }
                }
                r.setMsg(requestContext.getMessage("OK"));
                r.setCode(200);
                r.setData("导入学员成功");
            } else {
                String num = UUID.randomUUID().toString();  //生成一个随机数用作用户名
                String pathName = Constants.UPLOAD_PIC_URL + "/" + num + ".txt";  //生成文件存储在服务器的完整路径 如 /upload/3355d8d7-51c2-488a-80f4-ea97b2e93a30.jpg
                String path = request.getSession().getServletContext().getRealPath("/") + pathName;  //服务器绝对路径用于生成文件
                FileUtils.makefile(path); //空白文件生成
                // 做写文件操作 。。。。
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
                writer.write(contents.toString().toString());
                writer.flush();
                writer.close();
                String url = Constants.getIpAddress(request);  //获取服务器访问ip和端口 例如：http://127.0.0.1:8080/
                url += pathName;  //文件最终访问路径 如：http://127.0.0.1:8080/upload/3355d8d7-51c2-488a-80f4-ea97b2e93a30.jpg
                r.setMsg(requestContext.getMessage("Err"));
                r.setCode(202);
                r.setData("文件内容不符合要求，详情请查看日志" + url);
            }
        } else {
            r.setMsg(requestContext.getMessage("Err"));
            r.setCode(201);
            r.setData("文件为空");
        }
        return r;
    }
}
