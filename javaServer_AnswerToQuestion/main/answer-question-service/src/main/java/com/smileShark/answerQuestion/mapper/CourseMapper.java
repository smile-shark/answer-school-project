package com.smileShark.answerQuestion.mapper;

import com.smileShark.answerQuestion.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseMapper {
    int insertCourse(@Param("course") Course course);
    List<Course> selectAllCourse();
}
