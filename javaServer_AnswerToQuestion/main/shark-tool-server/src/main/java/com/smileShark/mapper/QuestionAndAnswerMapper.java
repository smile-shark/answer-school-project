package com.smileShark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smileShark.entity.QuestionAndAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface QuestionAndAnswerMapper extends BaseMapper<QuestionAndAnswer> {
    List<QuestionAndAnswer> selectQuestionAndAnswerByQuestion(@Param("question")String question);
    List<QuestionAndAnswer> selectAllQuestionAndAnswer();
    int deleteQuestionAndAnswerByQuestionId(@Param("questionId")String questionId);
}
