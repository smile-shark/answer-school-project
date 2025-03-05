package com.smileShark.main.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Subsection {
    private String subsectionId;
    private String subsectionName;
    private String courseId;
    private String chapterId;
}
