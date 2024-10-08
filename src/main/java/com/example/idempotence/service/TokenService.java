package com.example.idempotence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    RedisService redisService;

    public String createToken(){
        String token = UUID.randomUUID().toString();
        redisService.setEX(token,token,1000L);
        return token;
    }

    public boolean checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        if(ObjectUtils.isEmpty(token)){
            token = request.getParameter("token");
            if(ObjectUtils.isEmpty(token)){
                throw new RuntimeException("token不可为空");
            }
        }

        if(!redisService.exists(token)){
            throw new RuntimeException("重复的操作！");
        }

        boolean remove = redisService.remove(token);
        if(!remove){
            throw new RuntimeException("删除token失败");
        }
        return true;
    }
}
