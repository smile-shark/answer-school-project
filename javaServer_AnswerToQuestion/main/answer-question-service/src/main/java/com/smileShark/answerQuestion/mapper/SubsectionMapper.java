package com.smileShark.answerQuestion.mapper;

import com.smileShark.answerQuestion.entity.Subsection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SubsectionMapper {
    int insertSubsection(@Param("subsection") Subsection subsection);
    List<Subsection> selectSubsectionByChapterId(@Param("chapterId") String chapterId);
}
