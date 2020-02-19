package com.lavector.crawlers.tasks.configuration;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Aspect
public class RequestLogAspect {
    private final Logger logger = LoggerFactory.getLogger(RequestLogAspect.class);

    /**
     * 定义切点
     */
    @Pointcut("execution(* com.lavector.crawlers.tasks.controller.TaskController..*(..))")
    public void requestServer() {
    }

    @Around("requestServer()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        //记录请求开始执行时间：
        long beginTime = System.currentTimeMillis();
        //获取请求信息
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();

        //获取代理地址、请求地址、请求类名、方法名
        String remoteAddress = request.getRemoteAddr();
        System.out.println("ip地址：" + remoteAddress);
        String requestURI = request.getRequestURI();
        String methodName = pjp.getSignature().getName();
        String clazzName = pjp.getTarget().getClass().getSimpleName();

        //获取请求参数：
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        //获取请求参数类型
        String[] parameterNames = ms.getParameterNames();
        //获取请求参数值
        Object[] parameterValues = pjp.getArgs();
        StringBuilder sb = new StringBuilder();

        //组合请求参数，进行日志打印
        if (parameterNames != null && parameterNames.length > 0) {
            for (int i = 0; i < parameterNames.length; i++) {
                if (parameterNames[i].equals("bindingResult")) {
                    break;
                }
                if ((parameterValues[i] instanceof HttpServletRequest) || (parameterValues[i] instanceof HttpServletResponse)) {
                    sb.append("[").
                            append(parameterNames[i]).append("=").append(parameterValues[i])
                            .append("]");
                } else {
                    sb.append("[").
                            append(parameterNames[i]).append("=")
                            .append(JSON.toJSONString(parameterValues[i], SerializerFeature.WriteDateUseDateFormat))
                            .append("]");
                }
            }
        }
        Object result;
        try {
            result = pjp.proceed();
            System.out.println("...");
        } catch (Throwable throwable) {
            //请求操纵失败
            //记录错误日志
            logger.error("请求错误！ IP: {}" +
                            "URI->：{} 请求映射控制类->：{} " +
                            "请求方法->：{} 请求参数列表->：{}", remoteAddress, requestURI, clazzName, methodName,
                    sb.toString());
            throw throwable;
        }
        //请求操作成功
        String resultJosnString = "";
        if (result != null) {
            if (result instanceof ResponseEntity) {
                resultJosnString = JSON.toJSONString(result, SerializerFeature.WriteDateUseDateFormat);
            } else {
                resultJosnString = String.valueOf(result);
            }
        }
        //记录请求完成执行时间：
        long endTime = System.currentTimeMillis();
        long usedTime = endTime - beginTime;
        //记录日志
        logger.info("操作成功！ 请求耗时：{} " +
                        "IP: {}  URI->：{} " +
                        "请求映射控制类->：{} 请求方法->：{} " +
                        "请求参数列表->：{} 返回值->：{}", usedTime, remoteAddress, requestURI, clazzName,
                methodName, sb.toString(), resultJosnString);

        return result;
    }
}