package com.smileShark.main.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {
    public static final ExecutorService executorService= Executors.newFixedThreadPool(10);
}
