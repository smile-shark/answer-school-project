package com.smileShark.main.resoult;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GetChapterId {
    private String chapterId;

    @JsonProperty("chapterId")
    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public String toString() {
        return "GetChapterId{" +
                "chapterId='" + chapterId + '\'' +
                '}';
    }
}
