package com.smileShark.answerQuestion.service;


import com.smileShark.answerQuestion.common.Request;

public interface QuestionAndAnswerService {
    String getNeedAnswerQuestion(Request request);
    String answerQuestion(Request request);
    void addQuestion();
}
