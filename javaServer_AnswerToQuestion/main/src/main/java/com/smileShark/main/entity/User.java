    package com.smileShark.main.entity;

    import com.fasterxml.jackson.annotation.JsonProperty;
    import lombok.Data;
    import lombok.Getter;
    import org.springframework.stereotype.Component;

    @Data
    @Component
    public class User {
        private String userId;
        private String userPassword;
        private String userName;
    }
