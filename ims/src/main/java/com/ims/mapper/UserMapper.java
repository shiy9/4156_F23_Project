package com.ims.mapper;

import com.ims.entity.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    @Select("SELECT COUNT(*) > 0 FROM User WHERE email = #{email}")
    @ResultType(Boolean.class)
    boolean emailExists(String email);

    @Insert("INSERT INTO User (password, email) VALUES (#{password}, #{email})")
    int insert(Users user);

    @Select("SELECT email, password FROM User WHERE email = #{email}")
    @Results({
            @Result(column = "email", property = "email"),
            @Result(column = "password", property = "password")
    })
    Users getUser(String email);
}
