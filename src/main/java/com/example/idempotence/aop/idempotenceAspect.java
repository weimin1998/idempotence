package com.example.idempotence.aop;

import com.example.idempotence.service.TokenService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class idempotenceAspect {

    @Autowired
    TokenService tokenService;

    @Pointcut("@annotation(com.example.idempotence.annotation.AutoIdempotence)")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        tokenService.checkToken(request);
    }
}
