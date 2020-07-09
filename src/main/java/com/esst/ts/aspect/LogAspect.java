package com.esst.ts.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Aspect//表示这是一个切面类
@Slf4j
public class LogAspect {

//    @Around(value = "execution(* com.esst.ts.controller.*.*(..))")
//    public Object showLog(ProceedingJoinPoint point) {
//        long start = System.currentTimeMillis();
//        Object target = point.getTarget();
//        Object[] args = point.getArgs();
//        MethodSignature signature = (MethodSignature) point.getSignature();
//        String name = signature.getName();
//        Object result = null;
//        try {
//            try {
//                result = point.proceed(args);
//            }catch (Exception e) {
//                log.info("{}-{} 异常信息：{}",target,name,e);
//            } finally {
//            }
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        long end = System.currentTimeMillis();
//        log.info("{}-{} 返回结果：{}",target,name,JSON.toJSONString(result));
//        log.info("{}-{} 请求时长：{}",target,name,end-start);
//        return result;
//    }
}