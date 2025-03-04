package com.smileShark.main.business.logic;

import com.smileShark.main.mapper.AccessUser;
import com.smileShark.main.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class SetLogin {
    @Autowired
    private AccessUser accessUser;
    //这个方法只作为登录验证的入口，具体的登录逻辑由python脚本完成
    public User verifyLogin(User user) throws IOException, InterruptedException {
        /*这里通过获得的用户数据去调用python进行登录测试，
        登录失败就返回结果，
        登录成功就通过java的mybatis检查并更新用户数据
        成功后就创建jwt令牌并返回给控制器
        */
        //创建ProcessBuilder对象
        BufferedReader reader = getBufferedReader(user);
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        //等待Python脚本执行完成
        line = result.toString();
        log.info(line);
        System.out.println(line);
        if (line.contains("thisIsTrue")) {
            try{
                user.setUserName(line.split("&")[1]);
            }catch (Exception e){
                System.out.println("最后的到的字符串："+line);
                System.out.println("\n这是一个错误有关py文件返回值的异常："+e+"\n");
                System.out.println("\n"+e.getMessage()+"\n");
                e.printStackTrace();
                user.setUserName("error");
            }
            //调用自己的方法存储用户数据
            try{
                this.linkDataAccessLayerUpdatesData(user);
            }catch (Exception e){
                System.out.println("这是数据库上的错误：");
                e.printStackTrace();
            }
        };
        System.out.println(user);
        return user;
    }

    private static BufferedReader getBufferedReader(User user) throws IOException, InterruptedException {
//        ProcessBuilder processBuilder = new ProcessBuilder("python", "./javaServer_AnswerToQuestion/main/src/main/resources/static/pythonUnit/login.py", user.getUserId(), user.getUserPassword());
        ProcessBuilder processBuilder = new ProcessBuilder("python", "./pythonUnit/login.py", user.getUserId(), user.getUserPassword());
        //启动进程
        Process process = processBuilder.start();
        //读取Python脚本的输出
        return new BufferedReader(new InputStreamReader(process.getInputStream()));
    }

    private void linkDataAccessLayerUpdatesData(User user) {
        //访问到后端查看是否有数据和检查密码是否更新
        List<User> userList= accessUser.getUserByUserId(user.getUserId());
        //如果没有就添加数据
        if (userList.isEmpty()){
            accessUser.insertUser(user.getUserName(), user.getUserPassword(), user.getUserId());
            return;
        }
        //如果有更新，则更新数据库
        if(!Objects.equals(userList.get(0).getUserPassword(), user.getUserPassword())){
            accessUser.updateUserData(user.getUserName(), user.getUserPassword(), user.getUserId());
        }
    }
}
