package com.smileShark.main.resoult;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SQLQuestionType {
    private String questionId;
    private String question;
    private String answerId1;
    private String answer1;
    private String answerId2;
    private String answer2;
    private String answerId3;
    private String answer3;
    private String answerId4;
    private String answer4;
    private LocalDateTime startTime;
}
