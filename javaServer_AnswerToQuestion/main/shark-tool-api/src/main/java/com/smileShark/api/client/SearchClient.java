package com.smileShark.api.client;

import com.smileShark.api.dto.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("search-service")
public interface SearchClient {
    @PostMapping("/selectAnswers")
    String selectAnswers(@RequestBody Request request);
}
