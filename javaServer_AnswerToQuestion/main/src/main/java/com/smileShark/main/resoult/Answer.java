package com.smileShark.main.resoult;

import lombok.Data;

@Data
public class Answer {
    private String answerContent;
    private Boolean isTrue;

    public Answer(String answer1, String answerId1) {
        this.answerContent = answer1;
        this.isTrue = !answerId1.equals("该位置为错误答案");
    }
}
