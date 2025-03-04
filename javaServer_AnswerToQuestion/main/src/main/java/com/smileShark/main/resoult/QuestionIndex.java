package com.smileShark.main.resoult;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class QuestionIndex {
    private String question;
    private Integer index;

    @Override
    public String toString() {
        return "QuestionIndex{" +
                "question='" + question + '\'' +
                ", index='" + index + '\'' +
                '}';
    }
}
