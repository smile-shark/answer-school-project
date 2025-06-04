package com.smileShark.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
@TableName("question_and_answer")
public class QuestionAndAnswer {
    @TableId(value = "question_id",type = IdType.ASSIGN_UUID)
    private String questionId;
    private String question;
    private String answers;
    private LocalDateTime startTime;
}
