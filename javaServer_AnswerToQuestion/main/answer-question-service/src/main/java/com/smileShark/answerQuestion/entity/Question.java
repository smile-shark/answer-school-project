package com.smileShark.answerQuestion.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Component
public class Question {
    private String questionId;
    private String question;
    private List<Answer> answers;
    private LocalDateTime startTime;
}
