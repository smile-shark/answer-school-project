package com.smileShark.main.mapper;


import com.smileShark.main.entity.Chapter;
import com.smileShark.main.resoult.Course;
import com.smileShark.main.resoult.Subsection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SelectId{
    @Select("select * from coursetable")
    List<Course> selectAllCourse();
    @Select("select chapterTitle,chapterName,chapterId from chapter where courseId=#{courseId}")
    List<Chapter> selectAllChapter(String courseId);
    @Select("select SubsectionName,SubsectionId  from subsection where chapterId=#{chapterId}")
    @Results({
            @Result(column = "SubsectionName",property = "subsectionName"),
            @Result(column = "SubsectionId",property = "subsectionId")
    })
    List<Subsection> selectAllSubsection(String chapterId);
}
