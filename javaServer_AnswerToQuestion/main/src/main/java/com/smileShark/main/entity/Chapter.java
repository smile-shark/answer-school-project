package com.smileShark.main.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Chapter {
    private String chapterId;
    private String chapterTitle;
    private String chapterName;
    private String courseId;
    private List<Subsection> subsections;
}
