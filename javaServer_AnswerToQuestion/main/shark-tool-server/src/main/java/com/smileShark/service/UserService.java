package com.smileShark.service;

import com.smileShark.common.Request;
import com.smileShark.common.Result;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface UserService {
    Result login(Request request, HttpServletRequest httpServletRequest) throws IOException, InterruptedException;
}
