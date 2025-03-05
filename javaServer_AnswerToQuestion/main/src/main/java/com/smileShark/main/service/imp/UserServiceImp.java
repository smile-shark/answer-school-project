package com.smileShark.main.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileShark.main.common.PythonResult;
import com.smileShark.main.common.Request;
import com.smileShark.main.common.ResponseData;
import com.smileShark.main.common.Result;
import com.smileShark.main.entity.User;
import com.smileShark.main.mapper.ChapterMapper;
import com.smileShark.main.mapper.CourseMapper;
import com.smileShark.main.mapper.SubsectionMapper;
import com.smileShark.main.script.PythonScript;
import com.smileShark.main.service.UserService;
import com.smileShark.main.utils.IPAddressUtils;
import com.smileShark.main.utils.JwtUtils;
import com.smileShark.main.utils.ThreadUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private PythonScript pythonScript;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private SubsectionMapper subsectionMapper;

    @Override
    public String login(Request request, HttpServletRequest httpServletRequest) {
        Result result = Result.error();
        // 输出IP地址
        log.info("用户：{} 登录，IP地址：{}", request.getUserId(), IPAddressUtils.getUserIPAddress(httpServletRequest));

        // 调用python接口确认账号和密码
        try {
            PythonResult login = pythonScript.login(request);
            if (login.getIsLogin()) {
                // 登录成功后创建token
                result = Result.success("登录成功").setData(
                        new ResponseData() {{
                            setUserName(
                                    login.getUser().getUserName()
                            );
                            setToken(JwtUtils.createJwt(
                                    request.getUserId(),
                                    request.getUserPassword(),
                                    login.getUser().getUserName()
                            ));
                        }}
                );

                log.info("用户：{} 登录成功", request.getUserId());
                // 存储该用户拥有的课程
                ThreadUtils.executorService.submit(() -> {
                    User user = new User() {{
                        setUserId(request.getUserId());
                        setUserName(login.getUser().getUserName());
                        setUserPassword(request.getUserPassword());
                    }};
                    try {
                        PythonResult pythonResult = pythonScript.saveSomeCourseId(user);
                        if (pythonResult.getIsGetCourseId()) {
                            System.out.println("获取课程成功");
                            // 将返回得到的数据存储到数据库中，使用线程池
                            pythonResult.getCourses().forEach(course -> {

                                ThreadUtils.executorService.submit(() -> {
                                    System.out.println(course);
                                    course.getChapters().forEach(chapter -> {

                                        ThreadUtils.executorService.submit(() -> {
                                            chapter.getSubsections().forEach(subsection -> {

                                                ThreadUtils.executorService.submit(() -> {
                                                    System.out.println("小结id："+subsection.getSubsectionId());
                                                    System.out.println("章节di："+subsection.getChapterId());;
                                                    try {
                                                        int i = subsectionMapper.insertSubsection(subsection);
                                                        System.out.println("小结添加：" + i);
                                                    } catch (Exception e) {
                                                        System.out.println(e.getMessage());
                                                    }
                                                });
                                            });
                                        });
                                        try {
                                            int i = chapterMapper.addChapter(chapter);
                                            System.out.println("章节添加：" + i);

                                        } catch (Exception e) {
                                            System.out.println(e.getMessage());
                                        }

                                    });
                                });
                                try {
                                    int i = courseMapper.insertCourse(course);
                                    System.out.println("课程添加：" + i);
                                } catch (Exception _) {
                                }
                            });
                        } else {
                            log.info("用户课程获取失败：{}", user.getUserName());
                        }
                    } catch (Exception e) {
                        log.error("用户课程存储错误：{}", user.getUserName());
                        log.error(e.getMessage());
                    }
                });
            } else {
                result.setMessage("登陆失败，请检查用户名或密码");
            }

        } catch (Exception e) {
            log.error("登录失败：{}", e.getMessage());
        }
        return JSONObject.toJSONString(result);
    }
}
