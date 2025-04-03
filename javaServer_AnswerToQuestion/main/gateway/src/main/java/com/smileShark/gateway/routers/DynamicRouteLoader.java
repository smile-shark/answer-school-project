package com.smileShark.gateway.routers;

import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicRouteLoader {
    private final NacosConfigManager nacosConfigManager;
    private final RouteDefinitionWriter definitionWriter;
    private final Set<String> routeIds=new HashSet<>();
    private final ApplicationEventPublisher applicationEventPublisher;

    @PostConstruct
    public void initRouteConfigListener() throws NacosException {
        String group = "DEFAULT_GROUP";
        String dataId = "gateway-routers.json";
        // 项目启动时，加载路由配置
        String configInfo = nacosConfigManager.getConfigService()
                .getConfigAndSignListener(
                        dataId, group, 5000, new Listener() {
                            @Override
                            public Executor getExecutor() {
                                // 配置线程池
                                return null;
                            }

                            @Override
                            public void receiveConfigInfo(String s) {
                                // 路由配置更新时，重新加载路由配置
                                updateConfigInfo(s);
                            }
                        }
                );
        // 第一次读取到配置，加载路由配置
        updateConfigInfo(configInfo);
    }
    public void updateConfigInfo(String configInfo){
        log.debug("更新路由配置{}",configInfo);
        // 解析配置文件转为路由配置
        List<RouteDefinition> routeDefinitions = JSONUtil.toList(configInfo, RouteDefinition.class);
        // 清空旧的路由配置
        for (String routeId : routeIds) {
            Disposable subscribe = definitionWriter.delete(Mono.just(routeId))
                    .doOnSuccess(v -> log.debug("路由 {} 已删除", routeId))
                    .doOnError(e -> log.error("路由 {} 删除失败", routeId, e))
                    .subscribe();
        }
        // 清理记录的路由id
        routeIds.clear();
        // 写入新的路由配置
        for (RouteDefinition routeDefinition : routeDefinitions) {
            // 写入路由配置
            Disposable subscribe = definitionWriter.save(Mono.just(routeDefinition)).subscribe();
            if(subscribe.isDisposed()){
                System.out.println("写入路由成功:"+routeDefinition.getId());
            }
            // 记录路由id，便于后续更新路由配置
            routeIds.add(routeDefinition.getId());
        }
        // 发布路由更新事件，通知网关重新加载路由配置，不使用这个会失效
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
        log.debug("路由配置更新完成");
    }
}
