package com.smileShark.answerQuestion.service;


import com.smileShark.answerQuestion.common.Request;

public interface QuestionAndAnswerService {
    String getNeedAnswerQuestion(Request request,String jwt);
    String answerQuestion(Request request, String jwt);
    void addQuestion(String jwt);
}
