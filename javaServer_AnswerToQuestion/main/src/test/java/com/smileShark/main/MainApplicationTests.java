package com.smileShark.main;

import com.smileShark.main.entity.QuestionAndAnswer;
import com.smileShark.main.mapper.QuestionAndAnswerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainApplicationTests {

    @Autowired
    private QuestionAndAnswerMapper questionAndAnswerMapper;
    @Test
    void contextLoads() {
//        for (QuestionAndAnswer questionAndAnswer : questionAndAnswerMapper.selectAllQuestionAndAnswer()) {
//            if(questionAndAnswer.getAnswers().split("LBT_1534_LX_5212_WZL_4818").length%2==1){
//                int i = questionAndAnswerMapper.deleteQuestionAndAnswerByQuestionId(questionAndAnswer.getQuestionId());
//                if(i>0){
//                    System.out.println("删除成功:"+questionAndAnswer.getQuestionId());
//                }
//            }

    }

}
