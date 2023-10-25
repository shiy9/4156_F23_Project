package com.ims.mapper;

import com.ims.entity.Users;
import org.springframework.stereotype.Repository;

/**
 * Specific queries are linked in ims/src/main/resources/mapper/UserMapper.xml
 */
@Repository
public interface UserMapper {
  /**
   * Check if email already exists in the database.
   */
  boolean emailExists(String email);

  /**
   * Insert use into database
   * This function does not offer any error checking as they should have been done beforehand.
   */
  int insert(Users user);

  /**
   * Attempts to find the user. Used in the login process.
   */
  Users getUser(String email);

  /**
   * FOR TESTING PURPOSE ONLY.
   * DELETE the row associated with {email}
   */
  void testDeleteByEmail(String email);
}
