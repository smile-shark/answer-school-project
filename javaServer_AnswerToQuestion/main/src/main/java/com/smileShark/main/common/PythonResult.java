package com.smileShark.main.common;

import com.smileShark.main.entity.User;
import com.smileShark.main.entity.Course;
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
