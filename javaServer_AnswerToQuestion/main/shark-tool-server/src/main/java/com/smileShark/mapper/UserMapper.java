package com.smileShark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smileShark.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    int insertUser(@Param("user")User user);
}
