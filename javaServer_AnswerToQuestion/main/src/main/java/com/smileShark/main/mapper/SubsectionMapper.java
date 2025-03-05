package com.smileShark.main.mapper;

import com.smileShark.main.entity.Subsection;
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
