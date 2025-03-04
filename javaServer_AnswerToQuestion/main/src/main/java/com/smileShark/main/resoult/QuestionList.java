package com.smileShark.main.resoult;

import lombok.Data;

@Data
public class QuestionList {
    private Integer pageIndex;
    private Integer dataCount;
    private Question[] data;
}
