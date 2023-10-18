package com.ims.service.impl;

import com.ims.entity.Users;
import com.ims.mapper.UserMapper;
import com.ims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void createUser(Users user) {
        userMapper.insert(user);
    }

}
