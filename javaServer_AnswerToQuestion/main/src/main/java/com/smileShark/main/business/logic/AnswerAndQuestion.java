package com.smileShark.main.business.logic;

import com.smileShark.main.mapper.SelectAnswerAndQuestion;
import com.smileShark.main.resoult.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AnswerAndQuestion {
    @Autowired
    SelectAnswerAndQuestion selectAnswerAndQuestion;

    public QuestionList returnQuestionList(QuestionIndex questionIndex){
        //处理获取到的参数后传递到mybatis进行查询
        StringBuilder str= new StringBuilder("%");
        String[] charArray=questionIndex.getQuestion().split("");
        for(String s:charArray){
            if(!Objects.equals(s, " ")
                    && !Objects.equals(s, " ")
                    && !Objects.equals(s, "'")
                    && !Objects.equals(s, ">")
                    && !Objects.equals(s, "<")
                    && !Objects.equals(s, "/")
                    && !Objects.equals(s, ")")
                    && !Objects.equals(s, "\"")
                    && !Objects.equals(s, "(")
                    && !Objects.equals(s, "（")
                    && !Objects.equals(s, "）")){
                str.append(s).append("%");
            }
        }
        List<SQLQuestionType>   questionTypeList= selectAnswerAndQuestion.selectAnswerAndQuestion(String.valueOf(str),questionIndex.getIndex()-1);
        //通过后台mybatis获取到的数据进行处理为可直接使用的数据返回
        QuestionList questionList=new QuestionList();
        questionList.setPageIndex(questionIndex.getIndex());
        questionList.setDataCount(selectAnswerAndQuestion.countAllQuestions(String.valueOf(str)));//这里还需要通过mybatis去获取一下题目的总数
        //创建了一个题目列表
        Question[] questions=new Question[questionTypeList.size()];
        for(int i=0;i<questionTypeList.size();i++){
            questions[i]=new Question();
            questions[i].setQuestionTitle(questionTypeList.get(i).getQuestion());
            questions[i].setAnswerList(new Answer[]{
                    new Answer(questionTypeList.get(i).getAnswer1(),questionTypeList.get(i).getAnswerId1()),
                    new Answer(questionTypeList.get(i).getAnswer2(),questionTypeList.get(i).getAnswerId2()),
                    questionTypeList.get(i).getAnswer3()==null?null:new Answer(questionTypeList.get(i).getAnswer3(),questionTypeList.get(i).getAnswerId3()),
                    questionTypeList.get(i).getAnswer4()==null?null:new Answer(questionTypeList.get(i).getAnswer4(),questionTypeList.get(i).getAnswerId4()),
            });
        }
        //将题目列表存入了QuestionList对象中
        questionList.setData(questions);
        return questionList;
    }
}
