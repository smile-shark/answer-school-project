package com.smileShark.api.utils;

public class UserContext {
    private static final ThreadLocal<String> tl = new ThreadLocal<>();
    public static void setUser(String user) {
        tl.set(user);
    }
    public static String getUser(){
        return tl.get();
    }
    public static void removeUser() {
        tl.remove();
    }
}
