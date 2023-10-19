package com.ims.controller;

import com.ims.entity.Users;
import com.ims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String userEmail = user.getEmail();
        String userPassword = user.getPassword();
        if (!userService.isValidEmail(userEmail)) {
            return new ResponseEntity<>("Invalid email format", HttpStatus.BAD_REQUEST);
        }
        if (!userService.isValidPassword(userPassword)) {
            return new ResponseEntity<>("Password must contain at least 8 characters, one letter, and one number",
                    HttpStatus.BAD_REQUEST);
        }
        if (userService.userExist(userEmail)) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        String encryptedPass = encoder.encode(userPassword);
        user.setPassword(encryptedPass);

        // returns the number of rows inserted
        // if 0 -> failure
        int insertRes = userService.createUser(user);
        if (insertRes == 0) return new ResponseEntity<>("Insert failed", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>("Register Success", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Attempt to find the user
        Users curUser = userService.getUser(user.getEmail());
        if (curUser == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        if (!encoder.matches(user.getPassword(), curUser.getPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("Login successful", HttpStatus.OK);
    }

}
