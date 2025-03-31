package com.smileShark.search;

import com.smileShark.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan("com.smileShark.search.mapper")
@SpringBootApplication(scanBasePackages = {
        "com.smileShark.search",
        "com.smileShark.config"
})
@EnableFeignClients(basePackages = "com.smileShark.api.client",defaultConfiguration = DefaultFeignConfig.class)
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }

}
