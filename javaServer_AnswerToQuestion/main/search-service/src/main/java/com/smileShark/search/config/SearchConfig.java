package com.smileShark.search.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:another-config.properties")
public class SearchConfig {
    @Value("${search.size}")
    private Integer size;
    @Value("${mysql.data.split.string}")
    private String splitString;
}
