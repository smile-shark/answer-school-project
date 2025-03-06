package com.smileShark.main.utils;

import com.alibaba.fastjson.JSONObject;
import com.smileShark.main.common.PythonResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PythonScriptsUtils {
    public static PythonResult usePythonScript(String scriptPath, String... params) throws IOException, InterruptedException {
        // 调用python脚本
        Process process = Runtime.getRuntime().exec(String.format("python3 %s %s", scriptPath, String.join(" ", params)));
        // 获取python输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }
        int i = process.waitFor();
        if (i == 0) {
            System.out.println("脚本 " + scriptPath + " 正常执行完毕");
        } else {
            System.out.println("脚本 " + scriptPath + " 非正常退出\t退出码：" + i);
        }
        System.out.println("脚本输出：" + json);
        // 返回结构
        return JSONObject.parseObject(json.toString(), PythonResult.class);
    }
}
