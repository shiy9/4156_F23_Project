package com.ims.controller;

import com.ims.constants.ClientConstants;
import com.ims.entity.Client;
import com.ims.security.TokenUtil;
import com.ims.service.ClientService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Implements Client-related REST API.
 */
@RestController
@RequestMapping("/client")
public class ClientController {
  @Autowired
  private ClientService clientService;

  @Autowired
  private TokenUtil tokenUtil;

  /**
   * Function responsible for registering a new client. Registration will fail if client already
   * exists (duplicate email), or invalid email or password formats.
   */
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody Client client) {
    String clientEmail = client.getEmail();
    String clientPassword = client.getPassword();
    if (!clientService.isValidEmail(clientEmail)) {
      return new ResponseEntity<>(ClientConstants.INVALID_EMAIL, HttpStatus.BAD_REQUEST);
    }
    if (clientService.clientExist(clientEmail)) {
      return new ResponseEntity<>(ClientConstants.CLIENT_EXISTS, HttpStatus.BAD_REQUEST);
    }
    if (!clientService.isValidPassword(clientPassword)) {
      return new ResponseEntity<>(ClientConstants.INVALID_PASSWORD, HttpStatus.BAD_REQUEST);
    }
    String clientType = clientService.validateType(client.getClientType());
    if (clientType == null) {
      return new ResponseEntity<>(ClientConstants.INVALID_CLIENT_TYPE, HttpStatus.BAD_REQUEST);
    }
    client.setClientType(clientType);

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String encryptedPass = encoder.encode(clientPassword);
    client.setPassword(encryptedPass);

    // returns the number of rows inserted
    // if 0 -> failure
    int insertRes = clientService.createClient(client);
    if (insertRes == 0) {
      return new ResponseEntity<>(ClientConstants.CLIENT_INSERT_FAILED, HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<>(ClientConstants.CLIENT_REGISTER_SUCCESS, HttpStatus.CREATED);
    }
  }

  /**
   * Function responsible for login.
   * Upon successful login, return a JSON object containing:
   *  - "message" -> LOGIN_SUCCESS message
   *  - "token" -> auth token to perform other API calls.
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Client client) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    Map<String, String> response = new HashMap<>();

    // Attempt to find the client
    Client curClient = clientService.getClient(client.getEmail());
    if (curClient == null) {
      response.put(ClientConstants.LOGIN_BODY_MESSAGE_KEY, ClientConstants.CLIENT_NOT_FOUND);
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    if (!encoder.matches(client.getPassword(), curClient.getPassword())) {
      response.put(ClientConstants.LOGIN_BODY_MESSAGE_KEY, ClientConstants.PASSWORD_MISMATCH);
      return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    // Client exists, password matches, generate token
    // passing in curClient, not client since curClient is from db and contains all client info
    String token = tokenUtil.createToken(curClient);
    response.put(ClientConstants.LOGIN_BODY_MESSAGE_KEY, ClientConstants.LOGIN_SUCCESS);
    response.put(ClientConstants.LOGIN_BODY_TOKEN_KEY, token);

    return ResponseEntity.ok(response);
  }

  /**
   * TESTING PURPOSE ONLY
   * This function removes any new accounts created in the testing process. The endpoint should
   * NOT be present in the final version of the project.
   */
  @PostMapping("/clienttestdelete")
  public ResponseEntity<String> clientTestDelete(@RequestBody List<String> emails) {
    try {
      for (String email : emails) {
        clientService.testDeleteByEmail(email);
      }
      return new ResponseEntity<>(ClientConstants.TEST_DELETE_SUCCESS, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(ClientConstants.TEST_DELETE_FAILED, HttpStatus.BAD_REQUEST);
    }
  }
}
