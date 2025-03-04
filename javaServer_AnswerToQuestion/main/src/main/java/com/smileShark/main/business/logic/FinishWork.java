package com.smileShark.main.business.logic;

import com.smileShark.main.resoult.FinishCountAndContent;
import com.smileShark.main.resoult.FinishId;
import com.smileShark.main.resoult.GetQuestionCount;
import com.smileShark.main.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class FinishWork {

    private String answerQuestionsBySection = "./pythonUnit/answerQuestionsBySection.py";
    private String getAnswers1 = "./pythonUnit/getAnswers1.py";
    private String getCountAll = "./pythonUnit/getCountAll.py";
    private String getCountUseCourseId = "./pythonUnit/getCountUseCourseId.py";
    //这个要4个参数
    private String getCountUserChapter = "./pythonUnit/getCountUserChapter.py";
    private String getCountUseSubsection = "./pythonUnit/getCountUseSubsection.py";
    private String getSomeId = "./pythonUnit/getSomeId.py";

    public GetQuestionCount getQuestionCount(FinishId finishId, User user) throws IOException, InterruptedException {

        BufferedReader reader;
        if (!Objects.equals(finishId.getSelectSubsectionName(), "") && finishId.getSelectSubsectionName() != null) {

            reader = getBufferedReader(user, getCountUseSubsection, finishId.getSelectSubsectionName(), "");
        } else if (!Objects.equals(finishId.getSelectChapterName(), "") && finishId.getSelectChapterName() != null) {
            reader = getBufferedReader(user, getCountUserChapter, finishId.getSelectCourseName(), finishId.getSelectChapterName());

        } else if (!Objects.equals(finishId.getSelectCourseName(), "") && finishId.getSelectCourseName() != null) {
            reader = getBufferedReader(user, getCountUseCourseId, finishId.getSelectCourseName(), "");

        } else {

            reader = getBufferedReader(user, getCountAll, "", "");
        }

        String line;
        StringBuilder result = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        //等待Python脚本执行完成
        //获取到了全部输出
        line = result.toString();
        System.out.println("当前工作目录: " + System.getProperty("user.dir"));
        System.out.println("文件路径: " + System.getProperty("java.class.path"));
        log.info("这是python返回的内容 ：\n{}\n", line);
        log.info(line);
        GetQuestionCount getQuestionCount = new GetQuestionCount();

        String start = "allOfTheseQuestionsCount=";
        String end = "separate";

        //匹配字符串
        Pattern pattern = Pattern.compile(start + "(.*?)" + end);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            getQuestionCount.setQuestionCount(Integer.parseInt(matcher.group(1)));
        } else {
            getQuestionCount.setQuestionCount(0);
        }
        String[] idList;
        try{
            String Array = line.split("separate ")[1];
            if(Objects.equals(Array, "[]")){
                idList = new String[0];
            }else{
                idList = Array.replace("[", "")
                        .replace("]", "")
                        .replace("'", "")
                        .split(", ");
            }
        }catch (Exception e){
            idList = new String[0];
            log.info(e.getMessage());
        }

        getQuestionCount.setSubsectionTdList(idList);
        return getQuestionCount;
    }

    public void getSomeIdFromLogin(User user) throws IOException, InterruptedException {
        getBufferedReader(user, getSomeId, "", "");
    }

    public FinishCountAndContent getFinishCountAndContent(String subsectionId, User user) throws IOException, InterruptedException {

        BufferedReader reader = getBufferedReader(user, answerQuestionsBySection, subsectionId, "");
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            result.append(line).append("\n");
        }
        //等待Python脚本执行完成
        //获取到了全部输出
        line = result.toString();
        FinishCountAndContent finishCountAndContent = new FinishCountAndContent();

        String start = "sepamrate";
        String end = "separate";

        Pattern pattern = Pattern.compile(start + "(.*?)" + end);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            finishCountAndContent.setFinishCount(Integer.parseInt(matcher.group(1)));
        } else {
            finishCountAndContent.setFinishCount(0);
        }
        String Array ="";
        if(line.equals("data")){
            Array="";
        }else{
            Array = line.split("sepamrate")[0];
        }
        finishCountAndContent.setFinishContent(Array);

        return finishCountAndContent;
    }

    private static BufferedReader getBufferedReader(User user, String path, String param1, String param2) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("python", path, user.getUserId(), user.getUserPassword(), param1, param2);
        log.info("执行的命令为：python {} {} {} {} {}", path, user.getUserId(), user.getUserPassword(), param1, param2);


        //启动进程
        Process process = processBuilder.start();
        //读取Python脚本的输出
        return new BufferedReader(new InputStreamReader(process.getInputStream()));
    }

    public  void getSomeAnswers(User user) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("python", this.getAnswers1, user.getUserId(), user.getUserPassword());
        //启动进程
        Process process = processBuilder.start();
    }

}
