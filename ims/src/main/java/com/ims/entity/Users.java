package com.ims.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * The User entity. This class will reflect the User entity stored in the database.
 */
@Data
public class Users {
  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private String password;
}
