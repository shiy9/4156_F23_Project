package com.ims.controller;


import com.ims.constants.UserMessages;
import com.ims.entity.Users;
import com.ims.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Implements User-related REST API.
 */
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;

  /**
   * Function responsible for registering a new user. Registration will fail if user already
   * exists (duplicate email), or invalid email or password formats.
   */
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody Users user) {
    String userEmail = user.getEmail();
    String userPassword = user.getPassword();
    if (!userService.isValidEmail(userEmail)) {
      return new ResponseEntity<>(UserMessages.INVALID_EMAIL, HttpStatus.BAD_REQUEST);
    }
    if (userService.userExist(userEmail)) {
      return new ResponseEntity<>(UserMessages.USER_EXISTS, HttpStatus.BAD_REQUEST);
    }
    if (!userService.isValidPassword(userPassword)) {
      return new ResponseEntity<>(UserMessages.INVALID_PASSWORD, HttpStatus.BAD_REQUEST);
    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String encryptedPass = encoder.encode(userPassword);
    user.setPassword(encryptedPass);

    // returns the number of rows inserted
    // if 0 -> failure
    int insertRes = userService.createUser(user);
    if (insertRes == 0) {
      return new ResponseEntity<>(UserMessages.USER_INSERT_FAILED, HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<>(UserMessages.USER_REGISTER_SUCCESS, HttpStatus.CREATED);
    }
  }

  /**
   * Function responsible for login. For iteration 1, the function will only validate login
   * information. For future iteration, a stateful token will be returned which will permit
   * user operations (such as order inquiry etc.)
   */
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody Users user) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Attempt to find the user
    Users curUser = userService.getUser(user.getEmail());
    if (curUser == null) {
      return new ResponseEntity<>(UserMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    if (!encoder.matches(user.getPassword(), curUser.getPassword())) {
      return new ResponseEntity<>(UserMessages.PASSWORD_MISMATCH, HttpStatus.UNAUTHORIZED);
    }

    return new ResponseEntity<>(UserMessages.LOGIN_SUCCESS, HttpStatus.OK);
  }

  /**
   * TESTING PURPOSE ONLY
   * This function removes any new accounts created in the testing process. The endpoint should
   * NOT be present in the final version of the project.
   */
  @PostMapping("/usertestdelete")
  public ResponseEntity<String> userTestDelete(@RequestBody List<String> emails) {
    try {
      for (String email : emails) {
        userService.testDeleteByEmail(email);
      }
      return new ResponseEntity<>(UserMessages.TEST_DELETE_SUCCESS, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(UserMessages.TEST_DELETE_FAILED, HttpStatus.BAD_REQUEST);
    }
  }
}
