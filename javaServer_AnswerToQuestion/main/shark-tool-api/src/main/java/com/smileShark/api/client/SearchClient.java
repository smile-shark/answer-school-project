package com.smileShark.api.client;

import com.smileShark.api.client.fallback.SearchClientFallbackFactory;
import com.smileShark.api.dto.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "search-service",fallbackFactory = SearchClientFallbackFactory.class)
public interface SearchClient {
    @PostMapping("/search/selectAnswers")
    String selectAnswers(@RequestBody Request request);
}
