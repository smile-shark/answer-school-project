package com.smileShark.api.client.fallback;

import cn.hutool.json.JSONUtil;
import com.smileShark.api.client.SearchClient;
import com.smileShark.api.dto.Request;
import com.smileShark.api.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
@Slf4j
public class SearchClientFallbackFactory implements FallbackFactory<SearchClient> {

    @Override
    public SearchClient create(Throwable cause) {
        log.error("SearchClientFallbackFactory: {}", cause.getMessage());
        // 返回一个自定义的Fallback对象，返回一个处理后的对象
        return new SearchClient() {
            @Override
            public String selectAnswers(Request request) {
                return JSONUtil.toJsonStr(Result.error().setMessage("线程限流，请稍后再试"));
            }
        };
    }
}
