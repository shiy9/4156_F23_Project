package com.ims.mapper;

import com.ims.entity.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    boolean emailExists(String email);

    int insert(Users user);

    Users getUser(String email);
}
