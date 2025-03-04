package com.smileShark.main.resoult;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GetCourseId {
    private String courseId;

    @JsonProperty("courseId")
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "GetCourseId{" +
                "courseId='" + courseId + '\'' +
                '}';
    }
}
