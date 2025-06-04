    package com.smileShark.entity;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableId;
    import com.baomidou.mybatisplus.annotation.TableName;
    import com.fasterxml.jackson.annotation.JsonProperty;
    import lombok.Data;
    import lombok.Getter;
    import org.springframework.stereotype.Component;

    @Data
    @Component
    @TableName("user")
    public class User {
        @TableId(value = "user_id",type = IdType.ASSIGN_UUID)
        private String userId;
        private String userPassword;
        private String userName;
    }
