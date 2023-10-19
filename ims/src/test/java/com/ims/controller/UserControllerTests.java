package com.ims.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.constants.UserMessages;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    // ****************************
    // Register tests
    // ****************************
    @Test
    @Order(1)
    public void successRegTest() throws Exception {
        String userJSON = "{ \"email\": \"json@columbia.edu\", \"password\": \"Test1234\" }";
        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(UserMessages.USER_REGISTER_SUCCESS));
    }

    @Test
    @Order(2)
    public void dupEmailRegTest() throws Exception {
        // password is also invalid, but should return user exists as that is more important
        String userJSON = "{ \"email\": \"json@columbia.edu\", \"password\": \"imduplicate\" }";
        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(UserMessages.USER_EXISTS));
    }

    @Test
    @Order(3)
    public void badPassRegTest1() throws Exception {
        // password too short
        String userJSON = "{ \"email\": \"json2@columbia.edu\", \"password\": \"2short\" }";
        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(content().string(UserMessages.INVALID_PASSWORD));
    }

    @Test
    @Order(4)
    public void badPassRegTest2() throws Exception {
        // password has no letter
        String userJSON = "{ \"email\": \"json2@columbia.edu\", \"password\": \"12345678\" }";
        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(content().string(UserMessages.INVALID_PASSWORD));
    }

    @Test
    @Order(5)
    public void badPassRegTest3() throws Exception {
        // password has no number
        String userJSON = "{ \"email\": \"json2@columbia.edu\", \"password\": \"nonumberwhatsoever\" }";
        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(content().string(UserMessages.INVALID_PASSWORD));
    }

    @Test
    @Order(6)
    public void badEmailRegTest1() throws Exception {
        // invalid email format missing @ and .something
        String userJSON = "{ \"email\": \"imNotAnEmail\", \"password\": \"Test1234\" }";
        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(content().string(UserMessages.INVALID_EMAIL));
    }

    @Test
    @Order(7)
    public void badEmailRegTest2() throws Exception {
        // invalid email format -> missing .something
        String userJSON = "{ \"email\": \"notAnEmail@either\", \"password\": \"Test1234\" }";
        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(content().string(UserMessages.INVALID_EMAIL));
    }

    @Test
    @Order(8)
    public void successRegTest2() throws Exception {
        String userJSON = "{ \"email\": \"json2@columbia.edu\", \"password\": \"Test2345\" }";
        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(UserMessages.USER_REGISTER_SUCCESS));
    }

    @Test
    @Order(9)
    public void successRegTest3() throws Exception {
        String userJSON = "{ \"email\": \"json3@columbia.edu\", \"password\": \"Test3456\" }";
        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(UserMessages.USER_REGISTER_SUCCESS));
    }


    // ****************************
    // Login tests
    // ****************************
    @Test
    @Order(10)
    public void successLoginTest() throws Exception {
        String userJSON = "{ \"email\": \"json@columbia.edu\", \"password\": \"Test1234\" }";
        mockMvc.perform(post("/user/login")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(status().isOk())
                .andExpect(content().string(UserMessages.LOGIN_SUCCESS));
    }

    @Test
    @Order(11)
    public void successLogin2Test() throws Exception {
        String userJSON = "{ \"email\": \"json2@columbia.edu\", \"password\": \"Test2345\" }";
        mockMvc.perform(post("/user/login")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(status().isOk())
                .andExpect(content().string(UserMessages.LOGIN_SUCCESS));
    }

    @Test
    @Order(12)
    public void successLogin3Test() throws Exception {
        String userJSON = "{ \"email\": \"json3@columbia.edu\", \"password\": \"Test3456\" }";
        mockMvc.perform(post("/user/login")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(status().isOk())
                .andExpect(content().string(UserMessages.LOGIN_SUCCESS));
    }

    @Test
    @Order(13)
    public void userNotFound() throws Exception {
        String userJSON = "{ \"email\": \"json4@columbia.edu\", \"password\": \"Test3456\" }";
        mockMvc.perform(post("/user/login")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(UserMessages.USER_NOT_FOUND));
    }

    @Test
    @Order(14)
    public void passWordMismatch() throws Exception {
        // lower case 't' -> needs to be uppercase
        String userJSON = "{ \"email\": \"json@columbia.edu\", \"password\": \"test1234\" }";
        mockMvc.perform(post("/user/login")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(UserMessages.PASSWORD_MISMATCH));
    }

    @Test
    @Order(15)
    public void passWordMismatch2() throws Exception {
        String userJSON = "{ \"email\": \"json2@columbia.edu\", \"password\": \"Test1234\" }";
        mockMvc.perform(post("/user/login")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(UserMessages.PASSWORD_MISMATCH));
    }

    /**
     * Note that this is to clean up any new accounts we created during the testing process. It
     * will ONLY delete those accounts and will keep other existing accounts in place.
     */
    @Test
    @Order(16)
    public void cleanup() throws Exception {
        List<String> createdUsers = Arrays.asList("json@columbia.edu", "json2@columbia.edu", "json3@columbia.edu");
        String targets = new ObjectMapper().writeValueAsString(createdUsers);

        mockMvc.perform(post("/user/usertestdelete")
                        .contentType("application/json")
                        .content(targets))
                .andExpect(status().isOk())
                .andExpect(content().string(UserMessages.TEST_DELETE_SUCCESS));
    }
}
