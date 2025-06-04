package com.smileShark.common;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Request {
    private Integer identity; // 0 学生，1 教师，2 管理员
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
