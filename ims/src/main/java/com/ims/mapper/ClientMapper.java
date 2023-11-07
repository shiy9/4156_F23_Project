package com.ims.mapper;

import com.ims.entity.Client;
import org.springframework.stereotype.Repository;

/**
 * Specific queries are linked in ims/src/main/resources/mapper/ClientMapper.xml
 */
@Repository
public interface ClientMapper {
  /**
   * Check if email already exists in the database.
   */
  boolean emailExists(String email);

  /**
   * Insert use into database
   * This function does not offer any error checking as they should have been done beforehand.
   */
  int insert(Client client);

  /**
   * Attempts to find the client. Used in the login process.
   */
  Client getClient(String email);

  /**
   * FOR TESTING PURPOSE ONLY.
   * DELETE the row associated with {email}
   */
  void testDeleteByEmail(String email);
}
