package com.smileShark.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@TableName("course")
public class Course {
    @TableId(value = "course_id",type = IdType.ASSIGN_UUID)
    private String courseId;
    private String courseName;
    private List<Chapter> chapters;
}
