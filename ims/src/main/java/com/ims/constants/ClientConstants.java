package com.ims.constants;

/**
 * List of response messages, which will be used in ClientController and ClientControllerTests
 * The class should not be "instantiable", hence the private constructor.
 */
public final class ClientConstants {
  private ClientConstants() {
  } // prevent instantiation

  // Register related messages
  public static final String INVALID_EMAIL = "Invalid email format";
  public static final String INVALID_PASSWORD = "Password must contain at least 8 characters, "
          + "one letter, and one number";
  public static final String CLIENT_EXISTS = "Client already exists";
  public static final String CLIENT_INSERT_FAILED = "Insert failed"; // db rejects the insert
  public static final String CLIENT_REGISTER_SUCCESS = "Register Success";
  public static final String INVALID_CLIENT_TYPE = "Invalid client type. Must be \"retail\" or "
          + "\"warehouse\"";

  // Login related messages
  public static final String CLIENT_NOT_FOUND = "Client not found";
  public static final String PASSWORD_MISMATCH = "Incorrect password";
  public static final String LOGIN_SUCCESS = "Login successful";

  // Login related keys
  public static final String LOGIN_BODY_MESSAGE_KEY = "message";
  public static final String LOGIN_BODY_TOKEN_KEY = "token";
  public static final String LOGIN_BODY_CLIENT_ID = "clientId";
  public static final String CLAIM_KEY_CLIENT_TYPE = "clientType";
  public static final String CLAIM_KEY_CLIENT_ID = "clientId";

  // Client types
  public static final String CLIENT_TYPE_RETAIL = "retail";
  public static final String CLIENT_TYPE_WAREHOUSE = "warehouse";

  // Token related
  public static final String INVALID_TOKEN = "Expired or invalid token.";
  public static final String TYPE_NOT_AUTHORIZED = "Not authorized to use this endpoint.";

  // TESTING stuff
  public static final String TEST_DELETE_SUCCESS = "Accounts generated during testing deleted "
          + "successfully";
  public static final String TEST_DELETE_FAILED = "Failed to delete accounts generated during "
          + "testing";

}
