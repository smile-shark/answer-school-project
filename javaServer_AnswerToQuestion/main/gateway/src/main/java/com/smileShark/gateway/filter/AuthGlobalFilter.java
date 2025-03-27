package com.smileShark.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.smileShark.common.Result;
import com.smileShark.scripts.entity.User;
import com.smileShark.user.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    // 使用sprintBoot的AntPathMatcher来匹配路径
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取exchange中的reqeust
        ServerHttpRequest request = exchange.getRequest();
        // 判断是否需要登录拦截
        RequestPath path = request.getPath();
        if(antPathMatcher.match(path.toString(), "/user/login/**")){
            // 不需要登录拦截
            return chain.filter(exchange);
        }
        // 获取token
        String token = request.getHeaders().getFirst("token");
        // 校验token
        User user = JwtUtils.parseJWT(token, User.class);
        // 如果token校验通过，则放行，否则返回返回Result的ERROR
        if(user == null){
            // 未登录
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            return response.writeWith(Mono.just(
                    response.bufferFactory().wrap(
                            JSONObject.toJSONString(new Result() {{
                                        setCode(401);
                                        setMessage("身份验证失败");
                                    }})
                                    .getBytes()
                    )
            ));
        }
        String jsonString = JSONObject.toJSONString(user);
        String encodedUserJson = URLEncoder.encode(jsonString, StandardCharsets.UTF_8);
        System.out.println(jsonString);
        // 传递用户信息
        ServerWebExchange newExchange = exchange.mutate()
                .request(builder -> builder.header("user-info",
                        encodedUserJson  ))
                .build();

        return chain.filter(newExchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
