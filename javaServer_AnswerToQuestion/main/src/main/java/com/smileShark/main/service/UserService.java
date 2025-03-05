package com.smileShark.main.service;

import com.smileShark.main.common.Request;
import com.smileShark.main.common.Result;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    String login(Request request, HttpServletRequest httpServletRequest);
}
