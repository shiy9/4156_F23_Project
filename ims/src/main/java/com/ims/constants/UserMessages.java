package com.ims.constants;

/**
 * List of response messages, which will be used in UserController and UserControllerTests
 * The class should not be "instantiable", hence the private constructor.
 */
public final class UserMessages {
  private UserMessages() {} // prevent instantiation

  // Register related messages
  public static final String INVALID_EMAIL = "Invalid email format";
  public static final String INVALID_PASSWORD = "Password must contain at least 8 characters, "
          + "one letter, and one number";
  public static final String USER_EXISTS = "User already exists";
  public static final String USER_INSERT_FAILED = "Insert failed"; // db rejects the insert
  public static final String USER_REGISTER_SUCCESS = "Register Success";

  // Login related messages
  public static final String USER_NOT_FOUND = "User not found";
  public static final String PASSWORD_MISMATCH = "Incorrect password";
  public static final String LOGIN_SUCCESS = "Login successful";

  // TESTING stuff
  public static final String TEST_DELETE_SUCCESS = "Accounts generated during testing deleted "
          + "successfully";
  public static final String TEST_DELETE_FAILED = "Failed to delete accounts generated during "
          + "testing";
}
