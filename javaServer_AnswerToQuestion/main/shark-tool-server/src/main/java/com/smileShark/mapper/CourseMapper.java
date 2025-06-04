package com.smileShark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smileShark.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseMapper extends BaseMapper<Course> {
    int insertCourse(@Param("course") Course course);
    List<Course> selectAllCourse();
}
