package com.ims;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.constants.ClientConstants;

import com.ims.security.TokenUtil;
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

import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.core.type.TypeReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.io.IOException;
import java.util.Map;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TokenUtil tokenUtil;

  private void loginRespCheck(MvcResult mvcResult, String expectedMessage,
                              String expectedEmail, String expectedType) throws IOException {
    // convert resp body to map
    String responseContent = mvcResult.getResponse().getContentAsString();
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> responseMap = objectMapper.readValue(responseContent,
            new TypeReference<Map<String, String>>() {
            });

    // Checks message
    assertEquals(expectedMessage,
            responseMap.get(ClientConstants.LOGIN_BODY_MESSAGE_KEY));

    // No need to check token if login not successful.
    if (!Objects.equals(expectedMessage, ClientConstants.LOGIN_SUCCESS)) return;

    // Check token is valid
    String token = responseMap.get(ClientConstants.LOGIN_BODY_TOKEN_KEY);
    assertTrue(tokenUtil.validateToken(token, expectedType));

    // Further, check if the email in the token matches the input email
    assertEquals(expectedEmail, tokenUtil.getEmail(token));
  }


  // ****************************
  // Register tests
  // ****************************
  @Test
  @Order(1)
  public void successRegTest() throws Exception {
    String clientJSON = "{ \"email\": \"json@columbia.edu\", \"password\": \"Test1234\", " +
            "\"clientType\": \"reTail\" }";
    mockMvc.perform(post("/client/register")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(status().isCreated())
            .andExpect(content().string(ClientConstants.CLIENT_REGISTER_SUCCESS));
  }

  @Test
  @Order(2)
  public void dupEmailRegTest() throws Exception {
    // password is also invalid, but should return client exists as that is more important
    String clientJSON = "{ \"email\": \"json@columbia.edu\", \"password\": \"imduplicate\", " +
            "\"clientType\": \"retail\" }";
    mockMvc.perform(post("/client/register")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(ClientConstants.CLIENT_EXISTS));
  }

  @Test
  @Order(3)
  public void badPassRegTest1() throws Exception {
    // password too short
    String clientJSON = "{ \"email\": \"json2@columbia.edu\", \"password\": \"2short\", " +
            "\"clientType\": \"retail\" }";
    mockMvc.perform(post("/client/register")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(content().string(ClientConstants.INVALID_PASSWORD));
  }

  @Test
  @Order(4)
  public void badPassRegTest2() throws Exception {
    // password has no letter
    String clientJSON = "{ \"email\": \"json2@columbia.edu\", \"password\": \"12345678\", " +
            "\"clientType\": \"retail\" }";
    mockMvc.perform(post("/client/register")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(content().string(ClientConstants.INVALID_PASSWORD));
  }

  @Test
  @Order(5)
  public void badPassRegTest3() throws Exception {
    // password has no number
    String clientJSON = "{ \"email\": \"json2@columbia.edu\", \"password\": " +
            "\"nonumberwhatsoever\", " +
            "\"clientType\": \"retail\" }";
    mockMvc.perform(post("/client/register")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(content().string(ClientConstants.INVALID_PASSWORD));
  }

  @Test
  @Order(6)
  public void badEmailRegTest1() throws Exception {
    // invalid email format missing @ and .something
    String clientJSON = "{ \"email\": \"imNotAnEmail\", \"password\": \"Test1234\", " +
            "\"clientType\": \"retail\" }";
    mockMvc.perform(post("/client/register")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(content().string(ClientConstants.INVALID_EMAIL));
  }

  @Test
  @Order(7)
  public void badEmailRegTest2() throws Exception {
    // invalid email format -> missing .something
    String clientJSON = "{ \"email\": \"notAnEmail@either\", \"password\": \"Test1234\", " +
            "\"clientType\": \"retail\" }";
    mockMvc.perform(post("/client/register")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(content().string(ClientConstants.INVALID_EMAIL));
  }

  @Test
  @Order(7)
  public void badTypeRegTest() throws Exception {
    // invalid clientType
    String clientJSON = "{ \"email\": \"json2@columbia.edu\", \"password\": \"Test1234\", " +
            "\"clientType\": \"sometype\" }";
    mockMvc.perform(post("/client/register")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(content().string(ClientConstants.INVALID_CLIENT_TYPE));
  }

  @Test
  @Order(8)
  public void successRegTest2() throws Exception {
    String clientJSON = "{ \"email\": \"json2@columbia.edu\", \"password\": \"Test2345\", " +
            "\"clientType\": \"wAreHouse\" }";
    mockMvc.perform(post("/client/register")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(status().isCreated())
            .andExpect(content().string(ClientConstants.CLIENT_REGISTER_SUCCESS));
  }

  @Test
  @Order(9)
  public void successRegTest3() throws Exception {
    String clientJSON = "{ \"email\": \"json3@columbia.edu\", \"password\": \"Test3456\", " +
            "\"clientType\": \"retail\" }";
    mockMvc.perform(post("/client/register")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(status().isCreated())
            .andExpect(content().string(ClientConstants.CLIENT_REGISTER_SUCCESS));
  }


  // ****************************
  // Login tests
  // ****************************
  @Test
  @Order(10)
  public void successLoginTest() throws Exception {
    String clientJSON = "{ \"email\": \"json@columbia.edu\", \"password\": \"Test1234\" }";
    MvcResult mvcResult = mockMvc.perform(post("/client/login")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(status().isOk())
            .andReturn();
    loginRespCheck(mvcResult, ClientConstants.LOGIN_SUCCESS, "json@columbia.edu",
            ClientConstants.CLIENT_TYPE_RETAIL);
  }

  @Test
  @Order(11)
  public void successLogin2Test() throws Exception {
    String clientJSON = "{ \"email\": \"json2@columbia.edu\", \"password\": \"Test2345\" }";
    MvcResult mvcResult = mockMvc.perform(post("/client/login")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(status().isOk())
            .andReturn();
    loginRespCheck(mvcResult, ClientConstants.LOGIN_SUCCESS, "json2@columbia.edu",
            ClientConstants.CLIENT_TYPE_WAREHOUSE);
  }

  @Test
  @Order(12)
  public void successLogin3Test() throws Exception {
    String clientJSON = "{ \"email\": \"json3@columbia.edu\", \"password\": \"Test3456\" }";
    MvcResult mvcResult = mockMvc.perform(post("/client/login")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(status().isOk())
            .andReturn();

    loginRespCheck(mvcResult, ClientConstants.LOGIN_SUCCESS, "json3@columbia.edu",
            ClientConstants.CLIENT_TYPE_RETAIL);
  }

  @Test
  @Order(13)
  public void clientNotFound() throws Exception {
    String clientJSON = "{ \"email\": \"json4@columbia.edu\", \"password\": \"Test3456\" }";
    MvcResult mvcResult = mockMvc.perform(post("/client/login")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(status().isNotFound())
            .andReturn();
    // expectedEmail does not matter since it will not be checked
    loginRespCheck(mvcResult, ClientConstants.CLIENT_NOT_FOUND, "", "");
  }

  @Test
  @Order(14)
  public void passWordMismatch() throws Exception {
    // lower case 't' -> needs to be uppercase
    String clientJSON = "{ \"email\": \"json@columbia.edu\", \"password\": \"test1234\" }";
    MvcResult mvcResult = mockMvc.perform(post("/client/login")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(status().isUnauthorized())
            .andReturn();
    loginRespCheck(mvcResult, ClientConstants.PASSWORD_MISMATCH, "", "");
  }

  @Test
  @Order(15)
  public void passWordMismatch2() throws Exception {
    String clientJSON = "{ \"email\": \"json2@columbia.edu\", \"password\": \"Test1234\" }";
    MvcResult mvcResult = mockMvc.perform(post("/client/login")
                    .contentType("application/json")
                    .content(clientJSON))
            .andExpect(status().isUnauthorized())
            .andReturn();
    loginRespCheck(mvcResult, ClientConstants.PASSWORD_MISMATCH, "", "");
  }

  /**
   * Note that this is to clean up any new accounts we created during the testing process. It
   * will ONLY delete those accounts and will keep other existing accounts in place.
   */
  @Test
  @Order(16)
  public void cleanup() throws Exception {
    List<String> createdClients = Arrays.asList("json@columbia.edu", "json2@columbia.edu",
            "json3@columbia.edu");
    String targets = new ObjectMapper().writeValueAsString(createdClients);

    mockMvc.perform(post("/client/clienttestdelete")
                    .contentType("application/json")
                    .content(targets))
            .andExpect(status().isOk())
            .andExpect(content().string(ClientConstants.TEST_DELETE_SUCCESS));
  }
}
