package com.smileShark.main.common;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class ResponseData {
    private String userName;
    private String token;
    private Integer questionCount;
    private List<String> subsectionTdList;
}
