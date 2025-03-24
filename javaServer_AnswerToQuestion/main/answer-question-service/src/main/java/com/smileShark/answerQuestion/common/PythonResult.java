package com.smileShark.answerQuestion.common;

import com.smileShark.answerQuestion.entity.Course;
import com.smileShark.answerQuestion.entity.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
// 返回的python结果接收
public class PythonResult {
    private Boolean isLogin;
    private User user;
    private Boolean isGetCourseId;
    private List<Course> courses;
    private Integer questionCount;
    private List<String> subsectionTdList;
    private String questionData;
    private String finishContent;
    private Integer finishCount;
}
