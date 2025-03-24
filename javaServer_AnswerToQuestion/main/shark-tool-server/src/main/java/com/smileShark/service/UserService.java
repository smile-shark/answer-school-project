package com.smileShark.service;

import com.smileShark.common.Request;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    String login(Request request, HttpServletRequest httpServletRequest);
}
