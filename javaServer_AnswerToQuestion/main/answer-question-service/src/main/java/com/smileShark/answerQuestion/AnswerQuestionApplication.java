package com.smileShark.answerQuestion;

import com.smileShark.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@MapperScan("com.smileShark.answerQuestion.mapper")
@SpringBootApplication(scanBasePackages = {
        "com.smileShark.answerQuestion",
        "com.smileShark.config"
})
@EnableFeignClients(
        basePackages = "com.smileShark.api.client",
        defaultConfiguration = DefaultFeignConfig.class) // <-- 启用 Feign Clients
public class AnswerQuestionApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnswerQuestionApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
