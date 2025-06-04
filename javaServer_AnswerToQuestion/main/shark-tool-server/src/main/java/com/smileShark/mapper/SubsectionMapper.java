package com.smileShark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smileShark.entity.Subsection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SubsectionMapper extends BaseMapper<Subsection> {
    int insertSubsection(@Param("subsection") Subsection subsection);
    List<Subsection> selectSubsectionByChapterId(@Param("chapterId") String chapterId);
}
