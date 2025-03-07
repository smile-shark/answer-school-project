package com.smileShark.main.script;

import com.smileShark.main.code.PythonPath;
import com.smileShark.main.common.PythonResult;
import com.smileShark.main.common.Request;
import com.smileShark.main.entity.User;
import com.smileShark.main.utils.PythonScriptsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PythonScript {
    @Autowired
    private PythonPath pythonPath;
    @Autowired
    private PythonScriptsUtils pythonScriptsUtils;
    // 通过python脚本返回的信息判断是否可以登录
    public PythonResult login(Request request) throws IOException, InterruptedException {
        return pythonScriptsUtils.usePythonScript(
                pythonPath.PYTHON_SCRIPTS_PATH_LOGIN,
                request.getUserId(),
                request.getUserPassword()
        );
    }
    // 登录成功后获取一下课程的id
    public PythonResult saveSomeCourseId(User user) throws IOException, InterruptedException {
        return pythonScriptsUtils.usePythonScript(
                pythonPath.PYTHON_SCRIPTS_PATH_GTE_SOME_COURSE_ID,
                user.getUserId(),
                user.getUserPassword()
        );
    }
    // 根据小结获取需要回答的题目数量
    public PythonResult getQuestionBySubsectionId(User user,String subsectionId) throws IOException, InterruptedException {
        return pythonScriptsUtils.usePythonScript(
                pythonPath.PYTHON_SCRIPTS_PATH_GET_COUNT_USE_SUBSECTION,
                user.getUserId(),user.getUserPassword(),
                subsectionId
        );
    }
    // 根据章节获取需要回答的题目数量
    public PythonResult getQuestionByChapterId(User user,String courseId,String chapterId) throws IOException, InterruptedException {
        return pythonScriptsUtils.usePythonScript(
                pythonPath.PYTHON_SCRIPTS_PATH_GET_COUNT_USE_CHAPTER,
                user.getUserId(),user.getUserPassword(),
                courseId,
                chapterId
        );
    }
    // 根据课程获取需要回答的题目数量
    public PythonResult getQuestionByCourseId(User user,String courseId) throws IOException, InterruptedException {
        return pythonScriptsUtils.usePythonScript(
                pythonPath.PYTHON_SCRIPTS_PATH_GET_COUNT_USE_COURSE_ID,
                user.getUserId(),user.getUserPassword(),
                courseId
        );
    }
    // 获取所有的
    public PythonResult getQuestionByAll(User user) throws IOException, InterruptedException {
        return pythonScriptsUtils.usePythonScript(
                pythonPath.PYTHON_SCRIPTS_PATH_GET_COUNT_ALL,
                user.getUserId(),user.getUserPassword()
        );
    }
    // 回答问题
    public PythonResult getAllQuestions(User user,String subsectionId) throws IOException, InterruptedException {
        return pythonScriptsUtils.usePythonScript(
          pythonPath.PYTHON_SCRIPTS_PATH_ANSWER_QUESTION_BY_SECTION,
          user.getUserId(),
          user.getUserPassword(),
          subsectionId
        );
    }
    // 收录题目
    public void saveQuestion(User user) throws IOException, InterruptedException {
        pythonScriptsUtils.usePythonScript(
                pythonPath.PYTHON_SCRIPTS_PATH_GET_ANSWERS_1,
                user.getUserId(),
                user.getUserPassword()
        );
    }
    // 收录新答案
    public void saveNewAnswer(String courseId) throws IOException, InterruptedException {
        pythonScriptsUtils.usePythonScript(
                pythonPath.PYTHON_SCRIPTS_PATH_GET_NEW_ANSWERS,
                courseId
        );
    }
}
