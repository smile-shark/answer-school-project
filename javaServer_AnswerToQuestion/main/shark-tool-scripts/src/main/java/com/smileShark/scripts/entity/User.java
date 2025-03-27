    package com.smileShark.scripts.entity;

    import lombok.Data;
    import org.springframework.stereotype.Component;

    @Data
    @Component
    public class User {
        private String userId;
        private String userPassword;
        private String userName;
    }
