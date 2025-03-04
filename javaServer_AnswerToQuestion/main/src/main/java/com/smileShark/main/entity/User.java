    package com.smileShark.main.entity;

    import com.fasterxml.jackson.annotation.JsonProperty;
    import lombok.Data;
    import lombok.Getter;

    @Data
    public class User {
        private String userId;
        private String UserPassword;
        private String UserName;
    }
