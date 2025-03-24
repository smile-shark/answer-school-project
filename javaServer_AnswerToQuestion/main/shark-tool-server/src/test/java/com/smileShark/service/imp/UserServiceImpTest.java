package com.smileShark.service.imp;

import com.smileShark.common.Request;
import com.smileShark.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceImpTest {
    @Autowired
    private UserService userService;
    @Test
    public void loginTest(){
        System.out.println(userService.login(new Request(){{
            setUserId("320123199008210001");
            setUserPassword("210001");
        }},null));
    }
}
