package com.smileShark.main.mapper;

import com.smileShark.main.resoult.SQLQuestionType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SelectAnswerAndQuestion {
    @Select("select * from questionandanswer where question like #{questionStr} limit #{index},10")
    @Results({
            @Result(column = "question_id",property = "questionId"),
            @Result(column = "question",property = "question"),
            @Result(column = "answer_id_1",property = "answerId1"),
            @Result(column = "answer1",property = "answer1"),
            @Result(column = "answer_id_2",property = "answerId2"),
            @Result(column = "answer2",property = "answer2"),
            @Result(column = "answer_id_3",property = "answerId3"),
            @Result(column = "answer3",property = "answer3"),
            @Result(column = "answer_id_4",property = "answerId4"),
            @Result(column = "answer4",property = "answer4"),
            @Result(column = "startTime",property = "startTime")
    })
    List<SQLQuestionType> selectAnswerAndQuestion(String questionStr, Integer index);
    @Select("select count(question) from questionandanswer where question like #{questionStr}")
    Integer countAllQuestions(String questionStr);
}
