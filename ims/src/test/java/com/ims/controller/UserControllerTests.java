package com.ims.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void successRegisterTest() throws Exception {
        String userJSON = "{ \"email\": \"json@columbia.edu\", \"password\": \"Test1234\" }";
        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(userJSON))
                .andExpect(status().isCreated());
    }

}
