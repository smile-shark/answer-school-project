package com.smileShark.main.resoult;

import lombok.Data;

@Data
public class Question {
    private String questionTitle;
    private Answer[] answerList;
}
