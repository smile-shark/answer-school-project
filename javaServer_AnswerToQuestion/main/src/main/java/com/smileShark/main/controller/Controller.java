package com.smileShark.main.controller;

import com.alibaba.fastjson.JSONObject;
import com.smileShark.main.business.logic.AnswerAndQuestion;
import com.smileShark.main.business.logic.FinishWork;
import com.smileShark.main.business.logic.IdList;
import com.smileShark.main.business.logic.SetLogin;
import com.smileShark.main.common.Result;
import com.smileShark.main.entity.User;
import com.smileShark.main.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.smileShark.main.resoult.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class Controller {
    @Autowired
    private SetLogin setLogin;
    @Autowired
    private AnswerAndQuestion answerAndQuestion;
    @Autowired
    private IdList idList;
    @Autowired
    private FinishWork finishWork;

    private final ExecutorService executorService= Executors.newFixedThreadPool(10);
    //登陆接口
    @RequestMapping("/login")
    public String login(@RequestBody User user, HttpServletRequest request) throws IOException, InterruptedException {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Real-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        System.out.println("用户信息：" + user.toString() + " IP：" + ipAddress);
        Result res = new Result();
        user=setLogin.verifyLogin(user);
        if(user.getUserName()!=null){
            //成功登陆后创建jwt令牌
            UserNameAndToken userToken=new UserNameAndToken();
            userToken.setUserName(user.getUserName());
            userToken.setToken(JwtUtils.GenJwt(user.getUserId(),user.getUserPassword(),user.getUserName()));
            System.out.println("用户名："+user.getUserName());
            res.setCode(0);
            res.setSuccess(true);
            res.setMessage("登陆成功");
            res.setData(userToken);
        }else{
            res.setCode(401);
            res.setMessage("登陆失败，请检查用户名或密码");
        }
        User finalUser = user;
        executorService.submit(()->{
            try {
                finishWork.getSomeIdFromLogin(finalUser);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        return JSONObject.toJSONString(res);
    }
    //获取问题列表接口
    @RequestMapping("/selectAnswers")
    public String selectAnswers(@RequestBody QuestionIndex questionIndex){
        /*
        获取到问题以及索引，服务器自设定每页数量，通过index参数确定页数，返回对应页数的答案列表
         */
        Result res=new Result();
        res.setCode(0);
        res.setSuccess(true);
        res.setMessage("获取答案成功");
        res.setData(answerAndQuestion.returnQuestionList(questionIndex));
        return JSONObject.toJSONString(res);
    }
    //确认是否登录结束
    @RequestMapping("/loginIn")
    public Boolean loginIn(){
        return true;
    }
    @RequestMapping("/selectCourse")
    public String selectCourse(){
        Result res=new Result();
        //获取课程列表
        res.setCode(0);
        res.setSuccess(true);
        res.setMessage("获取课程列表成功");
        res.setData(idList.getAllCourses());
        return JSONObject.toJSONString(res);
    }
    @RequestMapping("/selectChapter")
    public String selectChapter(@RequestBody GetCourseId courseId){
        Result res=new Result();
        //获取章节列表
        res.setCode(0);
        res.setSuccess(true);
        res.setMessage("获取章节列表成功");
        res.setData(idList.getAllChapters(courseId.getCourseId()));
        return JSONObject.toJSONString(res);
    }
    @RequestMapping("/selectSubsection")
    public String selectSubsection(@RequestBody GetChapterId chapterId){
        Result res=new Result();
        //获取小节列表
        res.setCode(0);
        res.setSuccess(true);
        res.setMessage("获取小节列表成功");
        res.setData(idList.getAllSubsections(chapterId.getChapterId()));
        return JSONObject.toJSONString(res);
    }
    @RequestMapping("/FinishAboutAutomaticallyAnswering")
    public String FinishAboutAutomaticallyAnswering(@RequestBody FinishId finishId, @RequestHeader("token") String jwt) throws IOException, InterruptedException {
        /*
          接收到选择的Id后台处理，
          这里需要解析jwt令牌来获取到用户的账号和密码
          根据选择的Id来调用不同的方法
          由于可能题目很多，将会有多次请求
          这次的请求将会通过python获取到需要回答的题目的数量
          后台会创建一个全局变量，在没有完成之前都一直存储当前完成题目的数量
          在这个请求后，会有一个请求来确定答题数量是否完成

         */
        Result res=new Result();
        res.setCode(0);
        res.setSuccess(true);
        res.setMessage("开始回答");
        res.setData(finishWork.getQuestionCount(finishId,JwtUtils.parseJWT(jwt)));
        return JSONObject.toJSONString(res);
    }
    @RequestMapping("/finishState")
    public String finishState(@RequestBody SubsectionId subsectionId,@RequestHeader("token") String jwt) throws IOException, InterruptedException {
        Result res=new Result();
        res.setCode(0);
        res.setSuccess(true);
        res.setMessage("工作中...");
        res.setData(finishWork.getFinishCountAndContent(subsectionId.getSubsectionId(),JwtUtils.parseJWT(jwt)));
        return JSONObject.toJSONString(res);
    }
    @RequestMapping("/finish")
    public String finish(@RequestHeader("token") String jwt) throws IOException {
        finishWork.getSomeAnswers(JwtUtils.parseJWT(jwt));
        return "OK";
    }
}
