package com.esst.ts.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.esst.ts.model.Result;
import com.esst.ts.service.UserTokenService;
import com.esst.ts.utils.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private UserTokenService userTokenService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Result r = new Result();

        String userId = null, token = null;
        Map map = httpServletRequest.getParameterMap();
        Set keSet = map.entrySet();
        for (Iterator itr = keSet.iterator(); itr.hasNext(); ) {
            Map.Entry me = (Map.Entry) itr.next();
            Object ok = me.getKey();
            Object ov = me.getValue();
            String[] value = new String[1];
            if (ov instanceof String[]) {
                value = (String[]) ov;
            } else {
                value[0] = ov.toString();
            }
            if (ok.toString().equals("token")) {
                token = value[0];
            }
            if (ok.toString().equals("userId")) {
                userId = value[0];
            }
        }

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)) {
            httpServletResponse.reset();
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = httpServletResponse.getWriter();
            r.setMsg("请求无效，缺少必要参数");
            r.setCode(Result.PARAMETER_ERROR);
            pw.write(JSONObject.toJSONString(r));
            pw.flush();
            pw.close();
            return false;
        }

        if (userTokenService.checkToken(Integer.parseInt(userId), token, 1)) {
            httpServletResponse.reset();
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = httpServletResponse.getWriter();
            r.setMsg("无效token");
            r.setCode(Result.TOKEN_ERROR);
            pw.write(JSONObject.toJSONString(r));
            pw.flush();
            pw.close();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
