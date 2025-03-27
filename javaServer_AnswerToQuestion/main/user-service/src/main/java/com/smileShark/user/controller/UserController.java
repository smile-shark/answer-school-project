package com.smileShark.user.controller;

import com.alibaba.fastjson.JSONObject;

import com.smileShark.api.dto.Request;
import com.smileShark.api.dto.Result;
import com.smileShark.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    //确认是否登录结束
    @RequestMapping("/loginIn")
    public String loginIn() {
        return JSONObject.toJSONString(Result.success());
    }

    //登陆接口
    @RequestMapping("/login")
    public String login(@RequestBody Request request, HttpServletRequest httpServletRequest) {
        return userService.login(request, httpServletRequest);
    }
}
