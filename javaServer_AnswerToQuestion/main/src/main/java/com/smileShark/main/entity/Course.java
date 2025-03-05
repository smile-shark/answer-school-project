package com.smileShark.main.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Course {
    private String courseId;
    private String courseName;
    private List<Chapter> chapters;
}
