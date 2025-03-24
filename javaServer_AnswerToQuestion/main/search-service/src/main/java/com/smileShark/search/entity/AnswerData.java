package com.smileShark.search.entity;

import com.smileShark.search.entity.Question;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class AnswerData {
    private Integer dataCount;
    private Integer pageIndex;
    private List<Question> questions;
}
