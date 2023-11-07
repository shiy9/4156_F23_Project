package com.ims.service;

import com.ims.entity.Client;

/**
 * Declares the functions related to Client service. Implementations are in
 * ./impl/ClientServiceImpl.
 */
public interface ClientService {

  /**
   * Inserts client into the database (uses ClientMapper's insert() method).
   */
  int createClient(Client client);

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
   * Check if the client exists in the database. Used by the register process
   * Uses ClientMapper's emailExists() method.
   */
  boolean clientExist(String email);

  /**
   * Attempt to get the information of the client in the database. Used by the login process
   * for password verification. Uses ClientMapper's getClient() method.
   */
  Client getClient(String email);

  /**
   * FOR TESTING PURPOSE ONLY
   * Delete the row of data associated with email.
   * Uses ClientMapper's testDeleteByEmail() function.
   * Should NOT be present in the final version of the project.
   */
  void testDeleteByEmail(String email);
}
