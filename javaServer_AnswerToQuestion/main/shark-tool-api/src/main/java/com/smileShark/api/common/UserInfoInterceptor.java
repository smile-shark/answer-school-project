package com.smileShark.api.common;

import cn.hutool.core.util.StrUtil;
import com.smileShark.api.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取用户登录信息
        String header = request.getHeader("user-info");
        // 判断是否获取到了用户的登录信息
        if(StrUtil.isNotBlank(header)){
            String decodedUserJson = URLDecoder.decode(header, StandardCharsets.UTF_8); // 解码
            System.out.println("preHandle: " + decodedUserJson);
            // 处理用户登录信息
            UserContext.setUser(decodedUserJson);
        }
        // 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 处理完请求，返回内容后，清理资源
        UserContext.removeUser();
    }
}
