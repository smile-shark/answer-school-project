package com.smileShark.main.mapper;


import com.smileShark.main.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AccessUser {
    @Select("select userId,username,userPas from user where userId = #{userId}")
    @Results({
            @Result(column = "userId",property = "UserId"),
            @Result(column = "username",property = "UserName"),
            @Result(column = "userPas",property = "UserPassword"),
    })
    public List<User> getUserByUserId(String userId);
    @Update("update user set username = #{userName}, userPas = #{userPassword} where userId = #{userId}")
    public int updateUserData(String userName,String userPassword,String userId);
    @Insert("insert into user values (#{userId}, #{userName}, #{userPassword},null)")
    public int insertUser(String userName,String userPassword,String userId);
}
