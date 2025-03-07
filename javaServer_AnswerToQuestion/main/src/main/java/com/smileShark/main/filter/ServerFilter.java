package com.smileShark.main.filter;
import com.alibaba.fastjson.JSONObject;
import com.smileShark.main.common.Result;
import com.smileShark.main.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;


@Slf4j
@WebFilter(urlPatterns="/*")
public class ServerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("ServerFilter执行...");

        HttpServletRequest req= (HttpServletRequest) servletRequest;
        HttpServletResponse resp=(HttpServletResponse) servletResponse;
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String url=req.getRequestURL().toString();

        log.info("请求的url：{}",url);
        if(url.contains("login")){
            log.info("登陆操作，放行...");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        String jwt= req.getHeader("token");

        if (!StringUtils.hasLength(jwt)) {
            log.info("token为空，拦截请求...");
            Result error = new Result();
            error.setCode(401);
            error.setMessage("请先登录");
            System.out.println("error:" + error);
            resp.getWriter().write(JSONObject.toJSONString(error));
            resp.getWriter().flush(); // 刷新缓冲区
            resp.getWriter().close(); // 关闭流
            return;
        }

        // 解析token，判断是否合法
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            log.info("token解析失败，拦截请求...");
            Result error = new Result();
            error.setCode(401);
            error.setMessage("身份验证失败");
            resp.getWriter().write(JSONObject.toJSONString(error));
            resp.getWriter().flush(); // 刷新缓冲区
            resp.getWriter().close(); // 关闭流
            return;
        }
        log.info("令牌合法，放行");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}