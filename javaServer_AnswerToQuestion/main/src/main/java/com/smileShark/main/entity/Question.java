package com.smileShark.main.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Data
@Component
public class Question {
    private String questionId;
    private String question;
    private List<Answer> answers;
    private LocalDateTime startTime;
}
