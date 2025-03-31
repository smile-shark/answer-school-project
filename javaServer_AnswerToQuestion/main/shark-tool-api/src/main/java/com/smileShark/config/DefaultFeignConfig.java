    package com.smileShark.config;

    import com.smileShark.api.utils.HandlerRequestUTF8;
    import com.smileShark.api.utils.UserContext;
    import feign.Logger;
    import feign.RequestInterceptor;
    import feign.RequestTemplate;
    import org.springframework.context.annotation.Bean;

    public class DefaultFeignConfig {
        @Bean
        public Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }
        @Bean
        public RequestInterceptor userInfoRequestInterceptor(){
            return new RequestInterceptor() {
                @Override
                public void apply(RequestTemplate requestTemplate) {
                    requestTemplate.header("user-info",
                            HandlerRequestUTF8.handleRequest(UserContext.getUser()));
                }
            };
        }
    }
