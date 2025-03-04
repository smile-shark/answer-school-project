package com.smileShark.main.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChapterMapperTest {
    @Autowired
    private ChapterMapper chapterMapper;
    @Test
    public void selectAllChapterTest() {
        chapterMapper.selectAllChapter().forEach(System.out::println);
    }
}
