package com.smileShark.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@TableName("subsection")
public class Subsection {
    @TableId(value = "subsection_id",type = IdType.ASSIGN_UUID)
    private String subsectionId;
    private String subsectionName;
    private String courseId;
    private String chapterId;
}
