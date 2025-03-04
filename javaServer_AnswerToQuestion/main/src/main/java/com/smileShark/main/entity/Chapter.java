package com.smileShark.main.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Chapter {
    private String chapterId;
    private String chapterTitle;
    private String chapterName;
    private String courseId;
}
