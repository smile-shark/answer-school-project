package com.smileShark.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.core.env.Environment;

@MapperScan("com.smileShark.user.mapper")
@ComponentScan(basePackages = {"com.smileShark.scripts", "com.smileShark.user"})
@SpringBootApplication
public class UserApplication implements ApplicationListener<ApplicationContextEvent> {

    private Environment env;

    @Autowired
    public UserApplication(Environment env){
        this.env = env;
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        // 从环境中获取应用名称
        String appName = env.getProperty("spring.application.name");
        System.out.println("Application name at startup: " + appName);
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}