package com.smileShark.main.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileShark.main.common.Result;
import com.smileShark.main.entity.Course;
import com.smileShark.main.mapper.CourseMapper;
import com.smileShark.main.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CourseServiceImp implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public String selectAllCourses() {
        Result result=Result.error().setMessage("获取课程失败");
        try {
            List<Course> courses = courseMapper.selectAllCourse();
            result=Result.success().setMessage("获取课程成功").setData(courses);
        }catch (Exception e){
            log.error(e.getMessage());
            result.setMessage("获取课程失败，数据库查询异常");
        }
        return JSONObject.toJSONString(result);
    }
}
