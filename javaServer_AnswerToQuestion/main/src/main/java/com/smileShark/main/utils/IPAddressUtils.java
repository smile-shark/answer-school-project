package com.smileShark.main.utils;

import jakarta.servlet.http.HttpServletRequest;

public class IPAddressUtils {

    public static String getUserIPAddress(HttpServletRequest request) {
        String ipAddress = "";
        try {
            ipAddress = request.getHeader("X-Forwarded-For");
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("X-Real-IP");
            }
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
        } catch (Exception _) {
        }
        return ipAddress;
    }
}
