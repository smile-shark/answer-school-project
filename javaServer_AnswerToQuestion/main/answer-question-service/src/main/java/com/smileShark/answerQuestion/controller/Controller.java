package com.smileShark.answerQuestion.controller;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.smileShark.answerQuestion.common.Request;
import com.smileShark.answerQuestion.entity.User;
import com.smileShark.answerQuestion.service.ChapterService;
import com.smileShark.answerQuestion.service.CourseService;
import com.smileShark.answerQuestion.service.QuestionAndAnswerService;
import com.smileShark.answerQuestion.service.SubsectionService;
import com.smileShark.api.client.SearchClient;
import com.smileShark.api.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.apache.seata.spring.annotation.GlobalTransactional;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answerQuestion")
public class Controller {
    private final QuestionAndAnswerService questionAndAnswerService;
    private final CourseService courseService;
    private final ChapterService chapterService;
    private final SubsectionService subsectionService;
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;
    private final SearchClient searchClient;

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
    public String FinishAboutAutomaticallyAnswering(@RequestBody Request request) {

        return questionAndAnswerService.getNeedAnswerQuestion(request);
    }

    @RequestMapping("/finishState")
    public String finishState(@RequestBody Request request) {
        return questionAndAnswerService.answerQuestion(request);
    }

    @RequestMapping("/finish")
    public String finish() {
        questionAndAnswerService.addQuestion();
        return "OK";
    }
    @RequestMapping("/search/test")
    @GlobalTransactional // 开启分布式事务
    public String search(@RequestBody Request request) {
        String user = UserContext.getUser();
        System.out.println(JSONObject.parse(user));
        return searchClient.selectAnswers(
                BeanUtil.copyProperties(request, com.smileShark.api.dto.Request.class)
        );
    }
}
