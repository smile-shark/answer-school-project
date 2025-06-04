package com.smileShark.filter;

import com.alibaba.fastjson.JSONObject;
import com.smileShark.common.Result;
import com.smileShark.entity.User;
import com.smileShark.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;


@Slf4j
@WebFilter(urlPatterns = "/*")
public class ServerFilter implements Filter {
    public static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("ServerFilter执行...");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String url = req.getRequestURL().toString();
        String requestURI = req.getRequestURI();

        log.info("请求的url：{}", url);
        if(requestURI.contains("swagger")
        || requestURI.startsWith("/v3/api-docs")){
            filterChain.doFilter(servletRequest, servletResponse);
        }

        if (requestURI.startsWith("/login") && !requestURI.contains("/loginIn")) {
            log.info("登陆操作，放行...");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String jwt = req.getHeader("token");

        if (!StringUtils.hasLength(jwt)) {
            log.info("token为空，拦截请求...");
            resp.getWriter().write(JSONObject.toJSONString(new Result(){{
                setCode(401);
                setMessage("请先登录");
            }}));
            resp.getWriter().flush(); // 刷新缓冲区
            resp.getWriter().close(); // 关闭流
            return;
        }

        // 解析token，判断是否合法
        try {
            User user = JwtUtils.parseJWT(jwt, User.class);
            userThreadLocal.set(user);
        } catch (Exception e) {
            log.info("token解析失败，拦截请求...");
            resp.getWriter().write(JSONObject.toJSONString(new Result() {{
                setCode(401);
                setMessage("身份验证失败");
            }}));
            resp.getWriter().flush(); // 刷新缓冲区
            resp.getWriter().close(); // 关闭流
            return;
        }
        log.info("令牌合法，放行");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        // 清理资源
        userThreadLocal.remove();
    }
}