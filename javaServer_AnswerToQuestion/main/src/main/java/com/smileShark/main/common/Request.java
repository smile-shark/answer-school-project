package com.smileShark.main.common;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Request {
    private String userId;
    private String userPassword;
    private String userName;
    private String question;
    private Integer index;
    private String courseId;
    private String chapterId;
    private String selectCourseName;
    private String selectChapterName;
    private String selectSubsectionName;
    private String subsectionId;
}
