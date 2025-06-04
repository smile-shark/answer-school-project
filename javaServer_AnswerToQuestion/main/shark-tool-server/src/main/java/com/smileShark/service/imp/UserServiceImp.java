package com.smileShark.service.imp;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smileShark.common.PythonResult;
import com.smileShark.common.Request;
import com.smileShark.common.ResponseData;
import com.smileShark.common.Result;
import com.smileShark.entity.User;
import com.smileShark.mapper.ChapterMapper;
import com.smileShark.mapper.CourseMapper;
import com.smileShark.mapper.SubsectionMapper;
import com.smileShark.mapper.UserMapper;
import com.smileShark.script.PythonScript;
import com.smileShark.service.UserService;
import com.smileShark.utils.IPAddressUtils;
import com.smileShark.utils.JwtUtils;
import com.smileShark.utils.ThreadUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result login(Request request, HttpServletRequest httpServletRequest) throws IOException, InterruptedException {
        Result result = Result.error();
        // 输出IP地址
        log.info("用户：{} 登录，IP地址：{}", request.getUserId(), IPAddressUtils.getUserIPAddress(httpServletRequest));

        // 调用python接口确认账号和密码
        // 1. 如果是学生，调用学生接口
        if (request.getIdentity() == 0) {
            // 调用学生接口
            PythonResult login = pythonScript.login(request);
            if (login.getIsLogin()) {
                log.info("用户：{} 登录成功", request.getUserId());
                // 存储该用户拥有的课程
                ThreadUtils.executorService.submit(() -> {
                    User user=saveUser(login);

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
                                                    System.out.println("小结id：" + subsection.getSubsectionId());
                                                    System.out.println("章节di：" + subsection.getChapterId());
                                                    ;
                                                    try {
                                                        int i = subsectionMapper.insertSubsection(subsection);
                                                        System.out.println("小结添加：" + i);
                                                    } catch (Exception _) {
                                                        ;
                                                    }
                                                });
                                            });
                                        });
                                        try {
                                            int i = chapterMapper.addChapter(chapter);
                                            System.out.println("章节添加：" + i);

                                        } catch (Exception _) {
                                        }

                                    });
                                });
                                try {
                                    int i = courseMapper.insertCourse(course);
                                    if (i > 0) {
                                        log.info("课程存储成功：{}", course.getCourseName());
                                        pythonScript.saveNewAnswer(course.getCourseId());
                                    }
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
                // 登录成功后创建token
                result = Result.success("登录成功").setData(new ResponseData() {{
                    setUserName(login.getUser().getUserName());
                    setToken(JwtUtils.createJwt(request.getUserId(), request.getUserPassword(), login.getUser().getUserName()));
                }});
            } else {
                result.setMessage("登陆失败，请检查用户名或密码");
            }
        } else if (request.getIdentity() == 1) {
            // 调用教师接口
            PythonResult login = pythonScript.teacherLogin(request);
            if (login.getIsLogin()) {
                // 存储该用户拥有的课程
                ThreadUtils.executorService.submit(() -> {
                    log.info("用户：{} 登录成功", request.getUserId());
                    User user=saveUser(login);
                    /**
                     * TODO：教师登录存储用课程先不做
                     */
                });
                // 登录成功后创建token
                result = Result.success("登录成功").setData(new ResponseData() {{
                    setUserName(login.getUser().getUserName());
                    setToken(JwtUtils.createJwt(request.getUserId(), request.getUserPassword(), login.getUser().getUserName()));
                }});
            } else {
                result.setMessage("登陆失败，请检查用户名或密码");
            }

        }else if (request.getIdentity() == 2) {
            result.setMessage("管理登录还未实现");
        }
        return result;
    }

    private User saveUser(PythonResult login) {
        User user = BeanUtil.copyProperties(login.getUser(), User.class);
        try {
            // 存储用户信息到数据库中
            User hasUser = userMapper.selectById(user.getUserId());
            // 1. 如果用户不存在，则存储用户信息
            if (hasUser == null) {
                userMapper.insertUser(user);
                log.info("用户存储成功：{}", user.getUserName());
            }else{
                // 2. 如果用户存在，则更新用户信息
                int update = userMapper.update(user,new LambdaQueryWrapper<User>().eq(User::getUserId,user.getUserId()));
                if (update != 1) {
                    log.error("用户更新失败：{}", user.getUserName());
                }else {
                    log.info("用户更新成功：{}", user.getUserName());
                }
            }

        } catch (Exception e) {
            log.error("用户存储失败：{}", user.getUserName());
            log.error("{}", e.getMessage());
        }
        return user;
    }

}
