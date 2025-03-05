package com.smileShark.main.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.smileShark.main.common.PythonResult;
import com.smileShark.main.common.Request;
import com.smileShark.main.common.ResponseData;
import com.smileShark.main.common.Result;
import com.smileShark.main.entity.*;
import com.smileShark.main.mapper.QuestionAndAnswerMapper;
import com.smileShark.main.script.PythonScript;
import com.smileShark.main.service.QuestionAndAnswerService;
import com.smileShark.main.utils.JwtUtils;
import com.smileShark.main.utils.ThreadUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    @Autowired
    private PythonScript pythonScript;

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
            log.error(e.getMessage());
        }
        return JSONObject.toJSONString(result);
    }

    @Override
    public String getNeedAnswerQuestion(Request request, String jwt) {
        Result result = Result.error().setMessage("获取题目失败");
        try {
            PythonResult pythonResult;
            List<String> subsectionIds=new CopyOnWriteArrayList<>();
            User user = JwtUtils.parseJWT(jwt);
            // 区分用户的需求
            if (request.getSelectSubsectionName() != null && !request.getSelectSubsectionName().isEmpty()) {
                pythonResult = pythonScript.getQuestionBySubsectionId(user, request.getSelectSubsectionName());
            } else if (request.getSelectChapterName() != null && !request.getSelectChapterName().isEmpty()) {
                pythonResult = pythonScript.getQuestionByChapterId(user, request.getSelectChapterName());
            } else if (request.getSelectCourseName() != null && !request.getSelectCourseName().isEmpty()) {
                pythonResult = pythonScript.getQuestionByCourseId(user, request.getSelectCourseName());
            }else{
                pythonResult = pythonScript.getQuestionByAll(user);
            }
            result=Result.success().setData("获取题目数量成功")
                    .setData(new ResponseData(){{
                        setQuestionCount(pythonResult.getQuestionCount());
                        setSubsectionTdList(pythonResult.getSubsectionTdList());
                    }});
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return JSONObject.toJSONString(result);
    }

    @Override
    public String answerQuestion(Request request, String jwt) {
        Result result=Result.error().setData("回答失败");
        try{
            User user = JwtUtils.parseJWT(jwt);
            PythonResult allQuestions = pythonScript.getAllQuestions(user, request.getSubsectionId());
            result=Result.success().setMessage("回答成功")
                    .setData(allQuestions);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return JSONObject.toJSONString(result);
    }

    @Override
    public void addQuestion(String jwt) {
        try {
            User user = JwtUtils.parseJWT(jwt);
            ThreadUtils.executorService.execute(()-> {
                try {
                    pythonScript.saveQuestion(user);
                } catch (Exception e){
                    log.error(e.getMessage());
                }
            });
        }catch (Exception e){
            log.error(e.getMessage());
        }
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
