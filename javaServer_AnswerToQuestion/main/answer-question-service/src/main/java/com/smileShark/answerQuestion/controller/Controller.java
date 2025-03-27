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
import lombok.RequiredArgsConstructor;
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
    public String FinishAboutAutomaticallyAnswering(@RequestBody Request request, @RequestHeader("token") String jwt) {
        /*
          接收到选择的Id后台处理，
          这里需要解析jwt令牌来获取到用户的账号和密码
          根据选择的Id来调用不同的方法
          由于可能题目很多，将会有多次请求
          这次的请求将会通过python获取到需要回答的题目的数量
          后台会创建一个全局变量，在没有完成之前都一直存储当前完成题目的数量
          在这个请求后，会有一个请求来确定答题数量是否完成
         */
        return questionAndAnswerService.getNeedAnswerQuestion(request, jwt);
    }

    @RequestMapping("/finishState")
    public String finishState(@RequestBody Request request, @RequestHeader("token") String jwt) {
        return questionAndAnswerService.answerQuestion(request, jwt);
    }

    @RequestMapping("/finish")
    public String finish(@RequestHeader("token") String jwt) {
        questionAndAnswerService.addQuestion(jwt);
        return "OK";
    }
    @RequestMapping("/search/test")
    public String search(@RequestBody Request request,
                         @RequestHeader(value = "user-info",
                         required = false)String user) {
        String decodedUserJson = URLDecoder.decode(user, StandardCharsets.UTF_8); // 解码
        System.out.println(decodedUserJson);
        System.out.println(JSONObject.parse(decodedUserJson));
        /**
         * 1. 根据服务名称获取服务的实例列表
         * 2. 手写负载均衡，从实例中挑选一个实例
         * 3. 利用RestTemplate发送请求,得到响应结果
         */
        /*//1. 根据服务名称获取服务的实例列表
        List<ServiceInstance> instances = discoveryClient.getInstances("search-service");
        if(instances.isEmpty()){
           return "No instances found";
        }
        //2. 手写负载均衡，从实例中挑选一个实例
        ServiceInstance instance = instances.get(new Random().nextInt(instances.size()));
        //3. 利用RestTemplate发送请求,得到响应结果
        ResponseEntity<String> response = restTemplate.exchange(
                instance.getUri() + "/selectAnswers",
                HttpMethod.POST,
                new HttpEntity<>(request),
                String.class
        );
        System.out.println(response.getBody());
        return response.getBody();*/
        String s = searchClient.selectAnswers(BeanUtil.copyProperties(request, com.smileShark.api.dto.Request.class));
        System.out.println(s);
        return s;
    }
}
