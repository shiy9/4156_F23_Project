package com.ims.service;

import com.ims.entity.Users;

public interface UserService {

    /**
     * Create User
     */
    int createUser(Users user);

    boolean isValidEmail(String email);

    boolean isValidPassword(String password);

    boolean userExist(String email);

    Users getUser(String email);
}
