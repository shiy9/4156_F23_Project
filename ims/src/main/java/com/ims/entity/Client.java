package com.ims.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * The Client entity. This class will reflect the Client entity stored in the database.
 */
@Data
public class Client {
  @Getter
  @Setter
  private String email;

  @Getter
  @Setter
  private String password;
}
