package com.ims.service;

import com.ims.entity.Users;

/**
 * Declares the functions related to User service. Implementations are in ./impl/UserServiceImpl.
 */
public interface UserService {

  /**
   * Inserts user into the database (uses UserMapper's insert() method).
   */
  int createUser(Users user);

  /**
   * Validates the format of the email provided using a regex expression.
   * The email must be in the form of xxx@xxx.xxx
   */
  boolean isValidEmail(String email);

  /**
   * Validates the format of the password provided using a regex expression.
   * The password needs to have a minimum of 8 characters, at least one letter and one number.
   */
  boolean isValidPassword(String password);

  /**
   * Check if the user exists in the database. Used by the register process
   * Uses UserMapper's emailExists() method.
   */
  boolean userExist(String email);

  /**
   * Attempt to get the information of the user in the database. Used by the login process
   * for password verification. Uses UserMapper's getUser() method.
   */
  Users getUser(String email);

  /**
   * FOR TESTING PURPOSE ONLY
   * Delete the row of data associated with email.
   * Uses UserMapper's testDeleteByEmail() function.
   * Should NOT be present in the final version of the project.
   */
  void testDeleteByEmail(String email);
}
