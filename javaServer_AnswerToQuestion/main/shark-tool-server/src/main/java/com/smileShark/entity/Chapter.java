package com.smileShark.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@TableName("chapter")
public class Chapter {
    @TableId(value = "chapter_id",type = IdType.ASSIGN_UUID)
    private String chapterId;
    private String chapterTitle;
    private String chapterName;
    private String courseId;
    private List<Subsection> subsections;
}
