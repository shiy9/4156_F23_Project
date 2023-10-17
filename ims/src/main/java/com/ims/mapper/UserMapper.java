package com.ims.mapper;

import com.ims.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    int insert(User user);
}
