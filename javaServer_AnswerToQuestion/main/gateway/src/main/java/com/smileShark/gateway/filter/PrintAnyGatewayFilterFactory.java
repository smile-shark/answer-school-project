package com.smileShark.gateway.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class PrintAnyGatewayFilterFactory extends AbstractGatewayFilterFactory<PrintAnyGatewayFilterFactory.Config> {
    @Override
    public GatewayFilter apply(Config config) {
        // 使用OrderedGatewayFilter，设置优先级为1
        return new OrderedGatewayFilter(new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                System.out.println("PrintAnyGatewayFilterFactory");
//                System.out.println(config.getA());
//                System.out.println(config.getB());
//                System.out.println(config.getC());
                return chain.filter(exchange);
            }
        },1);
    }
    // 自定义配置参数，反射的时候会按照这个顺序来
    @Override
    public List<String> shortcutFieldOrder() {
        // 使用反射获取参数类中的值
        return List.of("a", "b", "c");
    }

    // 自定义配置参数类
    @Data
    public static class Config{
        private String a;
        private String b;
        private String c;
    }

    // 将Config字节码传递给父类
    public PrintAnyGatewayFilterFactory() {
        super(Config.class);
    }
}
