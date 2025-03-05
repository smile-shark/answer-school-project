package com.smileShark.main.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class QuestionAndAnswer {
    private String questionId;
    private String question;
    private String answers;
    private LocalDateTime startTime;
}
