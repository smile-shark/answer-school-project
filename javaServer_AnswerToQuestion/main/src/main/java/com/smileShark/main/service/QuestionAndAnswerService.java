package com.smileShark.main.service;

import com.smileShark.main.common.Request;

public interface QuestionAndAnswerService {
    String selectAnswersByQuestion(Request request);
    String getNeedAnswerQuestion(Request request,String jwt);
    String answerQuestion(Request request, String jwt);
    void addQuestion(String jwt);
}
