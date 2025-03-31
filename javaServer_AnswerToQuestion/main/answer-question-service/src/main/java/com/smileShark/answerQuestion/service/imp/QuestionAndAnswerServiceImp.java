package com.smileShark.answerQuestion.service.imp;


import com.alibaba.fastjson.JSONObject;
import com.smileShark.answerQuestion.common.PythonResult;
import com.smileShark.answerQuestion.common.Request;
import com.smileShark.answerQuestion.common.ResponseData;
import com.smileShark.answerQuestion.common.Result;
import com.smileShark.answerQuestion.entity.*;
import com.smileShark.answerQuestion.mapper.QuestionAndAnswerMapper;
import com.smileShark.answerQuestion.script.PythonScript;
import com.smileShark.answerQuestion.service.QuestionAndAnswerService;
import com.smileShark.answerQuestion.utils.JwtUtils;
import com.smileShark.answerQuestion.utils.ThreadUtils;
import com.smileShark.api.utils.UserContext;
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
public class QuestionAndAnswerServiceImp implements QuestionAndAnswerService {
    @Autowired
    private QuestionAndAnswerMapper questionAndAnswerMapper;
    @Autowired
    private PythonScript pythonScript;

    @Override
    public String getNeedAnswerQuestion(Request request) {
        Result result = Result.error().setMessage("获取题目失败");
        try {
            PythonResult pythonResult;
            User user = JSONObject.parseObject(UserContext.getUser(), User.class);
            // 区分用户的需求
            if (request.getSelectSubsectionName() != null && !request.getSelectSubsectionName().isEmpty()) {
                pythonResult = pythonScript.getQuestionBySubsectionId(user, request.getSelectSubsectionName());
            } else if (request.getSelectChapterName() != null && !request.getSelectChapterName().isEmpty()) {
                pythonResult = pythonScript.getQuestionByChapterId(user,request.getSelectCourseName(),request.getSelectChapterName());
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
    public String answerQuestion(Request request) {
        Result result=Result.error().setData("回答失败");
        try{
            User user = JSONObject.parseObject(UserContext.getUser(), User.class);
            PythonResult allQuestions = pythonScript.getAllQuestions(user, request.getSubsectionId());
            result=Result.success().setMessage("回答成功")
                    .setData(allQuestions);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return JSONObject.toJSONString(result);
    }

    @Override
    public void addQuestion() {
        try {
            User user = JSONObject.parseObject(UserContext.getUser(), User.class);
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
}
