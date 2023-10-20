package com.ims.service.impl;

import com.ims.entity.Users;
import com.ims.mapper.UserMapper;
import com.ims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    @Override
    public boolean isValidEmail(String email) {
        if (email == null) return false;
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean isValidPassword(String password) {
        if (password == null) return false;
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @Override
    public int createUser(Users user) {
        return userMapper.insert(user);
    }

    @Override
    public boolean userExist(String email) {
        return userMapper.emailExists(email);
    }

    @Override
    public Users getUser(String email) {
        return userMapper.getUser(email);
    }

    @Override
    public void testDeleteByEmail(String email) {
        userMapper.testDeleteByEmail(email);
    }
}
