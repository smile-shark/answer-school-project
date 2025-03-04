package com.smileShark.main.common;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class Result {
    private Integer code;
    private Boolean success;
    private String message;
    private Object data;
    private Long time = System.currentTimeMillis();

    public static Result error() {
        return new Result() {{
            setCode(500);
            setSuccess(false);
            setMessage("error");
        }};
    }

    public static Result success() {
        return new Result() {{
            setCode(200);
            setSuccess(true);
            setMessage("success");
        }};
    }

    public static Result success(Object o) {
        return new Result() {{
            setCode(200);
            setSuccess(true);
            setMessage("success");
            setData(o);
        }};
    }
}
