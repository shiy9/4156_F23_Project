package com.ims.mapper;

import com.ims.entity.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    int insert(Users user);
}
