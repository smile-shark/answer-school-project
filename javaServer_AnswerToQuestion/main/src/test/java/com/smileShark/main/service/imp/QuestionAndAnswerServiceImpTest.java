package com.smileShark.main.service.imp;

import com.smileShark.main.common.Request;
import com.smileShark.main.service.QuestionAndAnswerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QuestionAndAnswerServiceImpTest {
    @Autowired
    private QuestionAndAnswerService questionAndAnswerService;
    @Test
    public void selectAnswersByQuestionTest(){
        questionAndAnswerService.selectAnswersByQuestion(new Request(){{
            setIndex(1);
            setQuestion("mysql");
        }});
    }
}
