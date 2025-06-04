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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    private final RabbitTemplate rabbitTemplate;

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
//        String user = UserContext.getUser();
//        System.out.println(JSONObject.parse(user));
//        return searchClient.selectAnswers(
//                BeanUtil.copyProperties(request, com.smileShark.api.dto.Request.class)
//        );
//        rabbitTemplate.convertAndSend("simple.queue","hello world");
//        for(int i=0;i<50;i++){
//            rabbitTemplate.convertAndSend("work.queue","spring amqp "+i);
//        }
//        String exchange="answer-question.fanout";
//        String message="hello world";
//        rabbitTemplate.convertAndSend(exchange,"",message);
//        String exchange="answer-question.direct";
//        rabbitTemplate.convertAndSend(exchange,"red","hello world red");
//        rabbitTemplate.convertAndSend(exchange,"yellow","hello world yellow");
//        rabbitTemplate.convertAndSend(exchange,"blue","hello world blue");
//        rabbitTemplate.convertAndSend("answer-question.topic","china.news","中国新闻");
//        rabbitTemplate.convertAndSend("answer-question.topic","china.version","中国版本");
//        rabbitTemplate.convertAndSend("answer-question.topic","china.news.child","中国新闻Child");
        Map<String, Object> msg=new HashMap<>(2);
        msg.put("name","zhangsan");
        msg.put("age",20);
        rabbitTemplate.convertAndSend("object.queue",msg);
        return "OK";
    }
}
