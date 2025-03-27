package com.smileShark.user.mapper;

import com.smileShark.scripts.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    int insertUser(@Param("user") User user);
}
