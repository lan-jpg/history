/*package com.example.controller;

import com.example.common.Result;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class WebController {

    @Resource
    UserService adminService;
    //表示这是一个get请求接口
    @GetMapping("/hello")//接口的路径
    public Result hello(){
        return Result.success("hello");
    }

    @GetMapping("/admin")
    public Result admin(String name){
        String admin = adminService.admin(name);
        return Result.success(admin);
    }
}
*/