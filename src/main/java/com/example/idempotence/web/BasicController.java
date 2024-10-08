package com.example.idempotence.web;

import com.example.idempotence.annotation.AutoIdempotence;
import com.example.idempotence.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class BasicController {

    @Autowired
    TokenService tokenService;

    @GetMapping("/getToken")
    @ResponseBody
    public String getToken() {
        return tokenService.createToken();
    }


    // http://127.0.0.1:8080/hello?name=lisi
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
        return "Hello " + name;
    }

    // http://127.0.0.1:8080/user
    @RequestMapping("/user")
    @ResponseBody
    @AutoIdempotence
    public User user() {
        User user = new User();
        user.setName("theonefx");
        user.setAge(666);
        return user;
    }

}
