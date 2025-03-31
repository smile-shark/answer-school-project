package com.smileShark.search.controller;



import com.smileShark.api.utils.UserContext;
import com.smileShark.search.common.Request;
import com.smileShark.search.service.QuestionAndAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class QuestionAndAnswerController {
    private final QuestionAndAnswerService questionAndAnswerService;

    //获取问题列表接口
    @RequestMapping("/selectAnswers")
    public String selectAnswers(@RequestBody Request request) {
        System.out.println(UserContext.getUser());
        // 获取到问题以及索引，服务器自设定每页数量，通过index参数确定页数，返回对应页数的答案列表
        return questionAndAnswerService.selectAnswersByQuestion(request);
    }
}
