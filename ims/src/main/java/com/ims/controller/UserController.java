package com.ims.controller;

import com.ims.entity.User;
import com.ims.mapper.UserMapper;
import com.ims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register (@RequestBody User user) {
        // TODO email and password are not null
        // TODO check if email exists
        // TODO password restrictions
        // TODO register
        userService.createUser(user);
        // TODO token
        return "OK";
    }


}
