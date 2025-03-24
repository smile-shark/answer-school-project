package com.smileShark.search.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smileShark.search.common.Request;
import com.smileShark.search.common.Result;
import com.smileShark.search.entity.Answer;
import com.smileShark.search.entity.AnswerData;
import com.smileShark.search.entity.Question;
import com.smileShark.search.entity.QuestionAndAnswer;
import com.smileShark.search.mapper.QuestionAndAnswerMapper;
import com.smileShark.search.service.QuestionAndAnswerService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data
@Slf4j
@Service
@PropertySource("classpath:another-config.properties")
public class QuestionAndAnswerServiceImp implements QuestionAndAnswerService {
    @Value("${mysql.data.split.string}")
    private String splitString;
    @Autowired
    private QuestionAndAnswerMapper questionAndAnswerMapper;

    @Override
    public String selectAnswersByQuestion(Request request) {
        Result result = Result.error().setMessage("查询失败");

        // 处理获得的字符串
        StringBuilder buffer = new StringBuilder("%");
        buffer.append(String.join("%", request.getQuestion().replaceAll(
                "[ '<>&/()（）%_ ]", ""
        ).split("")));
        buffer.append("%");
        try {
            List<Question> questions = new CopyOnWriteArrayList<>();
            Page<QuestionAndAnswer> page = PageHelper.startPage(request.getIndex(), 10);
            questionAndAnswerMapper.selectQuestionAndAnswerByQuestion(
                    buffer.toString()).forEach(questionAndAnswer -> {
                        questions.add(new Question() {{
                            setQuestionId(questionAndAnswer.getQuestionId());
                            setQuestion(questionAndAnswer.getQuestion().replaceAll("/oss/api/ImageViewer/", "https://ai.cqzuxia.com/oss/api/ImageViewer/"));
                            setAnswers(makeAnswers(questionAndAnswer.getAnswers()));
                            setStartTime(questionAndAnswer.getStartTime());
                        }});
                    }
            );
            System.out.println("获取答案成功");
            result = Result.success().setMessage("获取答案成功").setData(
                    new AnswerData() {{
                        setDataCount((int) page.getTotal());
                        setPageIndex(request.getIndex());
                        setQuestions(
                                questions
                        );
                    }}
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(result);
    }


    private List<Answer> makeAnswers(String answers) {
        List<Answer> answerList = new CopyOnWriteArrayList<>();
        List<String> list = Arrays.asList(answers.split(splitString));
        for (int i = 0; i < list.size(); i += 2) {
            Answer answer = new Answer();
            answer.setAnswerId(list.get(i));
            answer.setAnswer(list.get(i + 1));
            answerList.add(answer);
        }
        return answerList;
    }
}
