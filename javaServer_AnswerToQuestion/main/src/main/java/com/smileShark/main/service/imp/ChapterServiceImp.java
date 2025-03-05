package com.smileShark.main.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileShark.main.common.Request;
import com.smileShark.main.common.Result;
import com.smileShark.main.entity.Chapter;
import com.smileShark.main.mapper.ChapterMapper;
import com.smileShark.main.service.ChapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class ChapterServiceImp implements ChapterService {
    @Autowired
    private ChapterMapper chapterMapper;
    @Override
    public String selectChapterByCourseId(Request request) {
        Result result=Result.error().setMessage("获取章节失败");
        try{
            List<Chapter> chapters = chapterMapper.selectChapterByCourseId(request.getCourseId());
            result=Result.success().setMessage("获取章节列表成功").setData(chapters);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return JSONObject.toJSONString(result);
    }
}
