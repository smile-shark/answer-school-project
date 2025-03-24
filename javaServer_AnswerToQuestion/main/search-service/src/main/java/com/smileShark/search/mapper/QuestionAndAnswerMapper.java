package com.smileShark.search.mapper;

import com.smileShark.search.entity.QuestionAndAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionAndAnswerMapper {
    List<QuestionAndAnswer> selectQuestionAndAnswerByQuestion(@Param("question")String question);
}
