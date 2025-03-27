package com.smileShark.user.service;

import com.smileShark.api.dto.Request;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    String login(Request request, HttpServletRequest httpServletRequest);
}
