package com.smileShark.main.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Answer {
    private String answerId;
    private String answer;
}
