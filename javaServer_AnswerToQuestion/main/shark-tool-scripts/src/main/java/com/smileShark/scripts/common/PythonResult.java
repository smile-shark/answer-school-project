package com.smileShark.scripts.common;

import com.smileShark.scripts.entity.Course;
import com.smileShark.scripts.entity.User;
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
