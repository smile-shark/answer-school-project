package com.smileShark.answerQuestion.mapper;

import com.smileShark.answerQuestion.entity.Chapter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChapterMapper {
    List<Chapter> selectAllChapter();
    int addChapter(@Param("chapter") Chapter chapter);
    List<Chapter> selectChapterByCourseId(@Param("courseId") String courseId);
}
