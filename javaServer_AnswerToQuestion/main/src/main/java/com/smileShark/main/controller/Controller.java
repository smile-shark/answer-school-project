package com.smileShark.main.controller;

import com.alibaba.fastjson.JSONObject;
import com.smileShark.main.common.Request;
import com.smileShark.main.common.Result;
import com.smileShark.main.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class Controller {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionAndAnswerService questionAndAnswerService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private SubsectionService subsectionService;

    //确认是否登录结束
    @RequestMapping("/loginIn")
    public String loginIn() {
        return JSONObject.toJSONString(Result.success());
    }

    //登陆接口
    @RequestMapping("/login")
    public String login(@RequestBody Request request, HttpServletRequest httpServletRequest) {
        return userService.login(request, httpServletRequest);
    }

    //获取问题列表接口
    @RequestMapping("/selectAnswers")
    public String selectAnswers(@RequestBody Request request) {
        // 获取到问题以及索引，服务器自设定每页数量，通过index参数确定页数，返回对应页数的答案列表
        return questionAndAnswerService.selectAnswersByQuestion(request);
    }

    @RequestMapping("/selectCourse")
    public String selectCourse() {
        return courseService.selectAllCourses();
    }

    @RequestMapping("/selectChapter")
    public String selectChapter(@RequestBody Request request) {
        return chapterService.selectChapterByCourseId(request);
    }

    @RequestMapping("/selectSubsection")
    public String selectSubsection(@RequestBody Request request) {
        return subsectionService.selectSubsectionByChapterId(request);
    }

    @RequestMapping("/FinishAboutAutomaticallyAnswering")
    public String FinishAboutAutomaticallyAnswering(@RequestBody Request request, @RequestHeader("token") String jwt){
        /*
          接收到选择的Id后台处理，
          这里需要解析jwt令牌来获取到用户的账号和密码
          根据选择的Id来调用不同的方法
          由于可能题目很多，将会有多次请求
          这次的请求将会通过python获取到需要回答的题目的数量
          后台会创建一个全局变量，在没有完成之前都一直存储当前完成题目的数量
          在这个请求后，会有一个请求来确定答题数量是否完成
         */
        return questionAndAnswerService.getNeedAnswerQuestion(request,jwt);
    }

    @RequestMapping("/finishState")
    public String finishState(@RequestBody Request request, @RequestHeader("token") String jwt){
        return questionAndAnswerService.answerQuestion(request,jwt);
    }

    @RequestMapping("/finish")
    public String finish(@RequestHeader("token") String jwt) throws IOException {
        questionAndAnswerService.addQuestion(jwt);
        return "OK";
    }
}
