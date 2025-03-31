package com.smileShark.api.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HandlerRequestUTF8 {
    public static String handleRequest(String request){
        return URLEncoder.encode(request, StandardCharsets.UTF_8);
    }
    public static String handleRequestParse(String request){
        return URLDecoder.decode(request, StandardCharsets.UTF_8);
    }
}
