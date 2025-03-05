package com.smileShark.main.service.imp;

import com.alibaba.fastjson.JSONObject;
import com.smileShark.main.common.Request;
import com.smileShark.main.common.Result;
import com.smileShark.main.entity.Subsection;
import com.smileShark.main.mapper.SubsectionMapper;
import com.smileShark.main.service.SubsectionService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Data
@Service
public class SubsectionServiceImp implements SubsectionService {
    @Autowired
    private SubsectionMapper subsectionMapper;
    @Override
    public String selectSubsectionByChapterId(Request request) {
        Result result=Result.error().setMessage("获取小节失败");
        try {
            List<Subsection> subsections = subsectionMapper.selectSubsectionByChapterId(request.getChapterId());
            result=Result.success().setMessage("获取小节成功").setData(subsections);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return JSONObject.toJSONString(result);
    }
}
