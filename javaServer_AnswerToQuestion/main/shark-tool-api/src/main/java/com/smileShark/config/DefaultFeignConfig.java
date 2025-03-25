    package com.smileShark.config;

    import feign.Logger;
    import org.springframework.context.annotation.Bean;

    public class DefaultFeignConfig {
        @Bean
        public Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }
    }
