package com.ims.service.impl;

import com.ims.entity.Client;
import com.ims.mapper.ClientMapper;
import com.ims.service.ClientService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Implementation of various Client Service functions.
 */
@Service
public class ClientServiceImpl implements ClientService {
  @Autowired
  private ClientMapper clientMapper;

  private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

  private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

  @Override
  public boolean isValidEmail(String email) {
    if (email == null) {
      return false;
    }
    Pattern pattern = Pattern.compile(EMAIL_REGEX);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

  @Override
  public boolean isValidPassword(String password) {
    if (password == null) {
      return false;
    }
    Pattern pattern = Pattern.compile(PASSWORD_REGEX);
    Matcher matcher = pattern.matcher(password);
    return matcher.matches();
  }

  @Override
  public int createClient(Client client) {
    return clientMapper.insert(client);
  }

  @Override
  public boolean clientExist(String email) {
    return clientMapper.emailExists(email);
  }

  @Override
  public Client getClient(String email) {
    return clientMapper.getClient(email);
  }

  @Override
  public void testDeleteByEmail(String email) {
    clientMapper.testDeleteByEmail(email);
  }
}